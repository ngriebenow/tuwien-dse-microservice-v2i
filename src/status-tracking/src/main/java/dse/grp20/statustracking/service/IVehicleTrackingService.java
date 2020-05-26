package dse.grp20.statustracking.service;

import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.entities.VehicleStatus;

import java.util.List;

public interface IVehicleTrackingService {

    void updateVehicle (VehicleStatusDTO vehicleStatus);

    VehicleStatus getVehicleStatus (String id);

    List<VehicleStatusDTO> findAllLatest();

    VehicleStatusDTO findByIdLatest(String id);
}
