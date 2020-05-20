package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.entity.Light;
import dse.grp20.actorsimulator.entity.TrafficLight;
import dse.grp20.actorsimulator.entity.TrafficLightStatus;
import dse.grp20.actorsimulator.external.IActorRegistryService;
import dse.grp20.actorsimulator.external.IStatusTrackingService;
import dse.grp20.actorsimulator.service.Constants;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.actorsimulator.service.ITrafficLightSimulationService;
import dse.grp20.common.dto.TrafficLightControlDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TrafficLightSimulationService implements ITrafficLightSimulationService {

    @Autowired
    private IActorRegistryService actorRegistryService;

    @Autowired
    private IStatusTrackingService statusTrackingService;

    @Autowired
    private ITimeService timeService;

    private Map<Long, TrafficLightSimulator> simulators = new HashMap<>();

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void restartSimulation() {
        TrafficLight trafficlight1 = Constants.TRAFFICLIGHT1;
        actorRegistryService.registerTrafficLight(modelMapper.map(trafficlight1, TrafficLightDTO.class));
        TrafficLightStatus status1 = new TrafficLightStatus();
        status1.setLight(Light.RED);
        status1.setFrom(0);
        status1.setTrafficLightId(trafficlight1.getId());
        TrafficLightSimulator ts1 = new TrafficLightSimulator(timeService, statusTrackingService, trafficlight1, status1);
        simulators.put(trafficlight1.getId(), ts1);
        Thread t1 = new Thread(ts1::simulate);
        t1.start();

        TrafficLight trafficlight2 = Constants.TRAFFICLIGHT2;
        actorRegistryService.registerTrafficLight(modelMapper.map(trafficlight2, TrafficLightDTO.class));
        TrafficLightStatus status2 = new TrafficLightStatus();
        status2.setLight(Light.GREEN);
        status2.setFrom(0);
        status2.setTrafficLightId(trafficlight2.getId());
        TrafficLightSimulator ts2 = new TrafficLightSimulator(timeService, statusTrackingService, trafficlight2, status2);
        simulators.put(trafficlight2.getId(), ts1);
        Thread t2 = new Thread(ts2::simulate);
        //t2.start();

        TrafficLight trafficlight3 = Constants.TRAFFICLIGHT3;
        actorRegistryService.registerTrafficLight(modelMapper.map(trafficlight2, TrafficLightDTO.class));
        TrafficLightStatus status3 = new TrafficLightStatus();
        status3.setLight(Light.RED);
        status3.setFrom(10000);
        status3.setTrafficLightId(trafficlight3.getId());
        TrafficLightSimulator ts3 = new TrafficLightSimulator(timeService, statusTrackingService, trafficlight3, status3);
        simulators.put(trafficlight3.getId(), ts1);
        Thread t3 = new Thread(ts3::simulate);
        //t3.start();
    }

    @Override
    public void controlTrafficLight(TrafficLightControlDTO controlDTO) {
        simulators.get(controlDTO.getTrafficLightId()).receiveControlStatus(controlDTO);
    }

    @Override
    public void stopSimulation() {
        simulators.entrySet().forEach(e -> {
            try {
                e.getValue().stop();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }
}
