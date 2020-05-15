package dse.grp20.statustracking.repositories;

import dse.grp20.statustracking.entities.TrafficLightStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ITrafficLightStatusRepository extends MongoRepository<TrafficLightStatus, String> {

    @Query("{ $and : [{'from' : {$gte : ?0}} , {'trafficLight.id': ?1}]}")
    List<TrafficLightStatus> findFutureStatusEntries(long timeStamp, long trafficLightId);

    @Query("{'from': {$gt : ?0}}")
    List<TrafficLightStatus> findStatusEntriesInFuture(long timeStamp);

    @Query("{'trafficLight.id': ?0}")
    List<TrafficLightStatus> findEntriesOfSameTrafficLight(long trafficLightId);
}
