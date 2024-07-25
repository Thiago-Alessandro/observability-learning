package net.weg.observability_test.service;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import net.weg.observability_test.filter.Filter;
import net.weg.observability_test.util.SetConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class SampleService {
    @Autowired
    private Tracer tracer;
    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Scheduled(fixedRate = 1000) // Executa a cada 5 segundos
    public void logDebugMessage() {
        logger.info("Mensagem genérica de teste - log gerado a cada 5 segundos");
    }

    public String testService(){
        String logLevel = MDC.get("X-LOG-LEVEL");
        Set<String> usecase = SetConverter.convertStringToSet(MDC.get("usecase"));

        System.out.println(makeRestTemplateRequest(logLevel, usecase));
        String result = (String) makeRestClientRequest(logLevel, usecase);
        printTestLogs();

        return result;
    }

    private Object makeRestClientRequest(String logLevel, Set<String> usecase){
        RestClient restClient = RestClient.builder().build();
        String result = restClient.get().uri("/app").retrieve().body(String.class);
        return result;
    }

    private Object makeRestTemplateRequest(String logLevel, Set<String> usecase){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-LOG-LEVEL", logLevel);
        headers.add("X-LOG-USECASE", SetConverter.convertSetToString(usecase));
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        String url = "http://localhost:1010/app";
        String result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class).toString();
        return result;
    }

    private void printTestLogs(){
        logger.trace("TRACE LOG");
        logger.debug("DEBUG LOG");
        logger.info("INFO LOG");
        logger.warn("WARN LOG");
        logger.error("ERROR LOG");
    }


    public void someMethod() {
        Span span = tracer.spanBuilder("SampleService.index").startSpan();
        try {
            logger.info("Passing through some method!");
        } finally {
            span.end();
        }
    }
}
