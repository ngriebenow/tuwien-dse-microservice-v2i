package dse.grp20.statustracking.service.impl;

import dse.grp20.common.dto.ScanDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.repositories.ITrafficLightStatusRepository;
import dse.grp20.statustracking.repositories.IVehicleStatusRepository;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrafficLightTrackingService implements ITrafficLightTrackingService {

    @Autowired
    private ITrafficLightStatusRepository trafficLightStatusRepository;

    @Autowired
    private IVehicleStatusRepository vehicleStatusRepository;

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public void updateTrafficLight(TrafficLightStatusDTO trafficLightStatus) {

//        this.trafficLightStatusRepository.findAll().forEach(item -> {
//            System.out.println("from: " + item.getFrom());
//            System.out.println("trafficLight: " + item.getTrafficLight().getId());
//        } );
//
//        System.out.println("-------------------------------------------------------");
//        System.out.println("from: " + trafficLightStatus.getFrom());
//        System.out.println("trafficLight: " + trafficLightStatus.getTrafficLight().getId());
//
//        System.out.println("-------------------------------------------------------");
//        this.trafficLightStatusRepository.findStatusEntriesInFuture(trafficLightStatus.getFrom()).forEach(item -> {
//            System.out.println("from: " + item.getFrom());
//            System.out.println("trafficLight: " + item.getTrafficLight().getId());
//        } );
//
//        System.out.println("-------------------------------------------------------");
//        this.trafficLightStatusRepository.findEntriesOfSameTrafficLight(trafficLightStatus.getTrafficLight().getId()).forEach(item -> {
//            System.out.println("trafficLight: " + item.getTrafficLight().getId());
//        } );


        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
                .findFutureStatusEntries(trafficLightStatus.getFrom(), trafficLightStatus.getTrafficLight().getId());

        System.out.println("Search for invalid Stati found: " + stati.size());

        for (TrafficLightStatus status : stati) {
            System.out.println("id: " + status.getId());
            this.trafficLightStatusRepository.delete(status);
        }

        System.out.println("-------------------------------------------------------");
        this.trafficLightStatusRepository.findAll().forEach(item -> {
            System.out.println("id: " + item.getId());
            System.out.println("from: " + item.getFrom());
            System.out.println("trafficLight: " + item.getTrafficLight().getId());
        } );

        this.trafficLightStatusRepository.insert(this.convertDTOtoEntity(trafficLightStatus));
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

        List<VehicleStatus> vehicleInRadius = this.vehicleStatusRepository
                .scanTrafficLightRadius(trafficLight.getLocation().getLongitude(), trafficLight.getLocation().getLatitude(), trafficLight.getScanRadius());

        return new ScanDTO(this.convertTrafficLightStatusEntitiesToDTO(futureStati), this.convertVehicleStatusEntitiesToDTO(vehicleInRadius));
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

    private List<VehicleStatusDTO> convertVehicleStatusEntitiesToDTO (List<VehicleStatus> entities) {
        List<VehicleStatusDTO> dtos = new ArrayList<>();
        for (VehicleStatus entity : entities) {
            dtos.add(this.modelMapper.map(entity, VehicleStatusDTO.class));
        }
        return dtos;
    }

    private List<TrafficLightStatusDTO> convertTrafficLightStatusEntitiesToDTO (List<TrafficLightStatus> entities) {
        List<TrafficLightStatusDTO> dtos = new ArrayList<>();
        for (TrafficLightStatus entity : entities) {
            dtos.add(this.modelMapper.map(entity, TrafficLightStatusDTO.class));
        }
        return dtos;
    }

}
