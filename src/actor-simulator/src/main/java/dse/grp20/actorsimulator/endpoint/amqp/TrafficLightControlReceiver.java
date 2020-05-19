package dse.grp20.actorsimulator.endpoint.amqp;

import dse.grp20.actorsimulator.service.ITrafficLightSimulationService;
import dse.grp20.common.dto.TrafficLightControlDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

public class TrafficLightControlReceiver {

    @Autowired
    private ITrafficLightSimulationService trafficLightSimulationService;

    @RabbitListener(queues = "trafficlight.control")
    public void controlTrafficLight(TrafficLightControlDTO trafficLightControlDTO) {
        trafficLightSimulationService.controlTrafficLight(trafficLightControlDTO);
    }

}
