package dse.grp20.statustracking.service.impl;

import com.mongodb.client.result.DeleteResult;
import dse.grp20.common.dto.*;
import dse.grp20.statustracking.entities.*;
import dse.grp20.statustracking.external.IActorControlService;
import dse.grp20.statustracking.repositories.ITrafficLightStatusRepository;
import dse.grp20.statustracking.repositories.IVehicleStatusRepository;
import dse.grp20.statustracking.service.ITimeService;
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
    private ITimeService timeService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IActorControlService actorControlService;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public void updateTrafficLight(TrafficLightStatusDTO trafficLightStatus) {

//        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
//                .findFutureStatusEntries(trafficLightStatus.getFrom(), trafficLightStatus.getTrafficLight().getId());

        List<TrafficLightStatus> invalidTrafficLightStati = this.mongoTemplate.find(new Query(Criteria
                .where("from").gte(trafficLightStatus.getFrom())
                .and("trafficLightId").is(trafficLightStatus.getTrafficLightId())), TrafficLightStatus.class, "TrafficLightStatus");


        for (TrafficLightStatus status : invalidTrafficLightStati) {
            DeleteResult dlRes = this.mongoTemplate.remove(new Query(Criteria.where("id").is(status.getId()))
                    , TrafficLightStatus.class, "TrafficLightStatus");
            System.out.println(dlRes);
        }


        this.mongoTemplate.save(this.convertDTOtoEntity(trafficLightStatus), "TrafficLightStatus");
//
//        TrafficLightStatus dto = this.convertDTOtoEntity(trafficLightStatus);
//        this.mongoTemplate.save(dto, "TrafficLightStatus");
////        this.trafficLightStatusRepository.insert(this.convertDTOtoEntity(trafficLightStatus));
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
            if (earliest == null || earliest.getFrom() > status.getFrom()) {
                earliest = status;
            }
        }

        System.out.println("from: " + earliest.getFrom());

        List<TrafficLightStatus> list = this.mongoTemplate.findAll(TrafficLightStatus.class, "TrafficLightStatus");
        list.forEach(item -> System.out.println("id: " + item.getId() + " from: " + item.getFrom()));


        List<TrafficLightStatus> invalidTrafficLightStati = this.mongoTemplate.find(new Query(Criteria
                .where("from").gte(earliest.getFrom())
                .and("trafficLightId").is(earliest.getTrafficLightId())), TrafficLightStatus.class, "TrafficLightStatus");
//        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
//                .findFutureStatusEntries(earliest.getFrom(), earliest.getTrafficLightId());
        System.out.println(invalidTrafficLightStati.size());

        this.mongoTemplate.findAllAndRemove(new Query(Criteria
                .where("from").gte(earliest.getFrom())
                .and("trafficLightId").is(earliest.getTrafficLightId())), TrafficLightStatus.class, "TrafficLightStatus");
//        this.trafficLightStatusRepository.deleteAll(stati);
        this.mongoTemplate.insert(this.convertDTOListToEntites(trafficLightStatuses), "TrafficLightStatus");
//        this.trafficLightStatusRepository.insert(this.convertDTOListToEntites(trafficLightStatuses));

    }

    @Override
    public ScanDTO scanTrafficLight(TrafficLightDTO trafficLight) {


//        List<TrafficLightStatus> futureStati = this.mongoTemplate.find(new Query(Criteria
//                .where("from").gte(System.currentTimeMillis())
//                .and("trafficLightId").is(trafficLight.getId())), TrafficLightStatus.class, "TrafficLightStatus");

        List<TrafficLightStatus> futureStati = this.mongoTemplate.find(new Query(Criteria
                .where("from").gte(this.timeService.getTime())
                .and("trafficLightId").is(trafficLight.getId())), TrafficLightStatus.class, "TrafficLightStatus");

        List<VehicleStatus> vehicleInRadius = this.mongoTemplate.find(new Query(Criteria.where("location")
                .withinSphere(new Circle(trafficLight.getLocation().getLongitude(),trafficLight.getLocation().getLatitude()
                        , trafficLight.getScanRadius() / 6378.1))
                .and("time").gt(this.timeService.getTime() - 10000)), VehicleStatus.class);

        // find NCE within radius in the last 10s
        List<NearCrashEvent> nearCrashEvents = this.mongoTemplate.find(new Query(Criteria.where("location")
                .withinSphere(new Circle(trafficLight.getLocation().getLongitude(),trafficLight.getLocation().getLatitude()
                        , trafficLight.getScanRadius() / 6378.1))
                .and("time").gt(this.timeService.getTime() - 10000)), NearCrashEvent.class);


        // only the ones that approach the traffic light!
        Map<String, VehicleStatus> vehicleIdToStatus = new HashMap<>();
        for (VehicleStatus status : vehicleInRadius) {
            VehicleStatus existingStatus = vehicleIdToStatus.get(status.getVin());
            if (existingStatus != null) {
                // check which of the Entries is more recent
                if (existingStatus.getTime() < status.getTime()) {

                    //remove old entry
                    vehicleIdToStatus.remove(existingStatus.getVin());

                    //check if new entry is approaching or not
                    if (this.isApproaching(status.getLocation(), status.getDirection(), trafficLight.getLocation())) {
                        vehicleIdToStatus.put(status.getVin(), status);
                    }
                }
            } else {
                if (this.isApproaching(status.getLocation(), status.getDirection(), trafficLight.getLocation())) {
                    vehicleIdToStatus.put(status.getVin(), status);
                }
            }
        }

        List<TrafficLightPlanDTO> trafficLightPlans = new ArrayList<>();
        for (NearCrashEvent event : nearCrashEvents) {
            VehicleStatus nceVehicle = vehicleIdToStatus.get(event.getVin());
            if (nceVehicle != null) {
                TrafficLightPlanDTO planDTO = new TrafficLightPlanDTO();
                planDTO.setTrafficLightId(trafficLight.getId());
                planDTO.setTrafficLightLocation(trafficLight.getLocation());
                planDTO.setVin(nceVehicle.getVin());
                planDTO.setVehicleLocation(this.modelMapper.map(nceVehicle.getLocation(), GeoDTO.class));
                planDTO.setSpeed(nceVehicle.getSpeed());
                trafficLightPlans.add(planDTO);
//                this.actorControlService.sendTrafficLightPlan(new TrafficLightPlanDTO(trafficLight,
//                        this.modelMapper.map(nceVehicle, VehicleStatusDTO.class)));
            }
        }

        this.actorControlService.sendTrafficLightPlan(trafficLightPlans);

        // for every NCE send a TrafficLightplan

        this.actorControlService.sendTrafficLightScanResult(new ScanDTO(this.convertTrafficLightStatusEntitiesToDTO(futureStati)
                , this.convertVehicleStatusEntitiesToDTO(vehicleIdToStatus.values())));


        return new ScanDTO(this.convertTrafficLightStatusEntitiesToDTO(futureStati)
                , this.convertVehicleStatusEntitiesToDTO(vehicleIdToStatus.values()));
    }

    @Override
    public List<TrafficLightStatusDTO> findAllLatest() {
        return null;
    }

    @Override
    public TrafficLightStatusDTO findByIdLatest(long id) {
        return null;
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

    private NearCrashEvent checkForNCEs () {
        return null;
    }

}
