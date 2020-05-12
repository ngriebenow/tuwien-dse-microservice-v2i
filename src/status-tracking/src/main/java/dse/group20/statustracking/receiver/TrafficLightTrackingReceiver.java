package dse.group20.statustracking.receiver;

import dse.group20.statustracking.service.ITrafficLightTrackingService;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class TrafficLightTrackingReceiver {

    @Autowired
    private ITrafficLightTrackingService trafficLightTrackingService;

    @RabbitListener(queues = "trafficLight.update")
    public void updateTrafficLight (TrafficLightStatusDTO trafficLightStatusDTO) {
        this.trafficLightTrackingService.updateTrafficLight(trafficLightStatusDTO);
    }
}
