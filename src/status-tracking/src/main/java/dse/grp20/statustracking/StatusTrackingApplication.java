package dse.grp20.statustracking;

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
public class StatusTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusTrackingApplication.class, args);
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
    Queue queueVehicleUpdate() {
        return new Queue("vehicle.update", false);
    }

    @Bean
    Queue queueVehiclePlan() { return new Queue("vehicle.plan", false); }

    @Bean
    Queue queueTrafficlightUpdate() {
        return new Queue("trafficlight.update", false);
    }

    @Bean
    Queue queueTrafficlightShedule() { return new Queue("trafficlight.shedule", false); }

    @Bean
    Queue queueTrafficlightScan() { return new Queue("trafficlight.scan", false); }

    @Bean
    Queue queueTrafficlightPlan() { return new Queue("trafficlight.plan", false); }

    @Bean
    Queue queueNearCrashEvent() { return new Queue("nearcrashevent.emit", false); }
}
