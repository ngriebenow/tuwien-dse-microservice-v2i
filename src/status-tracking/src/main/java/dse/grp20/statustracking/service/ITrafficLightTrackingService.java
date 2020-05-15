package dse.grp20.statustracking.service;

import dse.grp20.common.dto.ScanDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;

import java.util.List;

public interface ITrafficLightTrackingService {

    void updateTrafficLight(TrafficLightStatusDTO trafficLightStatus);

    TrafficLightStatusDTO getTrafficLightStatus(TrafficLightDTO trafficLight);

    void updateTrafficLightShedule(List<TrafficLightStatusDTO> trafficLightStatuses);

    ScanDTO scanTrafficLight(TrafficLightDTO trafficLight);
}
