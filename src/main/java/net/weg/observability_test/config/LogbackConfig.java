package net.weg.observability_test.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.util.StatusPrinter;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.weg.observability_test.filter.MDCLogLevelFilter;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ch.qos.logback.classic.Logger;

@Component
public class LogbackConfig implements LoggerContextListener {

    @EventListener(ApplicationReadyEvent.class)
    public void configureLogback() {
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        if (iLoggerFactory instanceof LoggerContext) {
            LoggerContext context = (LoggerContext) iLoggerFactory;
            configureDefaultContext(context);
        }
    }

    private void configureDefaultContext(LoggerContext context) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} - [%thread] - %highlight(%-5level) - usecase=[%X{usecase}] - %cyan(%-50.50class) >>> %msg%n");
        encoder.start();

        context.addTurboFilter(new MDCLogLevelFilter());
        Logger rootLogger = context.getLogger("ROOT");
//        rootLogger.setLevel(Level.TRACE);


        rootLogger.addAppender(createConsoleAppender(context, encoder));
        rootLogger.addAppender(createLogstashAppender(context, encoder));
        rootLogger.addAppender(createOpenTelemetryAppender(context, encoder));

        StatusPrinter.print(context);
    }

    private ConsoleAppender createConsoleAppender(LoggerContext context, PatternLayoutEncoder encoder) {
        ConsoleAppender appender = new ConsoleAppender();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();
        return appender;
    }

    private LogstashTcpSocketAppender createLogstashAppender(LoggerContext context, PatternLayoutEncoder encoder){
        LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();
        appender.setName("LOGSTASH");
        appender.addDestination("logstash01:5044");
        return appender;
    }

    private OpenTelemetryAppender createOpenTelemetryAppender(LoggerContext context, PatternLayoutEncoder encoder){
        OpenTelemetryAppender otelAppender = new OpenTelemetryAppender();
        otelAppender.setContext(context);
        otelAppender.setName("otel-otlp");
//        otelAppender.setEncoder(encoder);
        otelAppender.setCaptureCodeAttributes(true);
        otelAppender.setCaptureKeyValuePairAttributes(true);
        otelAppender.start();
        return otelAppender;
    }

    @Override
    public boolean isResetResistant() {
        return true;
    }

    @Override
    public void onStart(LoggerContext context) {
    }

    @Override
    public void onReset(LoggerContext context) {
        configureDefaultContext(context);
    }

    @Override
    public void onStop(LoggerContext context) {
    }

    @Override
    public void onLevelChange(Logger logger, Level level) {
    }
}
