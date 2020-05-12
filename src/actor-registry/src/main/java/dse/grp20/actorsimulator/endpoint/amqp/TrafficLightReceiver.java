package dse.grp20.actorsimulator.endpoint.amqp;

import dse.grp20.actorsimulator.exception.InvalidTrafficLightException;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.service.ITrafficLightRegistryService;
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

    @RabbitListener(queues = "trafficlight.register")
    public void register(TrafficLightDTO trafficLightDTO) throws InvalidTrafficLightException {
        trafficLightRegistryService.register(trafficLightDTO);
    }

}
