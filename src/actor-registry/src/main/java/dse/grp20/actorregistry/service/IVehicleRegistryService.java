package dse.grp20.actorregistry.service;

import dse.grp20.actorregistry.entity.Vehicle;
import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.common.dto.VehicleDTO;

public interface IVehicleRegistryService {

    void delete(VehicleDTO vehicle) throws NotFoundException;

    void add(VehicleDTO vehicle) throws InvalidVehicleException;

    VehicleDTO find(String id) throws NotFoundException;

}