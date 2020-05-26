package dse.grp20.statustracking;

import dse.grp20.common.dto.NearCrashEventDTO;
import dse.grp20.statustracking.entities.NearCrashEvent;
import dse.grp20.statustracking.service.INearCrashEventService;
import dse.grp20.statustracking.service.ITimeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class NearCrashEventTest {

    @Autowired
    INearCrashEventService nearCrashEventService;

    @Autowired
    MongoTemplate mongoTemplate;

    private static NearCrashEventDTO nce1;
    private static NearCrashEventDTO nce2;


    @BeforeAll
    public static void init(@Autowired MongoTemplate mongoTemplate, @Autowired ITimeService timeService) {
        mongoTemplate.dropCollection(NearCrashEvent.class);

        timeService.setTime(System.currentTimeMillis(), 1);

        nce1 = new NearCrashEventDTO(TestUtils.createGeoDTO(15.753431, 48.185847),
                timeService.getTime() - 500, "Vehicle1");

        nce2 = new NearCrashEventDTO(TestUtils.createGeoDTO(15.753445, 48.185838),
                timeService.getTime() - 1000, "Vehicle2");

    }

    @Test
    public void testNearCrashEvent_shouldPersist() {

        this.nearCrashEventService.registerNearCrashEvent(nce1);
        this.nearCrashEventService.registerNearCrashEvent(nce2);

        List<NearCrashEvent> nces =  this.mongoTemplate.findAll(NearCrashEvent.class, "NearCrashEvents");

        assertNotNull(nces);
        assertEquals(2, nces.size());

        List<String> ids = nces.stream().map(item -> item.getVin()).collect(Collectors.toList());

        assertTrue(ids.containsAll(Arrays.asList("Vehicle1", "Vehicle2")));
    }
}
