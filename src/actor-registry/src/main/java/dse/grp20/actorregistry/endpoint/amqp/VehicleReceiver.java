package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import dse.grp20.common.dto.VehicleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class VehicleReceiver {

    @Autowired
    IVehicleRegistryService vehicleRegistryService;

    private static Logger LOGGER = LoggerFactory.getLogger(VehicleReceiver.class);

    @RabbitListener(queues = "vehicle.delete")
    public void delete(VehicleDTO vehicleDTO){
        try {
            vehicleRegistryService.delete(vehicleDTO);
        } catch (NotFoundException e) {
            LOGGER.warn("could not delete vehicle " + vehicleDTO);
        }
    }

    @RabbitListener(queues = "vehicle.register")
    public void register(VehicleDTO vehicleDTO) {
        try {
            vehicleRegistryService.register(vehicleDTO);
        } catch (InvalidVehicleException e) {
            LOGGER.warn("could not register " + vehicleDTO);
        }
    }

}
