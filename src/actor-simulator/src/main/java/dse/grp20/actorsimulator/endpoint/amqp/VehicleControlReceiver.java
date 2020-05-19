package dse.grp20.actorsimulator.endpoint.amqp;

import dse.grp20.actorsimulator.service.IVehicleSimulationService;
import dse.grp20.common.dto.VehicleControlDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleControlReceiver {


    @Autowired
    private IVehicleSimulationService vehicleSimulationService;

    @RabbitListener(queues = "vehicle.control")
    public void controlVehicle(VehicleControlDTO vehicleControlDTO) {
        vehicleSimulationService.controlVehicle(vehicleControlDTO);
    }

}
