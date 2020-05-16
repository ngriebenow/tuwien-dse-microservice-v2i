package dse.grp20.actorcontrol;

import dse.grp20.actorcontrol.entities.VehicleControl;
import dse.grp20.actorcontrol.repositories.IVehicleControlRepository;
import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.common.dto.VehicleControlDTO;
import dse.grp20.common.dto.VehicleDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    private static String QUEUE_VEHICLE_CONTROL = "vehicle.control";

    private static VehicleDTO VEHICLE1;
    private static VehicleControlDTO VEHICLE_CONTROL1;

    @BeforeAll
    static void initAll(){
        VEHICLE1 = new VehicleDTO();
        VEHICLE1.setId("v1");
        VEHICLE1.setName("vehicle1");
        VEHICLE_CONTROL1 = new VehicleControlDTO();
        VEHICLE_CONTROL1.setVehicleId(VEHICLE1.getId());
        VEHICLE_CONTROL1.setSpeed(50.0);
    }

    @AfterEach
    public void clearDB() {
        vehicleControlRepository.deleteAll();
    }

    @Test
    public void testControlVehicle_calledDirectly_shouldSaveControlAdvice() {
        assertTrue(() -> vehicleControlRepository.findAll().isEmpty());
        List<VehicleControlDTO> vehicleControlDTO = new ArrayList<>();
        vehicleControlDTO.add(VEHICLE_CONTROL1);
        controlService.controlVehicles(vehicleControlDTO);

        List<VehicleControl> vehicleControlList = vehicleControlRepository.findAll();
        assertNotNull(vehicleControlList);
        assertEquals(VEHICLE1.getId(), vehicleControlList.get(0).getVehicleId());
        assertEquals(50.0, vehicleControlList.get(0).getSpeed());

        // Clear queue for tests coming after
        rabbitTemplate.receive(QUEUE_VEHICLE_CONTROL);
    }

    @Test
    public void testControlVehicle_sendVehicleStatus_shouldPublishControlAdvice() throws InterruptedException {
        List<VehicleControlDTO> vehicleControlDTO = new ArrayList<>();
        vehicleControlDTO.add(VEHICLE_CONTROL1);
        controlService.controlVehicles(vehicleControlDTO);

        Thread.sleep(WAITING_TIME);

        List<VehicleControl> vehicleControls = (List<VehicleControl>) rabbitTemplate.receiveAndConvert(QUEUE_VEHICLE_CONTROL);
        assertNotNull(vehicleControls);
    }

}
