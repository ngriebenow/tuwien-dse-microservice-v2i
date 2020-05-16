package dse.grp20.actorcontrol;

import dse.grp20.actorcontrol.entities.VehicleControl;
import dse.grp20.actorcontrol.repositories.IVehicleControlRepository;
import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.common.dto.VehicleDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class VehicleControlIntegrationTest {

    @Autowired
    private IControlService controlService;
    @Autowired
    private IVehicleControlRepository vehicleControlRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static long WAITING_TIME = 300;

    private static String QUEUE_VEHICLE_PLAN = "vehicle.plan";
    private static String QUEUE_VEHICLE_CONTROL = "vehicle.control";

    private static VehicleDTO VEHICLE1;
    private static VehicleStatusDTO VEHICLE_STATUS1;

    @BeforeAll
    static void initAll(){
        VEHICLE1 = new VehicleDTO();
        VEHICLE1.setId("v1");
        VEHICLE1.setName("vehicle1");
        VEHICLE_STATUS1 = new VehicleStatusDTO();
        VEHICLE_STATUS1.setVehicle(VEHICLE1);
    }

    @AfterEach
    public void clearDB() {
        vehicleControlRepository.deleteAll();
    }

    @Test
    public void testControlVehicle_calledDirectly_shouldSaveControlAdvice() throws InterruptedException, NotFoundException{
        assertThrows(NotFoundException.class, () -> vehicleControlRepository.findById(VEHICLE1.getId()).orElseThrow(NotFoundException::new));
        List<VehicleStatusDTO> vehicleStatusDTOList = new ArrayList<>();
        vehicleStatusDTOList.add(VEHICLE_STATUS1);
        controlService.controlVehicles(vehicleStatusDTOList);

        VehicleControl vehicleControl = vehicleControlRepository.findById(VEHICLE1.getId()).orElseThrow(NotFoundException::new);
        assertNotNull(vehicleControl);
        assertEquals(VEHICLE1.getId(), vehicleControl.getVehicleId());
        assertEquals(50.0, vehicleControl.getSpeed());

        // Clear queue for tests coming after
        rabbitTemplate.receive(QUEUE_VEHICLE_CONTROL);
    }

    @Test
    public void testControlVehicle_calledByQueue_shouldSaveControlAdvice() throws InterruptedException, NotFoundException{
        assertThrows(NotFoundException.class, () -> vehicleControlRepository.findById(VEHICLE1.getId()).orElseThrow(NotFoundException::new));
        List<VehicleStatusDTO> vehicleStatusDTOList = new ArrayList<>();
        vehicleStatusDTOList.add(VEHICLE_STATUS1);
        rabbitTemplate.convertAndSend(QUEUE_VEHICLE_PLAN, vehicleStatusDTOList);

        Thread.sleep(WAITING_TIME);
        VehicleControl vehicleControl = vehicleControlRepository.findById(VEHICLE1.getId()).orElseThrow(NotFoundException::new);
        assertNotNull(vehicleControl);
        assertEquals(VEHICLE1.getId(), vehicleControl.getVehicleId());
        assertEquals(50.0, vehicleControl.getSpeed());

        // Clear queue for tests coming after
        rabbitTemplate.receive(QUEUE_VEHICLE_CONTROL);
    }

    @Test
    public void testControlVehicle_sendVehicleStatus_shouldPublishControlAdvice() throws InterruptedException, NotFoundException {
        List<VehicleStatusDTO> vehicleStatusDTOList = new ArrayList<>();
        vehicleStatusDTOList.add(VEHICLE_STATUS1);
        rabbitTemplate.convertAndSend(QUEUE_VEHICLE_PLAN, vehicleStatusDTOList);

        Thread.sleep(WAITING_TIME);

        List<VehicleControl> vehicleControls = (List<VehicleControl>) rabbitTemplate.receiveAndConvert(QUEUE_VEHICLE_CONTROL);
        assertNotNull(vehicleControls);
    }

}
