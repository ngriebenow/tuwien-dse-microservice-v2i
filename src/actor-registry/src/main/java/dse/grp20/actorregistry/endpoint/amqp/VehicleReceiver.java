package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.entity.Vehicle;
import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import dse.grp20.common.dto.VehicleDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class VehicleReceiver {

    @Autowired
    IVehicleRegistryService vehicleRegistryService;

    @RabbitListener(queues = "vehicle.delete")
    public void delete(VehicleDTO vehicleDTO) throws NotFoundException {
        vehicleRegistryService.delete(vehicleDTO);
    }

    @RabbitListener(queues = "vehicle.register")
    public void register(VehicleDTO vehicleDTO) throws InvalidVehicleException {
        vehicleRegistryService.register(vehicleDTO);
    }

}
