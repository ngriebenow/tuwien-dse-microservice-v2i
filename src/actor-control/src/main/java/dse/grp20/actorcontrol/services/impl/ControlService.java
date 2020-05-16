package dse.grp20.actorcontrol.services.impl;

import dse.grp20.actorcontrol.entities.VehicleControl;
import dse.grp20.actorcontrol.repositories.IVehicleControlRepository;
import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.common.dto.VehicleControlDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class ControlService implements IControlService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IVehicleControlRepository vehicleControlRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void controlVehicles(List<VehicleStatusDTO> vehicleStatusDTOList) {
        // ToDo integration dummy data - implement plan logic - calculate speed
        List<VehicleControlDTO> vehicleControlDTOList = new ArrayList<>();
        for (VehicleStatusDTO v : vehicleStatusDTOList) {
            VehicleControl vehicleControl = new VehicleControl();
            vehicleControl.setVehicleId(v.getVehicle().getId());
            vehicleControl.setName(v.getVehicle().getName());
            vehicleControl.setSpeed(50.0);
            vehicleControl.setTimestamp(new Date().toString());
            vehicleControlRepository.save(vehicleControl);

            VehicleControlDTO vehicleControlDTO = modelMapper.map(vehicleControl, VehicleControlDTO.class);
            vehicleControlDTOList.add(vehicleControlDTO);
        }
        rabbitTemplate.convertAndSend("vehicle.control", vehicleControlDTOList);
    }

    @Override
    public void controlTrafficLights() {
        // ToDo implement
        throw new NotImplementedException();
    }

    @Override
    public void reactToNCE() {
        // ToDo implement
        throw new NotImplementedException();
    }
}
