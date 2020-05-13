package dse.grp20.statustracking;

import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class TrafficLightTrackingTest {

    @Autowired
    private ITrafficLightTrackingService trackingService;

    @Autowired
    private RabbitTemplate template;


    @BeforeAll
    static void initAll(){
    }

    @BeforeEach
    public void init() {
    }

    @Test
    public void Test() {
        System.out.println("Test");
    }
}
