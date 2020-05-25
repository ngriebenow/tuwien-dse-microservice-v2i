package dse.grp20.actorcontrol.services.impl;

import dse.grp20.actorcontrol.entities.TrafficLightControl;
import dse.grp20.actorcontrol.entities.VehicleControl;
import dse.grp20.actorcontrol.repositories.ITrafficLightControlRepository;
import dse.grp20.actorcontrol.repositories.IVehicleControlRepository;
import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.common.dto.TrafficLightControlDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleControlDTO;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ControlService implements IControlService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IVehicleControlRepository vehicleControlRepository;
    @Autowired
    private ITrafficLightControlRepository trafficLightControlRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void controlVehicles(List<VehicleControlDTO> vehicleControlDTOList) {
        rabbitTemplate.convertAndSend("vehicle.control", vehicleControlDTOList);
        vehicleControlDTOList.forEach(v -> vehicleControlRepository.save(modelMapper.map(v, VehicleControl.class)));
    }

    @Override
    public void controlTrafficLights(List<TrafficLightControlDTO> trafficLightControlDTOList) {
        rabbitTemplate.convertAndSend("trafficlight.control", trafficLightControlDTOList);
        trafficLightControlDTOList.forEach(t -> trafficLightControlRepository.save(modelMapper.map(t, TrafficLightControl.class)));
    }
}
