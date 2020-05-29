package dse.grp20.statustracking;

import dse.grp20.common.dto.*;
import dse.grp20.statustracking.entities.NearCrashEvent;
import dse.grp20.statustracking.entities.TrafficLightStatus;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.service.ITimeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrafficLightScanIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ITimeService timeService;

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

    public void clear() {
        while (this.rabbitTemplate.receive("trafficlight.scan") != null) {}
        while (this.rabbitTemplate.receive("trafficlight.plan") != null) {}
        while (this.rabbitTemplate.receive("vehicle.plan") != null) {}

    }

    @BeforeAll
    public static void initAll(@Autowired ITimeService timeService) {
        timeService.setTime(System.currentTimeMillis(), 1);
    }

    @BeforeEach
    public void init() {
        this.clear();
        this.setupAndQueueData();
    }

    private void setupAndQueueData() {
        this.mongoTemplate.dropCollection(VehicleStatus.class);
        this.mongoTemplate.dropCollection(TrafficLightStatus.class);

        // car within 200m radius
        vehicleStatus1 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.753431, 48.185847),
                this.timeService.getTime() - 500, "Vehicle1",
                TestUtils.createGeoDTO(15.753458,48.185838));

        // own test for this behaviour -> later
        // same car within radius but older status -> should not be found
        vehicleStatus2 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.753109, 48.185897),
                this.timeService.getTime() - 1000, "Vehicle1",
                TestUtils.createGeoDTO(15.75431,48.185847));

        // car outside of 200m radius
        vehicleStatus3 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.749150, 48.186212),
                this.timeService.getTime() - 500, "Vehicle2",
                TestUtils.createGeoDTO(15.749201,48.186200));

        // car inside radius but not on road (test for circular range)
        vehicleStatus4 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.754815, 48.184996),
                this.timeService.getTime() - 500, "Vehicle3",
                TestUtils.createGeoDTO(15.754823,48.185002));

        // car inside of 200m radius "behind" trafficLight                              15.754987, 48.185697
        vehicleStatus5 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.756103, 48.185604),
                this.timeService.getTime() - 500, "Vehicle4",
                TestUtils.createGeoDTO(15.756110,48.185598));

        // car outside of 200m radius "behind" trafficLight                             15.754987, 48.185697
        vehicleStatus6 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.758892, 48.185346),
                this.timeService.getTime() - 500, "Vehicle5",
                TestUtils.createGeoDTO(null,null));

        // own test for this behaviour -> later
        // older than 10s should not be found                                               15.754987, 48.185697
        vehicleStatus7 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(15.753458,48.185838),
                this.timeService.getTime() - 13000, "Vehicle6",
                TestUtils.createGeoDTO(15.758892, 48.185346));

        this.mongoTemplate.save(new VehicleStatus(vehicleStatus1));
        this.mongoTemplate.save(new VehicleStatus(vehicleStatus2));
        this.mongoTemplate.save(new VehicleStatus(vehicleStatus3));
        this.mongoTemplate.save(new VehicleStatus(vehicleStatus4));
        this.mongoTemplate.save(new VehicleStatus(vehicleStatus5));
        this.mongoTemplate.save(new VehicleStatus(vehicleStatus6));
        this.mongoTemplate.save(new VehicleStatus(vehicleStatus7));


        trafficLight1 = TestUtils.createTrafficLight(1, TestUtils.createGeoDTO(15.754987, 48.185697), 0.2);
        trafficLight1_status1 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, this.timeService.getTime() - 10000000);
        trafficLight1_status2 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.GREEN, this.timeService.getTime() + 10000000);
        trafficLight1_status3 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, this.timeService.getTime() + 20000000);
        trafficLight1_status4 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.GREEN, this.timeService.getTime() + 30000000);
        trafficLight1_status5 = TestUtils.createTrafficLightStatus(trafficLight1.getId(), LightDTO.RED, this.timeService.getTime() + 40000000);

        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status1));
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status2));
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status3));
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status4));
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight1_status5));

        trafficLight2 = TestUtils.createTrafficLight(2, TestUtils.createGeoDTO(48.185697, 15.754987), 2.0);
        trafficLight2_status1 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime() - 10000000);
        trafficLight2_status2 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.GREEN, this.timeService.getTime() + 10000000);
        trafficLight2_status3 = TestUtils.createTrafficLightStatus(trafficLight2.getId(), LightDTO.RED, this.timeService.getTime() + 20000000);

        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status1));
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status2));
        this.mongoTemplate.save(TestUtils.convertDTOtoEntity(trafficLight2_status3));
    }


    @Test
    public void testScanTrafficLight_shouldFindVehicle () throws InterruptedException {
        this.rabbitTemplate.convertAndSend("trafficlight.scan", trafficLight1);

        Thread.sleep(1000);

        ScanDTO scanResult = (ScanDTO) this.rabbitTemplate.receiveAndConvert("vehicle.plan", 1000);

        List<VehicleStatusDTO> vehicleStati = scanResult.getVehicleStati();

        assertEquals(2, vehicleStati.size());

        List<String> vehicleIds = vehicleStati.stream()
                .map(item ->  item.getVin()).collect(Collectors.toList());

        List<String> expectedIds = Arrays.asList(new String[]{"Vehicle1","Vehicle3"});

        Collections.sort(expectedIds);
        Collections.sort(vehicleIds);

        assertEquals(expectedIds,vehicleIds);


        List<TrafficLightStatusDTO> trafficLightStati = scanResult.getTrafficLightStati();
        assertEquals(4, trafficLightStati.size());

        assertTrue(trafficLightStati.stream().allMatch(item -> item.getTrafficLightId() == 1));

        List<TrafficLightPlanDTO> trafficlightPlans = (List<TrafficLightPlanDTO>) this.rabbitTemplate
                .receiveAndConvert("trafficlight.plan", 1000);

        assertTrue(trafficlightPlans.size() == 0);

    }


    @Test
    public void testScanTrafficLight_shouldFindVehicle_andNCE () throws InterruptedException {

        NearCrashEvent nce1 = new NearCrashEvent(TestUtils.createGeoDTO(15.753431, 48.185847),
                this.timeService.getTime() - 500, "Vehicle1");

        //should not be found because it is too old
        NearCrashEvent nce2 = new NearCrashEvent(TestUtils.createGeoDTO(15.753431, 48.185847),
                this.timeService.getTime() - 50000, "Vehicle3");

        this.mongoTemplate.save(nce1, "NearCrashEvents");
        this.mongoTemplate.save(nce2, "NearCrashEvents");



        this.rabbitTemplate.convertAndSend("trafficlight.scan", trafficLight1);

        Thread.sleep(1000);

        ScanDTO scanResult = (ScanDTO) this.rabbitTemplate.receiveAndConvert("vehicle.plan", 1000);

        List<VehicleStatusDTO> vehicleStati = scanResult.getVehicleStati();

        assertEquals(2, vehicleStati.size());

        List<String> vehicleIds = vehicleStati.stream()
                .map(item ->  item.getVin()).collect(Collectors.toList());

        List<String> expectedIds = Arrays.asList(new String[]{"Vehicle1","Vehicle3"});

        Collections.sort(expectedIds);
        Collections.sort(vehicleIds);

        assertEquals(expectedIds,vehicleIds);


        List<TrafficLightStatusDTO> trafficLightStati = scanResult.getTrafficLightStati();
        assertEquals(4, trafficLightStati.size());

        assertTrue(trafficLightStati.stream().allMatch(item -> item.getTrafficLightId() == 1));

        // "only one NCE should be found"
        List<TrafficLightPlanDTO> trafficlightPlans = (List<TrafficLightPlanDTO>) this.rabbitTemplate
                .receiveAndConvert("trafficlight.plan", 1000);

        assertNotNull(trafficlightPlans);

        assertEquals(1, trafficlightPlans.size());

        assertEquals("Vehicle1", trafficlightPlans.get(0).getVin());
        assertEquals(1, trafficlightPlans.get(0).getTrafficLightId());
    }

    @Test
    public void testScanTrafficLight_shouldNotFindVehicle () throws InterruptedException {
        TrafficLightDTO trafficLight2;
        trafficLight2 = TestUtils.createTrafficLight(1, TestUtils.createGeoDTO( 48.185697, 15.754987), 0.2);

        this.rabbitTemplate.convertAndSend("trafficlight.scan", trafficLight2);

        Thread.sleep(1000);

        ScanDTO scan = (ScanDTO) this.rabbitTemplate.receiveAndConvert("vehicle.plan", 1000);

        assertNotNull(scan);
        assertTrue(scan.getTrafficLightStati().size() == 4);
        assertTrue(scan.getVehicleStati().size() == 0);


        List<TrafficLightPlanDTO> trafficlightPlans = (List<TrafficLightPlanDTO>) this.rabbitTemplate
                .receiveAndConvert("trafficlight.plan", 1000);

        assertNotNull(trafficlightPlans);
        assertTrue(trafficlightPlans.size() == 0);    }

}
