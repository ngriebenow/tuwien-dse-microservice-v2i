package dse.grp20.actorsimulator.external;

import dse.grp20.actorsimulator.entity.TrafficLightStatus;
import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;

import java.util.List;

public interface IStatusTrackingService {

    void updateVehicle(VehicleStatusDTO vehicleStatusDTO);

    void updateTrafficLightSchedule(List<TrafficLightStatusDTO> trafficLightStati);

    void updateTrafficLight(TrafficLightStatusDTO trafficLightStatusDTO);

    void emitNearCrashEvent(NearCrashEventDTO nearCrashEventDTO);

    void scanTrafficLight(TrafficLightDTO trafficLightDTO);
}
