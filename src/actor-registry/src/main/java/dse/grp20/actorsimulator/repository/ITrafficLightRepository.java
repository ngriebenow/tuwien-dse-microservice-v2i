package dse.grp20.actorsimulator.repository;

import dse.grp20.actorsimulator.entity.TrafficLight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ITrafficLightRepository extends MongoRepository<TrafficLight, Long> {

}
