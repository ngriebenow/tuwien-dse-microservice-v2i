package dse.grp20.actorregistry.service;

import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.exception.InvalidTrafficLightException;
import dse.grp20.common.dto.TrafficLightDTO;

public interface ITrafficLightRegistryService {

    void delete(TrafficLightDTO vehicle) throws dse.grp20.actorregistry.exception.NotFoundException;

    void register(TrafficLightDTO vehicle) throws InvalidTrafficLightException;

    TrafficLightDTO find(Long id) throws NotFoundException;

}
