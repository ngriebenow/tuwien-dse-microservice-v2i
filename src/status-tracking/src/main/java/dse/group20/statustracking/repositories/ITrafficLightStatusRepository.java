package dse.group20.statustracking.repositories;

import dse.group20.statustracking.entities.TrafficLightStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ITrafficLightStatusRepository extends MongoRepository<TrafficLightStatus, String> {

    @Query("{trafficLightStatus.from: {$gt : ?0}")
    List<TrafficLightStatus> findAllInvalidTrafficStatusEntries(Long timeStamp);
}
