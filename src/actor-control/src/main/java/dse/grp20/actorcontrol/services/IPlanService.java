package dse.grp20.actorcontrol.services;


import dse.grp20.common.dto.*;

import java.util.List;

public interface IPlanService {

    void planVehicle(ScanDTO scanDTO);

    double calculateSpeed(VehicleStatusDTO vehicleStatusDTO, List<TrafficLightStatusDTO> trafficLightStatusDTOList, TrafficLightDTO trafficLightDTO);

    void planTrafficLight(TrafficLightPlanDTO trafficLightPlanDTO);

    long calculateGreenphase(TrafficLightPlanDTO trafficLightPlanDTO);
}
