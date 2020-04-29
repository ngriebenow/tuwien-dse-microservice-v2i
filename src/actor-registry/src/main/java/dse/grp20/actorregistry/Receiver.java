package dse.grp20.actorregistry;

import dse.grp20.actorregistry.entity.Vehicle;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@EnableRabbit
public class Receiver {

    @RabbitListener(queues = "vehicle.delete")
    public void delete(Vehicle vehicle) {
        System.out.println("received " + vehicle.getId());
    }

}
