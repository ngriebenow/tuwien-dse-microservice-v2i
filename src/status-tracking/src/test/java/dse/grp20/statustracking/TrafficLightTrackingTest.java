package dse.grp20.statustracking;

import dse.grp20.common.dto.LightDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
public class TrafficLightTrackingTest {

    @Autowired
    private ITrafficLightTrackingService trackingService;

    private static long WAITING_TIME = 300;
    

    private static ModelMapper modelMapper = new ModelMapper();


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


    @BeforeEach
    public void init(@Autowired MongoTemplate mongoTemplate) {

        mongoTemplate.dropCollection(TrafficLightStatus.class);

        trafficLight1 = TestUtils.createTrafficLight(1, null, null);
        trafficLight2 = TestUtils.createTrafficLight(2, null, null);
        trafficLight3 = TestUtils.createTrafficLight(3, null, null);

        trafficLight1_status1 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.RED, System.currentTimeMillis() - 10000000);
        trafficLight1_status2 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.GREEN, System.currentTimeMillis() + 10000000);

        trafficLight2_status1 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.RED, System.currentTimeMillis() - 10000000);
        trafficLight2_status2 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.GREEN, System.currentTimeMillis() + 10000000);
        trafficLight2_status3 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.GREEN, System.currentTimeMillis() + 20000000);


        trafficLight3_status1 = TestUtils.createTrafficLightStatus(trafficLight3, LightDTO.RED, System.currentTimeMillis() - 10000000);
        trafficLight3_status2 = TestUtils.createTrafficLightStatus(trafficLight3, LightDTO.GREEN, System.currentTimeMillis() + 10000000);

        mongoTemplate.save(modelMapper.map(trafficLight1_status1, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight1_status2, TrafficLightStatus.class));

        mongoTemplate.save(modelMapper.map(trafficLight2_status1, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight2_status2, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight2_status3, TrafficLightStatus.class));


        mongoTemplate.save(modelMapper.map(trafficLight3_status1, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight3_status2, TrafficLightStatus.class));
    }

    @Test
    public void testUpdateTrafficLightStatus(@Autowired MongoTemplate mongoTemplate) {

        System.out.println(mongoTemplate.findAll(TrafficLightStatus.class).size());


        trafficLight1_status3 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.RED, System.currentTimeMillis());
        this.trackingService.updateTrafficLight(trafficLight1_status3);


        // now trafficLight1_status2 should be deleted everything else should still exist
        List<TrafficLightStatus> persistedTrafficLightStati = mongoTemplate.findAll(TrafficLightStatus.class);
        assertEquals(7, persistedTrafficLightStati.size());

    }

    @Test
    public void testUpdateTrafficLightShedule(@Autowired MongoTemplate mongoTemplate) {


        trafficLight2_status4 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.RED, System.currentTimeMillis());
        trafficLight2_status5 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.GREEN, System.currentTimeMillis() + 10000000);
        trafficLight2_status6 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.RED, System.currentTimeMillis() + 20000000);
        trafficLight2_status7 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.GREEN, System.currentTimeMillis() + 30000000);
        trafficLight2_status8 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.RED, System.currentTimeMillis() + 40000000);
        trafficLight2_status9 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.GREEN, System.currentTimeMillis() + 50000000);
        trafficLight2_status10 = TestUtils.createTrafficLightStatus(trafficLight2, LightDTO.RED, System.currentTimeMillis() + 60000000);

        List<TrafficLightStatusDTO> shedule = Arrays.asList(trafficLight2_status4,trafficLight2_status5
                ,trafficLight2_status6,trafficLight2_status7,trafficLight2_status8,trafficLight2_status9
                ,trafficLight2_status10);

        this.trackingService.updateTrafficLightShedule(shedule);
        List<TrafficLightStatus> persistedTrafficLightStati = mongoTemplate.findAll(TrafficLightStatus.class);
        assertEquals(12, persistedTrafficLightStati.size());
    }
}
