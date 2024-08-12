package net.weg.observability_test.config;

import io.micrometer.observation.ObservationRegistry;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporter;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.ServiceLevelObjectiveBoundary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenTelemetryConfig {

    @Bean
    public OpenTelemetry openTelemetry() {
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().setEndpoint("http://otel-collector:4317").build()).build())
                .build();


//        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
//                .registerMetricReader(PeriodicMetricReader.builder(
//                        OtlpGrpcMetricExporter.builder().setEndpoint("http://otel-collector:4317").build())
//
////                        .setInterval(Duration.ofSeconds(10))
//                        .build()
//
//                ).build();

//        ObservationRegistry.create().


        LogRecordExporter logExporter = OtlpGrpcLogRecordExporter.builder()
                .setEndpoint("http://otel-collector:4317")
                .build();

        // Configure the SdkLogEmitterProvider
        SdkLoggerProvider logEmitterProvider = SdkLoggerProvider.builder()
//                .setResource(resource)
                .addLogRecordProcessor(BatchLogRecordProcessor.builder(logExporter).build())
//                .addLogProcessor()
                .build();



        OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
//                .setMeterProvider(meterProvider)
                .setLoggerProvider(logEmitterProvider)
                .setPropagators(ContextPropagators.noop())
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .build();

        GlobalOpenTelemetry.set(openTelemetrySdk);
        return openTelemetrySdk;
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("observability-test");
    }

//    @Bean
//    public Meter meter(OpenTelemetry openTelemetry) {
//        return openTelemetry.getMeter("observability-test");
//    }

}
