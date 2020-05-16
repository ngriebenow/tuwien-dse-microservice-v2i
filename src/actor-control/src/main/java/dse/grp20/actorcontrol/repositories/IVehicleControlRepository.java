package dse.grp20.actorcontrol.repositories;

import dse.grp20.actorcontrol.entities.VehicleControl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IVehicleControlRepository extends MongoRepository<VehicleControl, String> {

}
