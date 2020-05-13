package dse.grp20.statustracking.service;

import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.entities.VehicleStatus;

public interface IVehicleTrackingService {

    void updateVehicle (VehicleStatusDTO vehicleStatus);

    VehicleStatus getVehicleStatus (String id);
}
