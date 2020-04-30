package dse.grp20.actorsimulator.service;

import dse.grp20.common.dto.VehicleDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleDeletor {

    @Autowired
    private RabbitTemplate rabbitTemplate;



    public void deleteVehicle(VehicleDTO vehicleDTO) {
        rabbitTemplate.convertAndSend("vehicle.delete", vehicleDTO);
    }

}
