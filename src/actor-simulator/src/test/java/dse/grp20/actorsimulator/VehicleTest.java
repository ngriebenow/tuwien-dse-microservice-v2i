package dse.grp20.actorsimulator;


import dse.grp20.actorsimulator.service.impl.VehicleSimulationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class VehicleTest {

	@Autowired
	private VehicleSimulationService vehicleSimulationService;


	@Test
	public void testRegister_NewVehicle_shouldBeSaved() throws InterruptedException {


	}


}
