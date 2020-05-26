package dse.grp20.statustracking;

import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.statustracking.entities.NearCrashEvent;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NearCrashEventIntegrationTest {

    private static NearCrashEventDTO nce1;
    private static NearCrashEventDTO nce2;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ITimeService timeService;

    public void clear() {
        while (this.rabbitTemplate.receive("nearcrashevent.emit") != null) {}
    }

    @BeforeAll
    public static void initForAll(@Autowired ITimeService timeService) {
        timeService.setTime(System.currentTimeMillis(), 1);
    }

    @BeforeEach
    public void init() {
        this.clear();
        this.setupAndQueueData();
    }

    private void setupAndQueueData() {

        this.mongoTemplate.dropCollection(NearCrashEvent.class);

//        nce1 = new NearCrashEventDTO(TestUtils.createGeoDTO(15.753431, 48.185847),
//                System.currentTimeMillis() - 500, "Vehicle1");
//
//        nce2 = new NearCrashEventDTO(TestUtils.createGeoDTO(15.753445, 48.185838),
//                System.currentTimeMillis() - 1000, "Vehicle2");

        nce1 = new NearCrashEventDTO(TestUtils.createGeoDTO(15.753431, 48.185847),
                this.timeService.getTime() - 500, "Vehicle1");

        nce2 = new NearCrashEventDTO(TestUtils.createGeoDTO(15.753445, 48.185838),
                this.timeService.getTime() - 1000, "Vehicle2");

        this.rabbitTemplate.convertAndSend("nearcrashevent.emit", nce1);
        this.rabbitTemplate.convertAndSend("nearcrashevent.emit", nce2);

    }

    @Test
    public void testNearCrashEvent_shouldPersist() throws InterruptedException {
        Thread.sleep(1000);

        List<NearCrashEvent> nces =  this.mongoTemplate.findAll(NearCrashEvent.class, "NearCrashEvents");

        assertNotNull(nces);
        assertEquals(2, nces.size());

        List<String> ids = nces.stream().map(item -> item.getVin()).collect(Collectors.toList());

        assertTrue(ids.containsAll(Arrays.asList("Vehicle1", "Vehicle2")));
    }
}
