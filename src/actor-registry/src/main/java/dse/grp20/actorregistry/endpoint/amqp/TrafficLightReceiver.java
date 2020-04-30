package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import dse.grp20.common.dto.TrafficLightDTO;
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
    public void delete(TrafficLightDTO trafficLightDTO) throws NotFoundException {
        trafficLightRegistryService.delete(trafficLightDTO);
    }

}
