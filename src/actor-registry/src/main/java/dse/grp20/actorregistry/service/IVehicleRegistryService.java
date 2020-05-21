package dse.grp20.actorregistry.service;

import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.common.dto.VehicleDTO;

import java.util.List;

public interface IVehicleRegistryService {

    void delete(VehicleDTO vehicle) throws dse.grp20.actorregistry.exception.NotFoundException;

    void register(VehicleDTO vehicle) throws InvalidVehicleException;

    VehicleDTO find(String id) throws NotFoundException;

    List<VehicleDTO> findAll();

}
