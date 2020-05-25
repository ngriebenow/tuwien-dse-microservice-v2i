package dse.grp20.actorcontrol;

import dse.grp20.actorcontrol.services.IPlanService;
import dse.grp20.actorcontrol.services.ITimeService;
import dse.grp20.common.dto.TrafficLightPlanDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("dev")
public class TrafficLightPlanTests {

    Logger logger = LoggerFactory.getLogger("TrafficLightPlanTests");

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
    public void test01_calculateGreenphaseAfterNCE_correctGreenphaseForTrafficLight3() {
        logger.info("StartTime: {}", timeService.getTime());
        timeService.setSampleIncrement(120 * 1000); // 2 minutes
        timeService.getTime(); // update time
        timeService.setSampleIncrement(43 * 1000); // vehicle already drove 43sec/800m when NCE happened
        timeService.getTime(); // update time
        timeService.setSampleIncrement(20 * 1000); // vehicle waits 20sec before continue driving
        timeService.getTime(); // update time
        timeService.setSampleIncrement(0); // stop time flow for test
        logger.info("Time when vehicle continues driving: {}", timeService.getTime());

        long newGreenphase = planService.calculateGreenphase(testData.trafficLight3PlanDTO);

        logger.info("Phase1: {}-{}", testData.TRAFFICLIGHT3_STATUS1_LIGHT, testData.startTime+testData.TRAFFICLIGHT3_STATUS1_FROM);
        logger.info("Phase2: {}-{}", testData.TRAFFICLIGHT3_STATUS2_LIGHT, testData.startTime+testData.TRAFFICLIGHT3_STATUS2_FROM);
        logger.info("Phase3: {}-{}", testData.TRAFFICLIGHT3_STATUS3_LIGHT, testData.startTime+testData.TRAFFICLIGHT3_STATUS3_FROM);
        logger.info("Phase4: {}-{}", testData.TRAFFICLIGHT3_STATUS4_LIGHT, testData.startTime+testData.TRAFFICLIGHT3_STATUS4_FROM);
        logger.info("Phase5: {}-{}", testData.TRAFFICLIGHT3_STATUS5_LIGHT, testData.startTime+testData.TRAFFICLIGHT3_STATUS5_FROM);

        logger.info("Calculated new greenphase: {}", newGreenphase);
        logger.info("Asserted startTime: {}", testData.startTime);
        logger.info("Asserted new greenphase: {}", testData.startTime+testData.TRAFFICLIGHT3_NEXT_GREENPHASE_AFTER_NCE);

        assertTrue(newGreenphase > testData.startTime+testData.TRAFFICLIGHT3_NEXT_GREENPHASE_AFTER_NCE-1000
                && newGreenphase < testData.startTime+testData.TRAFFICLIGHT3_NEXT_GREENPHASE_AFTER_NCE+1000);

    }

}
