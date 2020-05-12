package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.entity.TrafficLight;
import dse.grp20.actorsimulator.exception.InvalidTrafficLightException;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.repository.ITrafficLightRepository;
import dse.grp20.actorsimulator.service.ITrafficLightRegistryService;
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
    public void delete(TrafficLightDTO trafficLightDTO) throws NotFoundException {
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
    public TrafficLightDTO find(Long id) throws NotFoundException {
        TrafficLight trafficLight = trafficLightRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(trafficLight,TrafficLightDTO.class);
    }
}
