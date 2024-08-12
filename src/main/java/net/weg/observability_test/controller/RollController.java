package net.weg.observability_test.controller;

import java.util.List;
import java.util.Optional;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import net.weg.observability_test.model.Dice;
import net.weg.observability_test.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("")
public class RollController {

//    private final Meter meter;
//    private final LongCounter requestsCounter;
//    private final LongCounter anonymousRequestsCounter;


//    private final LongHistogram requestDurationTimer;

    private static final Logger logger = LoggerFactory.getLogger(RollController.class);

    @Autowired
    private SampleService sampleService;
//    @Autowired
//    private Tracer tracer;

    public RollController(OpenTelemetry openTelemetry){
//        meter = openTelemetry.getMeter("RollController");
//
//        requestsCounter = meter
//                .counterBuilder("roll_dice_requests")
//                .setDescription("Número de requisições para o método rollDice")
//                .setUnit("requests")
//                .build();
//
//        anonymousRequestsCounter = meter
//                .counterBuilder("anonymous_roll_dice_requests")
//                .setDescription("Número de requisições anônimas para o método rollDice")
//                .setUnit("requests")
//                .build();

//        requestDurationTimer = meter
//                .histogramBuilder("http.response.latency")
//                .ofLongs()
//                .setDescription("Duração das requisições no método rollDice")
//                .setUnit("ms")
//                .build();
    }

//    @Timed(value = "endpoint.timed.test", histogram = true)
//    @Counted(value = "endpoint.counted.test")
    @GetMapping("/test")
    public String testController(){
        return sampleService.testService();
    }

//    @Timed(value = "endpoint.timed.rolldice")
//    @Counted(value = "endpoint.counted.rolldice")
    @GetMapping("/rolldice")
    public List<Integer> index(@RequestParam("player") Optional<String> player,
                               @RequestParam("rolls") Optional<Integer> rolls) {

//        Span span = tracer.spanBuilder("RollController.index").startSpan();

////        // Iniciar timer
////        long startTime = System.currentTimeMillis();
//
//        // Incrementar contador de requests
//        requestsCounter.add(1);

//        try (var scope = span.makeCurrent()) {
        try{

            Thread.sleep(100);
            sampleService.someMethod();
            if (!rolls.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing rolls parameter", null);
            }

            List<Integer> result = new Dice(1, 6).rollTheDice(rolls.get());

            if (player.isPresent()) {
                logger.info("{} is rolling the dice: {}", player.get(), result);
            } else {
                logger.info("Anonymous player is rolling the dice: {}", result);
//                anonymousRequestsCounter.add(
//                        1,
//                        Attributes.of(AttributeKey.stringKey("result"), result.toString(),
//                        AttributeKey.stringKey("rolls"), rolls.get().toString())
//                );
            }

            // Finalizar timer e gravar duração
//            long duration = System.currentTimeMillis() - startTime;
//            requestDurationTimer.record(duration);

//            span.setAttribute("player", player.orElse("Anonymous"));
//            span.setAttribute("rolls", rolls.get());
//            span.setAttribute("result", result.toString());

            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return List.of();
        } finally {
//            span.end();
        }
    }
}
