package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.dao.TrafficLight;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class TrafficLightReceiver {

    @Autowired
    ITrafficLightRegistryService trafficLightRegistryService;

    @RabbitListener(queues = "trafficlight.delete")
    public void delete(TrafficLight trafficLight) throws NotFoundException {
        trafficLightRegistryService.delete(trafficLight);
    }

}
