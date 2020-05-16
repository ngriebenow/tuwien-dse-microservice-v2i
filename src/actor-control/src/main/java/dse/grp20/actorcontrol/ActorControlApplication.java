package dse.grp20.actorcontrol;

import dse.grp20.common.dto.TrafficLightStatusDTO;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActorControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActorControlApplication.class, args);
    }

    @Bean
    Queue queueVehiclePlan() { return new Queue("vehicle.plan", false); }
    @Bean
    Queue queueTrafficLightPlan() { return new Queue("trafficlight.plan", false); }

    @Bean
    Queue queueVehicleControl() { return new Queue("vehicle.control", false); }
    @Bean
    Queue queueTrafficLightControl() { return new Queue("trafficlight.control", false); }
}
