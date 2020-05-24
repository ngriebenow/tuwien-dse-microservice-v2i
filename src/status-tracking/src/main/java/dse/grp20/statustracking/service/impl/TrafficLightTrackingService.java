package dse.grp20.statustracking.service.impl;

import com.mongodb.client.result.DeleteResult;
import dse.grp20.common.dto.*;
import dse.grp20.statustracking.entities.Geo;
import dse.grp20.statustracking.entities.Position;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.repositories.ITrafficLightStatusRepository;
import dse.grp20.statustracking.repositories.IVehicleStatusRepository;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TrafficLightTrackingService implements ITrafficLightTrackingService {

    @Autowired
    private ITrafficLightStatusRepository trafficLightStatusRepository;

    @Autowired
    private IVehicleStatusRepository vehicleStatusRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public void updateTrafficLight(TrafficLightStatusDTO trafficLightStatus) {

//        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
//                .findFutureStatusEntries(trafficLightStatus.getFrom(), trafficLightStatus.getTrafficLight().getId());

        List<TrafficLightStatus> stati1 = this.mongoTemplate.find(new Query(Criteria
                .where("from").gte(trafficLightStatus.getFrom())
                .and("trafficLight.id").is(trafficLightStatus.getTrafficLight().getId())), TrafficLightStatus.class, "TrafficLightStatus");

//        System.out.println("Search for invalid Stati found: " + stati.size());
        System.out.println("Search for invalid Stati1 found: " + stati1.size());


//        for (TrafficLightStatus status : stati) {
//            System.out.println("id: " + status.getId());
////            this.trafficLightStatusRepository.delete(status);
////            this.mongoTemplate.remove(status);
////            this.mongoTemplate.remove(new Query(Criteria.where("id").is(status.getId())), TrafficLightStatus.class);
//        }

        for (TrafficLightStatus status : stati1) {
            System.out.println("stati1 id: " + status.getId());
//            this.trafficLightStatusRepository.delete(status);
//            this.mongoTemplate.remove(status);
            List<TrafficLightStatus> fRes = this.mongoTemplate.find(new Query(Criteria.where("id").is(status.getId())), TrafficLightStatus.class, "TrafficLightStatus");

            List<TrafficLightStatus> all = this.mongoTemplate.findAll(TrafficLightStatus.class, "TrafficLightStatus");
            System.out.println(all.size());

            for (TrafficLightStatus stat : all) {
                if (stat.getId().equals(status.getId())) {
                    System.out.println("match");
                    DeleteResult dleRes = this.mongoTemplate.remove(stat, "TrafficLightStatus");
                    System.out.println(dleRes);
                }
            }
            all.forEach(item -> System.out.println("id: " + item.getId()));

            System.out.println("--------------------------------------------");


            System.out.println(fRes.size());
            fRes.forEach(item -> System.out.println("id: " + item.getId()));

            DeleteResult dlRes = this.mongoTemplate.remove(new Query(Criteria.where("id").is(status.getId())), TrafficLightStatus.class, "TrafficLightStatus");

            System.out.println(dlRes);
        }



        System.out.println("-------------------------------------------------------");
        this.trafficLightStatusRepository.findAll().forEach(item -> {
            System.out.println("id: " + item.getId());
            System.out.println("from: " + item.getFrom());
            System.out.println("trafficLight: " + item.getTrafficLight().getId());
        } );

        TrafficLightStatus dto = this.convertDTOtoEntity(trafficLightStatus);
        this.mongoTemplate.save(dto, "TrafficLightStatus");
//        this.trafficLightStatusRepository.insert(this.convertDTOtoEntity(trafficLightStatus));
    }

    @Override
    public TrafficLightStatusDTO getTrafficLightStatus(TrafficLightDTO trafficLight) {
        return null;
    }

    @Override
    public void updateTrafficLightShedule(List<TrafficLightStatusDTO> trafficLightStatuses) {

        //find entry with the smallest from value.
        TrafficLightStatusDTO earliest = null;
        for (TrafficLightStatusDTO status : trafficLightStatuses) {
            if (earliest == null || earliest.getFrom() < status.getFrom()) {
                earliest = status;
            }
        }
//
        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
                .findFutureStatusEntries(earliest.getFrom(), earliest.getTrafficLight().getId());

        this.trafficLightStatusRepository.deleteAll(stati);
        this.trafficLightStatusRepository.insert(this.convertDTOListToEntites(trafficLightStatuses));

    }

    @Override
    public ScanDTO scanTrafficLight(TrafficLightDTO trafficLight) {

        List<TrafficLightStatus> futureStati = this.trafficLightStatusRepository
                .findFutureStatusEntries(System.currentTimeMillis(), trafficLight.getId());

        List<VehicleStatus> vehicleInRadius = this.mongoTemplate.find(new Query(Criteria.where("location")
                .withinSphere(new Circle(trafficLight.getLocation().getLongitude(),trafficLight.getLocation().getLatitude()
                        , trafficLight.getScanRadius() / 6378.1))
                .and("timeStamp").gt(System.currentTimeMillis() - 10000)), VehicleStatus.class);


        // only the ones that approach the traffic light!
        Map<String, VehicleStatus> vehicleIdToStatus = new HashMap<>();
        for (VehicleStatus status : vehicleInRadius) {
            VehicleStatus existingStatus = vehicleIdToStatus.get(status.getVehicle().getId());
            if (existingStatus != null) {
                // check which of the Entries is more recent
                if (existingStatus.getTimeStamp() < status.getTimeStamp()) {

                    //remove old entry
                    vehicleIdToStatus.remove(existingStatus.getVehicle().getId());

                    //check if new entry is approaching or not
                    if (this.isApproaching(status.getLocation(), status.getVelocity(), trafficLight.getLocation())) {
                        vehicleIdToStatus.put(status.getVehicle().getId(), status);
                    }
                }
            } else {
                if (this.isApproaching(status.getLocation(), status.getVelocity(), trafficLight.getLocation())) {
                    vehicleIdToStatus.put(status.getVehicle().getId(), status);
                }
            }
        }

        return new ScanDTO(this.convertTrafficLightStatusEntitiesToDTO(futureStati)
                , this.convertVehicleStatusEntitiesToDTO(vehicleIdToStatus.values()));
    }

    private boolean isApproaching (Geo position, Geo futurePosition, GeoDTO trafficLightPosition) {

        if (futurePosition == null || futurePosition.getCoordinates() == null
                || futurePosition.getCoordinates().length < 2) {
            return false;
        }

        Double posLat = position.getCoordinates()[1];
        Double posLong = position.getCoordinates()[0];

        Double futureLat = futurePosition.getCoordinates()[1];
        Double futureLong = futurePosition.getCoordinates()[0];

        Position pos = this.determinePosition(position, trafficLightPosition);

        if (pos == null || futureLat == null || futureLong == null) {
            return false; // in my opinion car is already passing
        }

        switch (pos) {
            case NORTH:
                if (posLat > futureLat) {
                    return true;
                }
                break;
            case SOUTH:
                if (posLat < futureLat) {
                    return true;
                }
                break;
            case WEST:
                if (posLong < futureLong) {
                    return true;
                }
                break;
            case EAST:
                if (posLong > futureLong) {
                    return true;
                }
                break;

            // Annahme: Unsere Straßen führen immer direkt zu einer Ampel?
            case NORTHEAST:
                if (posLat > futureLat && posLong > futureLong) {
                    return true;
                }
                break;
            case NORTHWEST:
                if (posLat > futureLat && posLong < futureLong) {
                    return true;
                }
                break;
            case SOUTHEAST:
                if (posLat < futureLat && posLong > futureLong) {
                    return true;
                }
                break;
            case SOUTHWEST:
                if (posLat < futureLat && posLong < futureLong) {
                    return true;
                }
                break;
        }

        return false;
    }

    private Position determinePosition (Geo position, GeoDTO trafficLightPosition) {

        Double posLat = position.getCoordinates()[1];
        Double posLong = position.getCoordinates()[0];

        if (posLong < trafficLightPosition.getLongitude()) {
            if (posLat < trafficLightPosition.getLatitude()) {
                return Position.SOUTHWEST;
            } else if (posLat > trafficLightPosition.getLatitude()) {
                return Position.NORTHWEST;
            } else {
                return Position.WEST;
            }
        } else if (posLong > trafficLightPosition.getLongitude()) {
            if (posLat < trafficLightPosition.getLatitude()) {
                return Position.SOUTHEAST;
            } else if (posLat > trafficLightPosition.getLatitude()) {
                return Position.NORTHEAST;
            } else {
                return Position.EAST;
            }
        } else {
            if (posLat < trafficLightPosition.getLatitude()) {
                return Position.SOUTH;
            } else  if (posLat > trafficLightPosition.getLatitude()) {
                return Position.NORTH;
            }
        }

        // if Position is the same
        return null;
    }

    private TrafficLightStatus convertDTOtoEntity (TrafficLightStatusDTO dto) {
        return new TrafficLightStatus(dto);
    }

    private List<TrafficLightStatus> convertDTOListToEntites (List<TrafficLightStatusDTO> dtos) {

        List<TrafficLightStatus> entities = new ArrayList<>();
        for (TrafficLightStatusDTO dto : dtos) {
            entities.add(this.convertDTOtoEntity(dto));
        }
        return entities;
    }

    private List<VehicleStatusDTO> convertVehicleStatusEntitiesToDTO (Collection<VehicleStatus> entities) {
        List<VehicleStatusDTO> dtos = new ArrayList<>();
        for (VehicleStatus entity : entities) {
            dtos.add(this.modelMapper.map(entity, VehicleStatusDTO.class));
        }
        return dtos;
    }

    private List<TrafficLightStatusDTO> convertTrafficLightStatusEntitiesToDTO (Collection<TrafficLightStatus> entities) {
        List<TrafficLightStatusDTO> dtos = new ArrayList<>();
        for (TrafficLightStatus entity : entities) {
            dtos.add(this.modelMapper.map(entity, TrafficLightStatusDTO.class));
        }
        return dtos;
    }

}
