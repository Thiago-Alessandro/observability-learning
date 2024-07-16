package net.weg.observability_test.service;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import net.weg.observability_test.controller.RollController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Autowired
    private Tracer tracer;

    public void someMethod() {
        Span span = tracer.spanBuilder("SampleService.index").startSpan();
        try {
            logger.info("Passing through some method!");
        } finally {
            span.end();
        }
    }
}
