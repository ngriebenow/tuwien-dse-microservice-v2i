package dse.grp20.actorcontrol.endpoint.amqp;


import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Component
@EnableRabbit
public class VehicleControlReceiver {

    @Autowired
    IControlService controlService;

    @RabbitListener(queues = "vehicle.plan")
    public void controlVehicle(List<VehicleStatusDTO> vehicleStatusDTOList) {
        controlService.controlVehicles(vehicleStatusDTOList);
    }

    @RabbitListener(queues = "trafficlight.plan")
    public void controlTrafficLight() {
        // ToDo implement
        throw new NotImplementedException();
    }

}
