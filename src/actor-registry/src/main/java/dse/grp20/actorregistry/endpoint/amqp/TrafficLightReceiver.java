package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.dao.TrafficLight;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class TrafficLightReceiver {

    //@RabbitListener(queues = "trafficlight.delete")
    public void delete(TrafficLight trafficLight) {
        System.out.println("received " + trafficLight.getId());
    }

}
