package dse.grp20.actorregistry.service.impl;

import dse.grp20.actorregistry.dao.TrafficLight;
import dse.grp20.actorregistry.exception.InvalidTrafficLightException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.repository.ITrafficLightRepository;
import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TafficLightRegistryService implements ITrafficLightRegistryService {

    @Autowired
    ITrafficLightRepository trafficLightRepository;

    @Override
    public void delete(TrafficLight trafficLight) throws NotFoundException {
        find(trafficLight.getId());
        trafficLightRepository.delete(trafficLight);
    }

    @Override
    public void add(TrafficLight vehicle) throws InvalidTrafficLightException {
        trafficLightRepository.save(vehicle);
    }

    @Override
    public TrafficLight find(Long id) throws NotFoundException {
        return trafficLightRepository.findById(id.toString()).orElseThrow(NotFoundException::new);
    }
}
