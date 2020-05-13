package dse.grp20.actorregistry.service.impl;

import dse.grp20.actorregistry.entity.TrafficLight;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import dse.grp20.actorregistry.exception.InvalidTrafficLightException;
import dse.grp20.actorregistry.repository.ITrafficLightRepository;
import dse.grp20.common.dto.TrafficLightDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrafficLightRegistryService implements ITrafficLightRegistryService {

    @Autowired
    ITrafficLightRepository trafficLightRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void delete(TrafficLightDTO trafficLightDTO) throws dse.grp20.actorregistry.exception.NotFoundException {
        TrafficLight trafficLight = modelMapper.map(trafficLightDTO,TrafficLight.class);
        find(trafficLight.getId());
        trafficLightRepository.delete(trafficLight);
    }

    @Override
    public void register(TrafficLightDTO trafficLightDTO) throws InvalidTrafficLightException {
        TrafficLight trafficLight = modelMapper.map(trafficLightDTO,TrafficLight.class);
        trafficLightRepository.save(trafficLight);
    }

    @Override
    public TrafficLightDTO find(Long id) throws dse.grp20.actorregistry.exception.NotFoundException {
        TrafficLight trafficLight = trafficLightRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(trafficLight,TrafficLightDTO.class);
    }
}
