package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.exception.InvalidVehicleException;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.common.dto.VehicleDTO;

public interface IVehicleRegistryService {

    void delete(VehicleDTO vehicle) throws NotFoundException;

    void register(VehicleDTO vehicle) throws InvalidVehicleException;

    VehicleDTO find(String id) throws NotFoundException;

}
