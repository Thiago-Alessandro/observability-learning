package net.weg.observability_test.service;

import ch.qos.logback.classic.LoggerContext;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import net.weg.observability_test.util.MDCUsecase;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;


@Service
public class SampleService {

    private final RestClient restClient;
    private final RestTemplate restTemplate;

    public String resetLogbackContext() {
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        if (iLoggerFactory instanceof LoggerContext) {
            LoggerContext context = (LoggerContext) iLoggerFactory;
//            System.out.println(context.getLoggerList() + "\n");
//            System.out.println(context.getTurboFilterList());
//            System.out.println("before\n");
            context.reset();
//            System.out.println(context.getLoggerList() + "\n");
//            System.out.println(context.getTurboFilterList());
//            System.out.println("after\n");
            return "LoggerContext reset!";
        }
        return "Failed to reset LoggerContext!";
    }

    public SampleService(RestClient.Builder builder, RestTemplateBuilder restTemplateBuilder) {
        this.restClient = builder.build();
        this.restTemplate = restTemplateBuilder.build();
    }


    @Autowired
    private Tracer tracer;
    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Scheduled(fixedRate = 60000)
    public void logDebugMessage() {
        logger.info("Mensagem genérica de teste - log gerado a cada 60 segundos");
    }

    public String testService(){
        MDCUsecase.push("TS001");
        MDCUsecase.pop("MD001");

//        System.out.println(makeRestTemplateRequest());
//        String result = (String) makeRestClientRequest();

        printTestLogs();

        return "result";
    }

    private Object makeRestClientRequest(){
        String result = restClient.get().uri("/app").retrieve().body(String.class);
        return result;
    }

    private Object makeRestTemplateRequest(){
        String url = "http://localhost:1010/app";
        String result = restTemplate.getForObject(url, String.class).toString();
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
//            System.out.println(resetLogbackContext());
        } finally {
            span.end();
        }
    }
}
