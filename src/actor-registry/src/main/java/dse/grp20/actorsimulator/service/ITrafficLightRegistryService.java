package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.exception.InvalidTrafficLightException;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.common.dto.TrafficLightDTO;

public interface ITrafficLightRegistryService {

    void delete(TrafficLightDTO vehicle) throws NotFoundException;

    void register(TrafficLightDTO vehicle) throws InvalidTrafficLightException;

    TrafficLightDTO find(Long id) throws NotFoundException;

}
