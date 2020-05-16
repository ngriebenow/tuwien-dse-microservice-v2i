package dse.grp20.actorcontrol;

import dse.grp20.actorcontrol.entities.TrafficLightControl;
import dse.grp20.actorcontrol.repositories.ITrafficLightControlRepository;
import dse.grp20.actorcontrol.services.IControlService;
import dse.grp20.common.dto.*;
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
class TrafficLightControlIntegrationTest {

    @Autowired
    private IControlService controlService;
    @Autowired
    private ITrafficLightControlRepository trafficLightControlRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static long WAITING_TIME = 300;

    private static String QUEUE_TRAFFICLIGHT_CONTROL = "trafficlight.control";

    private static TrafficLightDTO TRAFFICLIGHT1;
    private static TrafficLightStatusDTO TRAFFICLIGHT_STATUS1;
    private static TrafficLightControl TRAFFICLIGHT_CONTROL;

    @BeforeAll
    static void initAll(){
        TRAFFICLIGHT1 = new TrafficLightDTO();
        TRAFFICLIGHT1.setId(1L);
        TRAFFICLIGHT1.setLocation(new GeoDTO(3.,4.));
        TRAFFICLIGHT1.setScanRadius(30.);
        TRAFFICLIGHT_STATUS1 = new TrafficLightStatusDTO();
        TRAFFICLIGHT_STATUS1.setTrafficLight(TRAFFICLIGHT1);
        TRAFFICLIGHT_STATUS1.setLightDTO(LightDTO.GREEN);
        TRAFFICLIGHT_STATUS1.setFrom(1L);
        TRAFFICLIGHT_CONTROL = new TrafficLightControl();
        TRAFFICLIGHT_CONTROL.setTrafficLight(TRAFFICLIGHT1);
        TRAFFICLIGHT_CONTROL.setLightDTO(LightDTO.GREEN);
        TRAFFICLIGHT_CONTROL.setFrom(1L);
    }

    @AfterEach
    public void clearDB() {
        trafficLightControlRepository.deleteAll();
    }

    @Test
    public void testControlVehicle_calledDirectly_shouldSaveControlAdvice() {
        assertTrue(() -> trafficLightControlRepository.findAll().isEmpty());
        List<TrafficLightStatusDTO> trafficLightStatusDTOList = new ArrayList<>();
        trafficLightStatusDTOList.add(TRAFFICLIGHT_STATUS1);
        controlService.controlTrafficLights(trafficLightStatusDTOList);

        List<TrafficLightControl> trafficLightControlList = trafficLightControlRepository.findAll();
        assertNotNull(trafficLightControlList);
        assertEquals(TRAFFICLIGHT_CONTROL.getTrafficLight().getId(), trafficLightControlList.get(0).getTrafficLight().getId());

        // Clear queue for tests coming after
        rabbitTemplate.receive(QUEUE_TRAFFICLIGHT_CONTROL);
    }

    @Test
    public void testControlVehicle_sendVehicleStatus_shouldPublishControlAdvice() throws InterruptedException {
        List<TrafficLightStatusDTO> trafficLightStatusDTOList = new ArrayList<>();
        trafficLightStatusDTOList.add(TRAFFICLIGHT_STATUS1);
        controlService.controlTrafficLights(trafficLightStatusDTOList);

        Thread.sleep(WAITING_TIME);

        List<TrafficLightStatusDTO> trafficLightStatusDTOList1 = (List<TrafficLightStatusDTO>) rabbitTemplate.receiveAndConvert(QUEUE_TRAFFICLIGHT_CONTROL);
        assertNotNull(trafficLightStatusDTOList1);
    }

}
