package dse.grp20.actorsimulator.external.impl;

import dse.grp20.actorsimulator.external.IActorRegistryService;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.VehicleDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class abstracts the actor registry service. It delegates incoming requests to the other microservice by sending a rabbitMQ message.
 */
@Component
public class ActorRegistryService implements IActorRegistryService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void registerVehicle(VehicleDTO vehicleDTO) {
        rabbitTemplate.convertAndSend("vehicle.register", vehicleDTO);
    }

    @Override
    public void registerTrafficLight(TrafficLightDTO trafficLightDTO) {
        rabbitTemplate.convertAndSend("trafficlight.register", trafficLightDTO);
    }

}
