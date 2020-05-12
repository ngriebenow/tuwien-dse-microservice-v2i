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
public class ActorRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActorRegistryApplication.class, args);
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
	Queue queueVehicleDelete() {
		return new Queue("vehicle.delete", false);
	}

	@Bean
	Queue queueTrafficlightRegister() {
		return new Queue("trafficlight.register", false);
	}

	@Bean
	Queue queueTrafficlightDelete() {
		return new Queue("trafficlight.delete", false);
	}

}
