package dse.group20.statustracking;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class StatusTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusTrackingApplication.class, args);
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
    Queue queueTrafficlightShedule() { return new Queue("trafficlight.update", false); }

}