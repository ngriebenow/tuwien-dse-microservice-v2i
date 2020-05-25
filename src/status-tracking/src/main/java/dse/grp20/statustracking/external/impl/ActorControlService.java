package dse.grp20.statustracking.external.impl;

import dse.grp20.common.dto.ScanDTO;
import dse.grp20.common.dto.TrafficLightPlanDTO;
import dse.grp20.statustracking.external.IActorControlService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActorControlService implements IActorControlService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendTrafficLightPlan(List<TrafficLightPlanDTO> trafficLightPlans) {
        this.rabbitTemplate.convertAndSend("trafficlight.plan", trafficLightPlans);
    }

    @Override
    public void sendTrafficLightScanResult(ScanDTO scan) {
        this.rabbitTemplate.convertAndSend("vehicle.plan", scan);
    }
}
