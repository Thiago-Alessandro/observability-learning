//package net.weg.observability_test;
//
//import io.opentelemetry.api.GlobalOpenTelemetry;
//import io.opentelemetry.api.metrics.LongCounter;
//import io.opentelemetry.api.metrics.Meter;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.SpanKind;
//import io.opentelemetry.api.trace.StatusCode;
//import io.opentelemetry.api.trace.Tracer;
//import io.opentelemetry.context.Context;
//import io.opentelemetry.context.Scope;
//import io.opentelemetry.context.propagation.TextMapGetter;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Collections;
//
//
//@Component
//public class filter extends OncePerRequestFilter {
//    String SERVICE_NAME = System.getenv("OTEL_SERVICE_NAME");
//
//    private static final TextMapGetter<HttpServletRequest> getter =
//            new TextMapGetter<HttpServletRequest>() {
//                @Override
//                public Iterable<String> keys(HttpServletRequest httpServletRequest) {
//                    return Collections.list(httpServletRequest.getHeaderNames());
//                }
//
//                @Override
//                public String get(HttpServletRequest httpServletRequest, String s) {
//                    return httpServletRequest.getHeader(s);
//                }
//            };
//
//    @Override
//    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
//        Tracer tracer = GlobalOpenTelemetry.getTracer(SERVICE_NAME);
//
//        Context extractedContext = GlobalOpenTelemetry.getPropagators()
//                .getTextMapPropagator()
//                .extract(Context.current(), request, getter);
//
//        Span span = tracer.spanBuilder(request.getRequestURI())
//                .setSpanKind(SpanKind.SERVER)
//                .setParent(extractedContext)
//                .startSpan();
//
//        Span.current().setAttribute("foo", "bar");
//
//        Meter myMeter = GlobalOpenTelemetry.getMeter("my_meter");
//        LongCounter counter = myMeter.counterBuilder("my_counter").build();
//        counter.add(42);
//
//        try (Scope scope = span.makeCurrent()) {
//            filterChain.doFilter(request, response);
//        } catch (Exception e) {
//            span.setStatus(StatusCode.ERROR);
//            throw e;
//        } finally {
//            span.end();
//        }
//    }
//
//
//}
