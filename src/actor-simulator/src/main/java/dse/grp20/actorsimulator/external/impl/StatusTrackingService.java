package dse.grp20.actorsimulator.external.impl;

import dse.grp20.actorsimulator.external.IStatusTrackingService;
import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatusTrackingService implements IStatusTrackingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void updateVehicle(VehicleStatusDTO vehicleStatusDTO) {
        rabbitTemplate.convertAndSend("vehicle.update", vehicleStatusDTO);
    }

    @Override
    public void updateTrafficLightSchedule(List<TrafficLightStatusDTO> trafficLightStati) {
        System.out.println("updating new schedule with " + trafficLightStati.get(0));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rabbitTemplate.convertAndSend("trafficlight.schedule", new ArrayList<>(trafficLightStati));
    }

    @Override
    public void updateTrafficLight(TrafficLightStatusDTO statusDTO) {
        rabbitTemplate.convertAndSend("trafficlight.update", statusDTO);
    }

    @Override
    public void emitNearCrashEvent(NearCrashEventDTO nearCrashEventDTO) {
        rabbitTemplate.convertAndSend("nearcrashevent.emit",nearCrashEventDTO);
    }

    @Override
    public void scanTrafficLight(TrafficLightDTO trafficLightDTO) {
        rabbitTemplate.convertAndSend("trafficlight.scan", trafficLightDTO);
    }
}
