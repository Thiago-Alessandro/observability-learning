package net.weg.observability_test.util;

import org.slf4j.MDC;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestCustomizer implements RestClientCustomizer {
    @Override
    public void customize(RestClient.Builder restClientBuilder) {
        String logLevel = MDC.get("X-LOG-LEVEL");
        String usecase = MDC.get("X-LOG-USECASE");
        restClientBuilder
                .baseUrl("http://localhost:1010")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("X-LOG-LEVEL", logLevel);
                    httpHeaders.add("X-LOG-USECASE", usecase);
                }).defaultRequest(requestHeadersSpec -> requestHeadersSpec.retrieve()).build();
    }
}
