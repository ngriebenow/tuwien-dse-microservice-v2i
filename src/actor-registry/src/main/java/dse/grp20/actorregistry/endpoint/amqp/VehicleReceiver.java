package dse.grp20.actorregistry.endpoint.amqp;

import dse.grp20.actorregistry.dao.Vehicle;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
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
    public void delete(Vehicle vehicle) throws NotFoundException {
        vehicleRegistryService.delete(vehicle);
    }

}
