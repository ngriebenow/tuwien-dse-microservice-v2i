package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.exception.InvalidTrafficLightException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import dse.grp20.common.dto.TrafficLightDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class TrafficLightReceiver {

    @Autowired
    ITrafficLightRegistryService trafficLightRegistryService;

    private static Logger LOGGER = LoggerFactory.getLogger(TrafficLightReceiver.class);

    @RabbitListener(queues = "trafficlight.delete")
    public void delete(TrafficLightDTO trafficLightDTO) {
        try {
            trafficLightRegistryService.delete(trafficLightDTO);
        } catch (NotFoundException e) {
            LOGGER.warn("could not delete " + trafficLightDTO);
        }
    }

    @RabbitListener(queues = "trafficlight.register")
    public void register(TrafficLightDTO trafficLightDTO) {
        try {
            trafficLightRegistryService.register(trafficLightDTO);
        } catch (InvalidTrafficLightException e) {
            LOGGER.warn("could not register " + trafficLightDTO);
        }
    }

}
