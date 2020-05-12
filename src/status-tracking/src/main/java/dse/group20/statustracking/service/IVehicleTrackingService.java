package dse.group20.statustracking.service;

import dse.group20.statustracking.entities.VehicleStatus;
import dse.grp20.common.dto.VehicleStatusDTO;

public interface IVehicleTrackingService {

    void updateVehicle (VehicleStatusDTO vehicleStatus);

    VehicleStatus getVehicleStatus (String id);
}
