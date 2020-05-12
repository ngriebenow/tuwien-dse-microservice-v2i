package dse.grp20.actorsimulator.repository;

import dse.grp20.actorsimulator.entity.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IVehicleRepository extends MongoRepository<Vehicle, String> {

}
