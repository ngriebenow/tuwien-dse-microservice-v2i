package dse.grp20.actorsimulator;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class ActorSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActorSimulatorApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	Queue queueVehicleRegister() {
		return new Queue("vehicle.register", false);
	}

	@Bean
	Queue queueTrafficlightRegister() {
		return new Queue("trafficlight.register", false);
	}

	@Bean
	Queue queueVehicleUpdate() {
		return new Queue("vehicle.update", false);
	}

	@Bean
	Queue queueTrafficlightUpdate() {
		return new Queue("trafficlight.update", false);
	}

	@Bean
	Queue queueVehicleControl() {
		return new Queue("vehicle.control", false);
	}

	@Bean
	Queue queueTrafficlightControl() {
		return new Queue("trafficlight.control", false);
	}


}
