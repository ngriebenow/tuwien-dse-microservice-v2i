package dse.grp20.statustracking.receiver;

import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableRabbit
public class TrafficLightTrackingReceiver {

    @Autowired
    private ITrafficLightTrackingService trafficLightTrackingService;

    @RabbitListener(queues = "trafficlight.update")
    public void updateTrafficLight (TrafficLightStatusDTO trafficLightStatusDTO) {
        this.trafficLightTrackingService.updateTrafficLight(trafficLightStatusDTO);
    }

    @RabbitListener(queues = "trafficlight.shedule")
    public void updateTrafficLightShedule (List<TrafficLightStatusDTO> trafficLightStatusDTO) {
        this.trafficLightTrackingService.updateTrafficLightShedule(trafficLightStatusDTO);
    }

    @RabbitListener(queues = "trafficlight.scan")
    public void scanTrafficLight (TrafficLightDTO trafficLight) {
        this.trafficLightTrackingService.scanTrafficLight(trafficLight);
    }

}
