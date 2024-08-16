package net.weg.observability_test.config;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import net.weg.observability_test.enums.MDCKeys;
import org.slf4j.MDC;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import io.micrometer.core.instrument.Timer;
import java.util.*;

@Configuration
public class RestConfig {

    private final String BASE_URL = "http://localhost:1010";


    //trocar para outra classe
//    @Bean
//    public Timer requestLatencyTimer(MeterRegistry registry) {
//        return Timer.builder("http.server.requests.latency")
//                .publishPercentileHistogram()
//                .publishPercentiles(0.5, 0.95, 0.99)
//                .register(registry);
//    }
//
//    @Bean
//    public DistributionSummary requestSizeSummary(MeterRegistry registry) {
//        return DistributionSummary.builder("http.server.requests.size")
//                .publishPercentileHistogram()
//                .publishPercentiles(0.5, 0.95, 0.99)
//                .register(registry);
//    }




    @Bean
    public RestClientCustomizer restClientCustomizer() {
        return restClientBuilder -> {
            restClientBuilder.defaultRequest(request -> request.headers(this::setDeafultHeaders));
//            restClientBuilder.baseUrl(BASE_URL);
        };
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer() {
        return restTemplate -> {
            restTemplate.setInterceptors(Collections.singletonList(defaultHeadersInterceptor()));
        };
    }

    private ClientHttpRequestInterceptor defaultHeadersInterceptor() {
        return (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            setDeafultHeaders(headers);
            return execution.execute(request, body);
        };
    }

    private void setDeafultHeaders(HttpHeaders headers){
        for (MDCKeys header : MDCKeys.values()) {
            String mdcValue = MDC.get(header.mdcKey());
            if(mdcValue != null){
                headers.set(header.mdcKey(), mdcValue);
            }
        }
    }

}
