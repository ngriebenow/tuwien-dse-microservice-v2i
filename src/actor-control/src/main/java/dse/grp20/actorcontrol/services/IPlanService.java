package dse.grp20.actorcontrol.services;


import dse.grp20.common.dto.TrafficLightPlanDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;

import java.util.List;

public interface IPlanService {

    void planVehicle(List<TrafficLightStatusDTO> trafficLightStatusDTOList,
                     List<VehicleStatusDTO> vehicleStatusDTOList);

    double calculateSpeed(VehicleStatusDTO vehicleStatusDTO, List<TrafficLightStatusDTO> trafficLightStatusDTOList);

    void planTrafficLight(TrafficLightPlanDTO trafficLightPlanDTO);

    long calculateGreenphase(TrafficLightPlanDTO trafficLightPlanDTO);
}
