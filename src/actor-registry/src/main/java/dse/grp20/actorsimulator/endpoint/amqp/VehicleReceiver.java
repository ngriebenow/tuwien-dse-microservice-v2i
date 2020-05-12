package dse.grp20.actorsimulator.endpoint.amqp;

import dse.grp20.actorsimulator.exception.InvalidVehicleException;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.service.IVehicleRegistryService;
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
