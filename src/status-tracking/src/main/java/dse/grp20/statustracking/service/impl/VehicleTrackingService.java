package dse.grp20.statustracking.service.impl;

import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.repositories.IVehicleStatusRepository;
import dse.grp20.statustracking.service.IVehicleTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleTrackingService implements IVehicleTrackingService {

    @Autowired
    private IVehicleStatusRepository vehicleStatusRepository;

    @Override
    public void updateVehicle(VehicleStatusDTO vehicleStatus) {
        this.vehicleStatusRepository.insert(this.convertDTOtoEntity(vehicleStatus));
    }

    @Override
    public VehicleStatus getVehicleStatus(String id) {
        Optional<VehicleStatus> vehicleStatus = this.vehicleStatusRepository.findById(id);
        return vehicleStatus.isPresent() ? vehicleStatus.get() : null;
    }

    @Override
    public List<VehicleStatusDTO> findAllLatest() {
        return null;
    }

    @Override
    public VehicleStatusDTO findByIdLatest(String id) {
        return null;
    }

    private VehicleStatus convertDTOtoEntity (VehicleStatusDTO dto) {
        return new VehicleStatus(dto);
    }
}
