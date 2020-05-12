package dse.group20.statustracking.service.impl;

import dse.group20.statustracking.entities.TrafficLightStatus;
import dse.group20.statustracking.repositories.ITrafficLightStatusRepository;
import dse.group20.statustracking.service.ITrafficLightTrackingService;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrafficLightTrackingService implements ITrafficLightTrackingService {

    @Autowired
    ITrafficLightStatusRepository trafficLightStatusRepository;

    @Override
    public void updateTrafficLight(TrafficLightStatusDTO trafficLightStatus) {
        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
                .findAllInvalidTrafficStatusEntries(trafficLightStatus.getFrom());

        this.trafficLightStatusRepository.deleteAll(stati);
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

        List<TrafficLightStatus> stati = this.trafficLightStatusRepository
                .findAllInvalidTrafficStatusEntries(earliest.getFrom());

        this.trafficLightStatusRepository.deleteAll(stati);
        this.trafficLightStatusRepository.insert(this.convertDTOListToEntites(trafficLightStatuses));

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
}
