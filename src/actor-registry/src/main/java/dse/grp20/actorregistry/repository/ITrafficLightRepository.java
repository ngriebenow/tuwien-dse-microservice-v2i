package dse.grp20.actorregistry.repository;

import dse.grp20.actorregistry.entity.TrafficLight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITrafficLightRepository extends MongoRepository<TrafficLight, Long> {

}
