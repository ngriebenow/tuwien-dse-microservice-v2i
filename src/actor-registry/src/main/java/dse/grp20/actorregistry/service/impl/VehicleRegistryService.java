package dse.grp20.actorregistry.service.impl;

import dse.grp20.actorregistry.entity.Vehicle;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.repository.IVehicleRepository;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import dse.grp20.common.dto.VehicleDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This class handles incoming CRUD requests and delegates them to the repository.
 */
@Component
public class VehicleRegistryService implements IVehicleRegistryService {

    @Autowired
    IVehicleRepository vehicleRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void delete(VehicleDTO vehicleDTO) throws NotFoundException {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        find(vehicle.getVin());
        vehicleRepository.delete(vehicle);
    }

    @Override
    public void register(VehicleDTO vehicleDTO) {
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        vehicleRepository.save(vehicle);
    }

    @Override
    public VehicleDTO find(String id) throws NotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(vehicle, VehicleDTO.class);
    }

    @Override
    public List<VehicleDTO> findAll() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        Type listType = new TypeToken<List<Vehicle>>() {}.getType();
        return modelMapper.map(vehicles, listType);
    }
}
