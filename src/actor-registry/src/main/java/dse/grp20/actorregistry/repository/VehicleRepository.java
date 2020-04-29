package dse.grp20.actorregistry.repository;

import dse.grp20.actorregistry.dao.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

}
