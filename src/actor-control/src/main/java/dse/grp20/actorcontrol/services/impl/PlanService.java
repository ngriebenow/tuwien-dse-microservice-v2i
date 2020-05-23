package dse.grp20.actorcontrol.services.impl;

import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.actorcontrol.services.IPlanService;
import dse.grp20.common.dto.TrafficLightPlanDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PlanService implements IPlanService {

    @Autowired
    private IControlService controlService;

    @Override
    public void planVehicle(List<TrafficLightStatusDTO> trafficLightStatusDTOList, // all future stati from one trafficlight
                            List<VehicleStatusDTO> vehicleStatusDTOList) // all scanned vehicles from that trafficlight
    {

    }

    @Override
    public void planTrafficLight(TrafficLightPlanDTO trafficLightPlanDTO) {

    }
}
