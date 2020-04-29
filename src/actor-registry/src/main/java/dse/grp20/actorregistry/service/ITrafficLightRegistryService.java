package dse.grp20.actorregistry.service;

import dse.grp20.actorregistry.dao.TrafficLight;
import dse.grp20.actorregistry.dao.Vehicle;
import dse.grp20.actorregistry.exception.InvalidTrafficLightException;
import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;

public interface ITrafficLightRegistryService {

    void delete(TrafficLight vehicle) throws NotFoundException;

    void add(TrafficLight vehicle) throws InvalidTrafficLightException;

    TrafficLight find(Long id) throws NotFoundException;

}
