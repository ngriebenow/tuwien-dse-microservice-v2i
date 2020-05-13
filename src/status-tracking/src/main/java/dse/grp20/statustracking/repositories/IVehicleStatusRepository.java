package dse.grp20.statustracking.repositories;

import dse.grp20.statustracking.entities.VehicleStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IVehicleStatusRepository extends MongoRepository<VehicleStatus, String> {
}
