package dse.grp20.actorcontrol.endpoint.amqp;


import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableRabbit
public class ControlReceiver {

    @Autowired
    IControlService controlService;

    @RabbitListener(queues = "vehicle.plan")
    public void controlVehicle(List<VehicleStatusDTO> vehicleStatusDTOList,
                               List<TrafficLightStatusDTO> trafficLightStatusDTOList) {
        // ToDo implement
    }

    @RabbitListener(queues = "nearcrashevent.react")
    public void controlTrafficLight(VehicleStatusDTO vehicleStatusDTO,
                                    List<TrafficLightStatusDTO> trafficLightStatusDTOList) {
        // ToDo implement
    }

}
