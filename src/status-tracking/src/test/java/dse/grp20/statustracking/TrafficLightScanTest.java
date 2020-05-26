package dse.grp20.statustracking;


import dse.grp20.common.dto.*;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.service.ITimeService;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
public class TrafficLightScanTest {

    @Autowired
    private ITrafficLightTrackingService trafficLightTrackingService;

    private static VehicleStatusDTO vehicleStatus1;
    private static VehicleStatusDTO vehicleStatus2;
    private static VehicleStatusDTO vehicleStatus3;
    private static VehicleStatusDTO vehicleStatus4;
    private static VehicleStatusDTO vehicleStatus5;
    private static VehicleStatusDTO vehicleStatus6;
    private static VehicleStatusDTO vehicleStatus7;


    private static TrafficLightDTO trafficLight1;
    private static TrafficLightStatusDTO trafficLight1_status1;
    private static TrafficLightStatusDTO trafficLight1_status2;
    private static TrafficLightStatusDTO trafficLight1_status3;
    private static TrafficLightStatusDTO trafficLight1_status4;
    private static TrafficLightStatusDTO trafficLight1_status5;

    private static TrafficLightDTO trafficLight2;
    private static TrafficLightStatusDTO trafficLight2_status1;
    private static TrafficLightStatusDTO trafficLight2_status2;
    private static TrafficLightStatusDTO trafficLight2_status3;




    @BeforeAll
    public static void init (@Autowired MongoTemplate mongoTemplate, @Autowired ITimeService timeService) {

        mongoTemplate.dropCollection(VehicleStatus.class);
        mongoTemplate.dropCollection(TrafficLightStatus.class);

        timeService.setTime(System.currentTimeMillis(), 1);

        // car within 200m radius
        vehicleStatus1 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.753431, 48.185847),
                timeService.getTime() - 500, "Vehicle1",
                TestUtils.createGeoDTO(15.753458,48.185838));

        // own test for this behaviour -> later
        // same car within radius but older status -> should not be found
        vehicleStatus2 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.753109, 48.185897),
                timeService.getTime() - 1000, "Vehicle1",
                TestUtils.createGeoDTO(15.75431,48.185847));

        // car outside of 200m radius
        vehicleStatus3 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.749150, 48.186212),
                timeService.getTime() - 500, "Vehicle2",
                TestUtils.createGeoDTO(15.749201,48.186200));

        // car inside radius but not on road (test for circular range)
        vehicleStatus4 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.754815, 48.184996),
                timeService.getTime() - 500, "Vehicle3",
                TestUtils.createGeoDTO(15.754823,48.185002));

        // car inside of 200m radius "behind" trafficLight                              15.754987, 48.185697
        vehicleStatus5 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.756103, 48.185604),
                timeService.getTime() - 500, "Vehicle4",
                TestUtils.createGeoDTO(15.756110,48.185598));

        // car outside of 200m radius "behind" trafficLight                             15.754987, 48.185697
        vehicleStatus6 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.758892, 48.185346),
                timeService.getTime() - 500, "Vehicle5",
                TestUtils.createGeoDTO(null,null));

        // own test for this behaviour -> later
        // older than 10s should not be found                                               15.754987, 48.185697
        vehicleStatus7 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.753458,48.185838),
                timeService.getTime() - 13000, "Vehicle6",
                TestUtils.createGeoDTO(15.758892, 48.185346));

        mongoTemplate.save(convertDTOtoEntity(vehicleStatus1));
        mongoTemplate.save(convertDTOtoEntity(vehicleStatus2));
        mongoTemplate.save(convertDTOtoEntity(vehicleStatus3));
        mongoTemplate.save(convertDTOtoEntity(vehicleStatus4));
        mongoTemplate.save(convertDTOtoEntity(vehicleStatus5));
        mongoTemplate.save(convertDTOtoEntity(vehicleStatus6));
        mongoTemplate.save(convertDTOtoEntity(vehicleStatus7));


        trafficLight1 = TestUtils.createTrafficLight(1, TestUtils.createGeoDTO(15.754987, 48.185697), 0.2);
        trafficLight1_status1 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, timeService.getTime() - 10000000);
        trafficLight1_status2 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.GREEN, timeService.getTime() + 10000000);
        trafficLight1_status3 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, timeService.getTime() + 20000000);
        trafficLight1_status4 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.GREEN, timeService.getTime() + 30000000);
        trafficLight1_status5 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, timeService.getTime() + 40000000);

        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status1));
        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status2));
        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status3));
        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status4));
        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status5));

        trafficLight2 = TestUtils.createTrafficLight(2, TestUtils.createGeoDTO(48.185697, 15.754987), 2.0);
        trafficLight2_status1 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, timeService.getTime() - 10000000);
        trafficLight2_status2 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, timeService.getTime() + 10000000);
        trafficLight2_status3 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, timeService.getTime() + 20000000);

        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status1));
        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status2));
        mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status3));
    }


    @Test
    public void TestTrafficLightScan () {

        ScanDTO scanDTO = this.trafficLightTrackingService.scanTrafficLight(trafficLight1);
        List<VehicleStatusDTO> vehicleStati = scanDTO.getVehicleStati();

        assertEquals(2, vehicleStati.size());

        List<String> vehicleIds = vehicleStati.stream()
                .map(item ->  item.getVin()).collect(Collectors.toList());

        List<String> expectedIds = Arrays.asList(new String[]{"Vehicle1","Vehicle3"});

        Collections.sort(expectedIds);
        Collections.sort(vehicleIds);

        assertEquals(expectedIds,vehicleIds);


        List<TrafficLightStatusDTO> trafficLightStati = scanDTO.getTrafficLightStati();
        assertEquals(4, trafficLightStati.size());

        assertTrue(trafficLightStati.stream().allMatch(item -> item.getTrafficLightId() == 1));
    }

    private static VehicleStatus convertDTOtoEntity (VehicleStatusDTO dto) {
        return new VehicleStatus(dto);
    }

}
