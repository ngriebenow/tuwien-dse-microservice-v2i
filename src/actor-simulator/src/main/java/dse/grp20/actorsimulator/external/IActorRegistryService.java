package dse.grp20.actorsimulator.external;

import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.VehicleDTO;

public interface IActorRegistryService {

    void registerVehicle(VehicleDTO vehicleDTO);

    void registerTrafficLight(TrafficLightDTO trafficLightDTO);
}
