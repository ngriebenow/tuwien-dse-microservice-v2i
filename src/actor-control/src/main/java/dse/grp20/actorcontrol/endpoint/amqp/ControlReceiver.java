package dse.grp20.actorcontrol.endpoint.amqp;


import dse.grp20.actorcontrol.services.IPlanService;
import dse.grp20.common.dto.ScanDTO;
import dse.grp20.common.dto.TrafficLightPlanDTO;
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
    IPlanService planService;

    @RabbitListener(queues = "vehicle.plan")
    public void controlVehicle(ScanDTO scanDTO) {
        planService.planVehicle(scanDTO);
    }

    @RabbitListener(queues = "trafficlight.plan")
    public void controlTrafficLight(TrafficLightPlanDTO trafficLightPlanDTO) {
        planService.planTrafficLight(trafficLightPlanDTO);
    }

}
