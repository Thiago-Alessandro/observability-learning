package net.weg.observability_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ObservabilityTestApplication {

	public static void main(String[] args) {
		new SpringApplication(ObservabilityTestApplication.class).run(args);
	}

}
