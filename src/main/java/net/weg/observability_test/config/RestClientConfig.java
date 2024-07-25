//package net.weg.observability_test.config;
//
//import org.springframework.boot.web.client.RestClientCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.web.client.RestClient;
//
//import java.io.IOException;
//import java.util.Collections;
//
//@Configuration
//public class RestClientConfig {
//
//    @Bean
//    public RestClientCustomizer restClientCustomizer() {
//        return new RestClientCustomizer() {
//            @Override
//            public void customize(RestClient.Builder restClientBuilder) {
//                restClientBuilder.requestInterceptor()
//                restClientBuilder.requestInterceptors(Collections.singletonList(defaultHeadersInterceptor()));
//            }
//        };
//    }
//
//    private ClientHttpRequestInterceptor defaultHeadersInterceptor() {
//        return new ClientHttpRequestInterceptor() {
//            @Override
//            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//                HttpHeaders headers = request.getHeaders();
//                headers.set("X-Default-Header", "DefaultHeaderValue");
//                // Adicione mais cabeçalhos padrão aqui
//                return execution.execute(request, body);
//            }
//        };
//    }
//}
