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

    private static final long WAITING_TIME = 300;

    private static final String QUEUE_TRAFFICLIGHT_CONTROL = "trafficlight.control";

    private static TrafficLightDTO TRAFFICLIGHT1;
    private static TrafficLightPlanDTO TRAFFICLIGHT_PLAN1;
    private static TrafficLightControlDTO TRAFFICLIGHT_CONTROL;

    private static final String vin = "v1";
    private static final double speed = 19.66;
    private static final GeoDTO vehicleLocation = new GeoDTO(3.,4.);


    @BeforeAll
    static void initAll(){
        TRAFFICLIGHT1 = new TrafficLightDTO();
        TRAFFICLIGHT1.setId(1L);
        TRAFFICLIGHT1.setLocation(new GeoDTO(3.,4.));
        TRAFFICLIGHT1.setScanRadius(30.);
        TRAFFICLIGHT_STATUS1 = new TrafficLightStatusDTO();
        TRAFFICLIGHT_STATUS1.setTrafficLightId(TRAFFICLIGHT1.getId());
        TRAFFICLIGHT_STATUS1.setLight(LightDTO.GREEN);
        TRAFFICLIGHT_STATUS1.setFrom(System.currentTimeMillis());
        TRAFFICLIGHT_CONTROL = new TrafficLightControl();
        TRAFFICLIGHT_CONTROL.setTrafficLightId(TRAFFICLIGHT1.getId());
        TRAFFICLIGHT_CONTROL.setLightDTO(LightDTO.GREEN);
        TRAFFICLIGHT_CONTROL.setFrom(System.currentTimeMillis());
    }

    @AfterEach
    public void clearDB() {
        trafficLightControlRepository.deleteAll();
    }

    @Test
    public void testControlVehicle_calledDirectly_shouldSaveControlAdvice() {
        assertTrue(() -> trafficLightControlRepository.findAll().isEmpty());
        List<TrafficLightControlDTO> trafficLightControlDTOList = new ArrayList<>();
        trafficLightControlDTOList.add(TRAFFICLIGHT_CONTROL);
        controlService.controlTrafficLights(trafficLightControlDTOList);

        List<TrafficLightControl> trafficLightControlDTOList1 = trafficLightControlRepository.findAll();
        assertNotNull(trafficLightControlDTOList1);
        assertEquals(TRAFFICLIGHT_CONTROL.getTrafficLightId(), trafficLightControlDTOList1.get(0).getTrafficLightId());

        // Clear queue for tests coming after
        rabbitTemplate.receive(QUEUE_TRAFFICLIGHT_CONTROL);
    }

    @Test
    public void testControlVehicle_sendVehicleStatus_shouldPublishControlAdvice() throws InterruptedException {
        List<TrafficLightControlDTO> trafficLightControlDTOList = new ArrayList<>();
        trafficLightControlDTOList.add(TRAFFICLIGHT_CONTROL);
        controlService.controlTrafficLights(trafficLightControlDTOList);

        Thread.sleep(WAITING_TIME);

        List<TrafficLightControlDTO> trafficLightControlDTOList1 = (List<TrafficLightControlDTO>) rabbitTemplate.receiveAndConvert(QUEUE_TRAFFICLIGHT_CONTROL);
        assertNotNull(trafficLightControlDTOList1);
    }

}
