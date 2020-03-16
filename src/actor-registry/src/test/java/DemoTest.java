import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DemoTest {

    @Test
    public void whenAdding_ReturnSum() {
        assertThat(Calculation.add(3,4), is(7.0));
    }

}
