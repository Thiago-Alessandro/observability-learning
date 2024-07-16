//package net.weg.observability_test.controller;
//
//
//import net.weg.observability_test.controller.SampleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/test")
//public class SampleController {
//
//    private final SampleService sampleService;
//
//    @Autowired
//    public SampleController(SampleService sampleService) {
//        this.sampleService = sampleService;
//    }
//
//    @GetMapping("/sample")
//    public String sampleEndpoint() {
//        return sampleService.doSomething();
//    }
//}
