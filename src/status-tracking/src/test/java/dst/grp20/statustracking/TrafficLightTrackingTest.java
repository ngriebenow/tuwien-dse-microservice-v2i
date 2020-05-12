package dst.grp20.statustracking;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class TrafficLightTrackingTest {


    @BeforeAll
    static void initAll(){
    }

    @BeforeEach
    public void init() {
    }
}
