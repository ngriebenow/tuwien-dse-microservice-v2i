package dse.grp20.statustracking;

import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.entities.VehicleStatus;
import dse.grp20.statustracking.service.IVehicleTrackingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
public class VehicleTrackingServiceTest {

    @Autowired
    private IVehicleTrackingService vehicleTrackingService;

    private static VehicleStatusDTO vehicleStatus1;
    private static VehicleStatusDTO vehicleStatus2;
    private static VehicleStatusDTO vehicleStatus3;
    private static VehicleStatusDTO vehicleStatus4;
    private static VehicleStatusDTO vehicleStatus5;
    private static VehicleStatusDTO vehicleStatus6;

    @BeforeAll
    public static void init(@Autowired MongoTemplate mongoTemplate) {

        mongoTemplate.dropCollection(VehicleStatus.class);

        vehicleStatus1 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(12.12,12.12), System.currentTimeMillis(),
                "Vehicle1", TestUtils.createGeoDTO(12.13,12.14));

        vehicleStatus2 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(12.12,12.12), System.currentTimeMillis(),
                "Vehicle1", TestUtils.createGeoDTO(12.13,12.14));

        vehicleStatus3 = TestUtils.createVehicleStatus(TestUtils.createGeoDTO(12.12,12.12), System.currentTimeMillis(),
                "Vehicle1", TestUtils.createGeoDTO(12.13,12.14));

    }

    @Test
    public void testUpdateVehicleStatus(@Autowired MongoTemplate mongoTemplate) {

        this.vehicleTrackingService.updateVehicle(vehicleStatus1);
        this.vehicleTrackingService.updateVehicle(vehicleStatus2);
        this.vehicleTrackingService.updateVehicle(vehicleStatus2);

        List<VehicleStatus> vehicleStatusList = mongoTemplate.findAll(VehicleStatus.class);
        assertEquals(3, vehicleStatusList.size());

    }


}
