package dse.grp20.statustracking;

import dse.grp20.common.dto.LightDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.service.ITimeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrafficLightTrackingIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ITimeService timeService;

    private static TrafficLightDTO trafficLight1;
    private static TrafficLightDTO trafficLight2;
    private static TrafficLightDTO trafficLight3;


    private static TrafficLightStatusDTO trafficLight1_status1;
    private static TrafficLightStatusDTO trafficLight1_status2;
    private static TrafficLightStatusDTO trafficLight1_status3;

    private static TrafficLightStatusDTO trafficLight2_status1;
    private static TrafficLightStatusDTO trafficLight2_status2;
    private static TrafficLightStatusDTO trafficLight2_status3;
    private static TrafficLightStatusDTO trafficLight2_status4;
    private static TrafficLightStatusDTO trafficLight2_status5;
    private static TrafficLightStatusDTO trafficLight2_status6;
    private static TrafficLightStatusDTO trafficLight2_status7;
    private static TrafficLightStatusDTO trafficLight2_status8;
    private static TrafficLightStatusDTO trafficLight2_status9;
    private static TrafficLightStatusDTO trafficLight2_status10;



    private static TrafficLightStatusDTO trafficLight3_status1;
    private static TrafficLightStatusDTO trafficLight3_status2;

    public void clear() {
        while (this.rabbitTemplate.receive("trafficlight.update") != null) {}
        while (this.rabbitTemplate.receive("trafficlight.shedule") != null) {}
    }

    @BeforeAll
    public static void initAll(@Autowired ITimeService timeService) {
        timeService.setTime(System.currentTimeMillis(), 1);
    }

    @BeforeEach
    public void init () {
        this.clear();
        this.setupAndQueueData();
    }

    private void setupAndQueueData() {
        this.mongoTemplate.dropCollection("TrafficLightStatus");

        trafficLight1 = TestUtils.createTrafficLight(1, null, null);
        trafficLight2 = TestUtils.createTrafficLight(2, null, null);
        trafficLight3 = TestUtils.createTrafficLight(3, null, null);

        trafficLight1_status1 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, this.timeService.getTime() - 10000000);
        trafficLight1_status2 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.GREEN, this.timeService.getTime() + 10000000);

        trafficLight2_status1 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime() - 10000000);
        trafficLight2_status2 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, this.timeService.getTime() + 10000000);
        trafficLight2_status3 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, this.timeService.getTime() + 20000000);


        trafficLight3_status1 = TestUtils.createTrafficLightStatus(trafficLight3.getId(), LightDTO.RED, this.timeService.getTime() - 10000000);
        trafficLight3_status2 = TestUtils.createTrafficLightStatus(trafficLight3.getId(), LightDTO.GREEN, this.timeService.getTime() + 10000000);


        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status1), "TrafficLightStatus");
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status2), "TrafficLightStatus");

        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status1), "TrafficLightStatus");
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status2), "TrafficLightStatus");
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status3), "TrafficLightStatus");


        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight3_status1), "TrafficLightStatus");
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight3_status2), "TrafficLightStatus");
    }


    @Test
    public void testUpdateTrafficLight_shouldTrigger () throws InterruptedException {

        trafficLight1_status3 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, this.timeService.getTime());
        this.rabbitTemplate.convertAndSend("trafficlight.update", trafficLight1_status3);

        Thread.sleep(1000);

        // now trafficLight1_status2 should be deleted everything else should still exist
        List<TrafficLightStatus> persistedTrafficLightStati = this.mongoTemplate.findAll(TrafficLightStatus.class);
        assertEquals(7, persistedTrafficLightStati.size());

    }

    @Test
    public void testUpdateTrafficLightShedule_shouldTrigger () throws InterruptedException {


        trafficLight2_status4 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime());
        trafficLight2_status5 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, this.timeService.getTime() + 10000000);
        trafficLight2_status6 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime() + 20000000);
        trafficLight2_status7 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, this.timeService.getTime() + 30000000);
        trafficLight2_status8 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime() + 40000000);
        trafficLight2_status9 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, this.timeService.getTime() + 50000000);
        trafficLight2_status10 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime() + 60000000);

        List<TrafficLightStatusDTO> shedule = Arrays.asList(trafficLight2_status4,trafficLight2_status5
                ,trafficLight2_status6,trafficLight2_status7,trafficLight2_status8,trafficLight2_status9
                ,trafficLight2_status10);

        this.rabbitTemplate.convertAndSend("trafficlight.shedule", shedule);

        Thread.sleep(1000);

        List<TrafficLightStatus> persistedTrafficLightStati = this.mongoTemplate.findAll(TrafficLightStatus.class);
        assertEquals(12, persistedTrafficLightStati.size());

    }
}
