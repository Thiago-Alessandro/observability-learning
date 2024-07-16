package net.weg.observability_test;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObservabilityTestApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ObservabilityTestApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);

	}

}
