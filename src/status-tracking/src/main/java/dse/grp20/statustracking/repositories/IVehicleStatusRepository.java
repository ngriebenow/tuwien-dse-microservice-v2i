package dse.grp20.statustracking.repositories;

import dse.grp20.statustracking.entities.VehicleStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IVehicleStatusRepository extends MongoRepository<VehicleStatus, String> {

    //Should return all trafficlights in radius.
//    @Query("{ 'location' : { $geoWithin : { $centerSphere : [ [?0 , ?1 ], ?2 / 6378.1 ]}}}")
//    @Query("{ 'vehicle.id' : 'Vehicle1'}")
    @Query("{ 'location' : { $geoWithin : { $centerSphere : [ [48.185697 , 15.754987 ], 0.2 / 6378.1 ] } } }")
    List<VehicleStatus> scanTrafficLightRadius(Double longitude, Double latitude, Double scanRadius);
}
