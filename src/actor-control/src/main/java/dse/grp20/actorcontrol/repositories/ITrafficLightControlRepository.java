package dse.grp20.actorcontrol.repositories;

import dse.grp20.actorcontrol.entities.TrafficLightControl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITrafficLightControlRepository extends MongoRepository<TrafficLightControl, Long> {

}
