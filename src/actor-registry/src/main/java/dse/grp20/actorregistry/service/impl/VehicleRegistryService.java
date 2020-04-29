package dse.grp20.actorregistry.service.impl;

import dse.grp20.actorregistry.dao.Vehicle;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.repository.VehicleRepository;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class VehicleRegistryService implements IVehicleRegistryService {

    @Autowired
    VehicleRepository vehicleRepository;


    @Override
    public void delete(Vehicle vehicle) throws NotFoundException {
        find(vehicle.getId());
        vehicleRepository.delete(vehicle);
    }

    @Override
    public void add(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle find(Long id) throws NotFoundException {
        return vehicleRepository.findById(id.toString()).orElseThrow(NotFoundException::new);
    }
}
