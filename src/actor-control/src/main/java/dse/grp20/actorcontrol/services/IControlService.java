package dse.grp20.actorcontrol.services;

import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleControlDTO;

import java.util.List;

public interface IControlService {

    void controlVehicles(List<VehicleControlDTO> vehicleControlDTOList);

    void controlTrafficLights(List<TrafficLightStatusDTO> trafficLightStatusDTOList);
}
