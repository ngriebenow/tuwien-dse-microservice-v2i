package dse.grp20.actorsimulator;


import dse.grp20.actorsimulator.entity.VehicleStatus;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.service.Vehicle1Simulator;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;




@SpringBootTest
class VehicleTest {

	@Autowired
	private Vehicle1Simulator simulator;


	@Test
	public void testRegister_NewVehicle_shouldBeSaved() throws InterruptedException, NotFoundException {


	}


}
