package dse.grp20.actorsimulator;


import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.service.Simulator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class VehicleTest {

	@Autowired
	private Simulator simulator;


	@Test
	public void testRegister_NewVehicle_shouldBeSaved() throws InterruptedException, NotFoundException {


	}


}
