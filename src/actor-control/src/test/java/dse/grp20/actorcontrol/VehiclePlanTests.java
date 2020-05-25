package dse.grp20.actorcontrol;

import dse.grp20.actorcontrol.services.IPlanService;
import dse.grp20.actorcontrol.services.ITimeService;
import dse.grp20.actorcontrol.services.impl.TimeService;
import dse.grp20.common.dto.TrafficLightStatusDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
public class VehiclePlanTests {

    @Autowired
    private IPlanService planService;

    @Autowired
    @Qualifier("fluxkompensator")
    private ITimeService timeService;
    private TestData testData;

    @BeforeEach
    public void setup() {
        timeService.reset();
        testData = new TestData(timeService.getTime());
    }

    @Test
    public void test01_calculateSpeed_correctSpeedAfterTrafficLight1() {
        List<TrafficLightStatusDTO> trafficLightStatusDTOList = new ArrayList<>();
        trafficLightStatusDTOList.add(testData.trafficLight1Status1);
        trafficLightStatusDTOList.add(testData.trafficLight1Status2);
        trafficLightStatusDTOList.add(testData.trafficLight1Status3);
        trafficLightStatusDTOList.add(testData.trafficLight1Status4);
        trafficLightStatusDTOList.add(testData.trafficLight1Status5);

        double newSpeed = planService.calculateSpeed(testData.vehicle1Status, trafficLightStatusDTOList);

        assertTrue(testData.vehicleSpeedAfterTrafficLight1-1 < newSpeed && newSpeed < testData.vehicleSpeedAfterTrafficLight1+1);
    }

    @Test
    public void test02_calculateSpeed_correctSpeedAfterTrafficLight2() {
        timeService.setSampleIncrement(60000);

        List<TrafficLightStatusDTO> trafficLightStatusDTOList = new ArrayList<>();
        trafficLightStatusDTOList.add(testData.trafficLight2Status1);
        trafficLightStatusDTOList.add(testData.trafficLight2Status2);
        trafficLightStatusDTOList.add(testData.trafficLight2Status3);
        trafficLightStatusDTOList.add(testData.trafficLight2Status4);
        trafficLightStatusDTOList.add(testData.trafficLight2Status5);

        double newSpeed = planService.calculateSpeed(testData.vehicle2Status, trafficLightStatusDTOList);

        assertTrue(testData.vehicleSpeedAfterTrafficLight2-1 < newSpeed && newSpeed < testData.vehicleSpeedAfterTrafficLight2+1);
    }

    @Test
    public void test03_calculateSpeed_correctSpeedForTrafficLight1And2() {
        List<TrafficLightStatusDTO> trafficLightStatusDTOList1 = new ArrayList<>();
        trafficLightStatusDTOList1.add(testData.trafficLight1Status1);
        trafficLightStatusDTOList1.add(testData.trafficLight1Status2);
        trafficLightStatusDTOList1.add(testData.trafficLight1Status3);
        trafficLightStatusDTOList1.add(testData.trafficLight1Status4);
        trafficLightStatusDTOList1.add(testData.trafficLight1Status5);

        double newSpeed1 = planService.calculateSpeed(testData.vehicle1Status, trafficLightStatusDTOList1);

        assertTrue(testData.vehicleSpeedAfterTrafficLight1-1 < newSpeed1 && newSpeed1 < testData.vehicleSpeedAfterTrafficLight1+1);

        timeService.setSampleIncrement(60000);

        List<TrafficLightStatusDTO> trafficLightStatusDTOList2 = new ArrayList<>();
        trafficLightStatusDTOList2.add(testData.trafficLight2Status1);
        trafficLightStatusDTOList2.add(testData.trafficLight2Status2);
        trafficLightStatusDTOList2.add(testData.trafficLight2Status3);
        trafficLightStatusDTOList2.add(testData.trafficLight2Status4);
        trafficLightStatusDTOList2.add(testData.trafficLight2Status5);

        double newSpeed2 = planService.calculateSpeed(testData.vehicle2Status, trafficLightStatusDTOList2);

        assertTrue(testData.vehicleSpeedAfterTrafficLight2-1 < newSpeed2 && newSpeed2 < testData.vehicleSpeedAfterTrafficLight2+1);
    }
}
