package dse.grp20.actorcontrol;

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
    Queue queueNCEReact() { return new Queue("nearcrashevent.react", false); }

    @Bean
    Queue queueVehicleControl() { return new Queue("vehicle.control", false); }
    @Bean
    Queue queueTrafficLightControl() { return new Queue("trafficlight.control", false); }
}
