package dse.grp20.actorcontrol.services;

import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;

import java.util.List;


public interface IControlService {

    void controlVehicles(List<VehicleStatusDTO> vehicleStatusDTOList);

    void controlTrafficLights();
    void reactToNCE();
}
