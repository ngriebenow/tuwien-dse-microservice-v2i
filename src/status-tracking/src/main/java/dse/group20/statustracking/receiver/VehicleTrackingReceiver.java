package dse.group20.statustracking.receiver;

import dse.group20.statustracking.service.IVehicleTrackingService;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class VehicleTrackingReceiver {

    @Autowired
    private IVehicleTrackingService vehicleTrackingService;

    @RabbitListener(queues = "vehicle.update")
    public void update(VehicleStatusDTO vehicleStatusDTO) {
        this.vehicleTrackingService.updateVehicle(vehicleStatusDTO);
    }
}
