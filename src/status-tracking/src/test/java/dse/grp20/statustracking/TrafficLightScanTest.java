package dse.grp20.statustracking;


import dse.grp20.common.dto.*;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
public class TrafficLightScanTest {

    @Autowired
    private ITrafficLightTrackingService trafficLightTrackingService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static ModelMapper modelMapper = new ModelMapper();

    private static VehicleStatusDTO vehicleStatus1;
    private static VehicleStatusDTO vehicleStatus2;
    private static VehicleStatusDTO vehicleStatus3;
    private static VehicleStatusDTO vehicleStatus4;
    private static VehicleStatusDTO vehicleStatus5;
    private static VehicleStatusDTO vehicleStatus6;

    private static TrafficLightDTO trafficLight1;
    private static TrafficLightStatusDTO trafficLight1_status1;
    private static TrafficLightStatusDTO trafficLight1_status2;
    private static TrafficLightStatusDTO trafficLight1_status3;
    private static TrafficLightStatusDTO trafficLight1_status4;
    private static TrafficLightStatusDTO trafficLight1_status5;




    @BeforeAll
    public static void init (@Autowired MongoTemplate mongoTemplate) {

        mongoTemplate.dropCollection(VehicleStatus.class);
        mongoTemplate.dropCollection(TrafficLightStatus.class);

        // car within 200m radius
        vehicleStatus1 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(48.185847,15.753431), System.currentTimeMillis() - 500000,
                TestUtils.createVehicleDTO("Vehicle1"), TestUtils.createGeoDTO(null,null));

        // same car within radius but older status -> should not be found
        vehicleStatus2 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(48.185897,15.753109), System.currentTimeMillis() - 1000000,
                TestUtils.createVehicleDTO("Vehicle1"), TestUtils.createGeoDTO(null,null));

        // car outside of 200m radius
        vehicleStatus3 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(48.186212,15.749150), System.currentTimeMillis() - 500000,
                TestUtils.createVehicleDTO("Vehicle2"), TestUtils.createGeoDTO(null,null));

        // car inside radius but not on road (test for circular range)
        vehicleStatus4 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(48.184996,15.754815), System.currentTimeMillis() - 500000,
                TestUtils.createVehicleDTO("Vehicle3"), TestUtils.createGeoDTO(null,null));

        // car inside of 200m radius "behind" trafficLight
        vehicleStatus5 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(48.185604,15.756103), System.currentTimeMillis() - 500000,
                TestUtils.createVehicleDTO("Vehicle4"), TestUtils.createGeoDTO(null,null));

        // car outside of 200m radius "behind" trafficLight
        vehicleStatus6 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(48.185346,15.758892), System.currentTimeMillis() - 500000,
                TestUtils.createVehicleDTO("Vehicle5"), TestUtils.createGeoDTO(null,null));

        mongoTemplate.save(modelMapper.map(vehicleStatus1, VehicleStatus.class));
        mongoTemplate.save(modelMapper.map(vehicleStatus2, VehicleStatus.class));
        mongoTemplate.save(modelMapper.map(vehicleStatus3, VehicleStatus.class));
        mongoTemplate.save(modelMapper.map(vehicleStatus4, VehicleStatus.class));
        mongoTemplate.save(modelMapper.map(vehicleStatus5, VehicleStatus.class));
        mongoTemplate.save(modelMapper.map(vehicleStatus6, VehicleStatus.class));



        trafficLight1 = TestUtils.createTrafficLight(1, TestUtils.createGeoDTO(48.185697, 15.754987), 0.2);
        trafficLight1_status1 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.RED, System.currentTimeMillis() - 10000000);
        trafficLight1_status2 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.GREEN, System.currentTimeMillis() + 10000000);
        trafficLight1_status3 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.RED, System.currentTimeMillis() + 20000000);
        trafficLight1_status4 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.GREEN, System.currentTimeMillis() + 30000000);
        trafficLight1_status5 = TestUtils.createTrafficLightStatus(trafficLight1, LightDTO.RED, System.currentTimeMillis() + 40000000);

        mongoTemplate.save(modelMapper.map(trafficLight1_status1, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight1_status2, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight1_status3, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight1_status4, TrafficLightStatus.class));
        mongoTemplate.save(modelMapper.map(trafficLight1_status5, TrafficLightStatus.class));
    }


    @Test
    public void TestTrafficLightScan (@Autowired MongoTemplate mongoTemplate) {
        ScanDTO scanDTO = this.trafficLightTrackingService.scanTrafficLight(trafficLight1);

        assertEquals(3, scanDTO.getVehicleStati().size());
    }
}
