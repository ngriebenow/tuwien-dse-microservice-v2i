package dse.grp20.actorsimulator;


import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.service.Vehicle1Simulator;
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
		Thread t = new Thread(() -> {
			try {
				simulator.simulate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t.start();

		while (true) {
			System.out.println(simulator.getCurrentStatus());
			Thread.sleep(1000);
		}

	}


}
