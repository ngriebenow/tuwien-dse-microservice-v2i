package dse.grp20.statustracking.external;

import dse.grp20.common.dto.ScanDTO;
import dse.grp20.common.dto.TrafficLightPlanDTO;

import java.util.List;

public interface IActorControlService {

    void sendTrafficLightPlan(List<TrafficLightPlanDTO> trafficLightPlan);

    void sendTrafficLightScanResult(ScanDTO scan);
}
