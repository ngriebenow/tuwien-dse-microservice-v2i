package dse.grp20.statustracking;

import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.entities.VehicleStatus;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class VehicleTrackingIntegrationService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    private static VehicleStatusDTO vehicleStatus1;
    private static VehicleStatusDTO vehicleStatus2;
    private static VehicleStatusDTO vehicleStatus3;

    public void clear() {
        while (this.rabbitTemplate.receive("vehicle.update") != null) {}
    }

    @BeforeEach
    public void init () {

        this.clear();
        this.setupAndQueueData();
    }

    @AfterEach
    public void reset () {
        this.clear();
    }

    @Test
    public void testVehicleUpdate_shouldTrigger () throws InterruptedException {
        Thread.sleep(1000);

        List<VehicleStatus> vehicles = this.mongoTemplate.findAll(VehicleStatus.class, "VehicleStatus");

        assertNotNull(vehicles);
        assertEquals(3, vehicles.size());

        List<String> vehicleIds = vehicles.stream()
                .map(item ->  item.getVin()).collect(Collectors.toList());

        List<String> expectedIds = Arrays.asList(new String[]{"Vehicle1","Vehicle2", "Vehicle3"});

        Collections.sort(expectedIds);
        Collections.sort(vehicleIds);

        assertEquals(expectedIds,vehicleIds);
    }

    private void setupAndQueueData() {
        this.mongoTemplate.dropCollection(VehicleStatus.class);

        vehicleStatus1 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(12.12,12.12), System.currentTimeMillis(),
                "Vehicle1", TestUtils.createGeoDTO(12.13,12.14));

        vehicleStatus2 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(12.13,12.14), System.currentTimeMillis(),
                "Vehicle2", TestUtils.createGeoDTO(12.15,12.16));

        vehicleStatus3 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(12.12,12.12), System.currentTimeMillis(),
                "Vehicle3", TestUtils.createGeoDTO(12.13,12.14));

        this.rabbitTemplate.convertAndSend("vehicle.update", vehicleStatus1);
        this.rabbitTemplate.convertAndSend("vehicle.update", vehicleStatus2);
        this.rabbitTemplate.convertAndSend("vehicle.update", vehicleStatus3);

    }
}
