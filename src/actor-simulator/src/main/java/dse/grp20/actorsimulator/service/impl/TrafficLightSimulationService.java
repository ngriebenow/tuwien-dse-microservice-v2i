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
        TrafficLight trafficLight = Constants.TRAFFICLIGHT1;

        actorRegistryService.registerTrafficLight(modelMapper.map(trafficLight, TrafficLightDTO.class));

        TrafficLightStatus status = new TrafficLightStatus();
        status.setLight(Light.RED);
        status.setFrom(timeService.getTime());
        TrafficLightSimulator ts1 = new TrafficLightSimulator(timeService, statusTrackingService, trafficLight, status);

        simulators.put(trafficLight.getId(), ts1);

        Thread t = new Thread(ts1::simulate);
        t.start();
    }

    @Override
    public void controlTrafficLight(TrafficLightControlDTO controlDTO) {
        simulators.get(controlDTO.getTrafficLightId()).receiveControlStatus(controlDTO);
    }
}
