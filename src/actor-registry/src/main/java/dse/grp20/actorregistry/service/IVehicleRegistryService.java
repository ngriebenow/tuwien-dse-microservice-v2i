package dse.grp20.actorregistry.service;

import dse.grp20.actorregistry.dao.Vehicle;
import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;

public interface IVehicleRegistryService {

    void delete(Vehicle vehicle) throws NotFoundException;

    void add(Vehicle vehicle) throws InvalidVehicleException;

    Vehicle find(Long id) throws NotFoundException;

}
