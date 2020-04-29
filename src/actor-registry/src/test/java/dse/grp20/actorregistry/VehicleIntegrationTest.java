package dse.grp20.actorregistry;

import dse.grp20.actorregistry.dao.Vehicle;
import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@ActiveProfiles("dev")
class VehicleIntegrationTest {

	@Autowired
	private IVehicleRegistryService registryService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final static String QUEUE_VEHICLE_DELETE = "vehicle.delete";

	private static Vehicle VEHICLE1;

	@BeforeAll
	static void initAll(){
		VEHICLE1 = new Vehicle();
		VEHICLE1.setId(1L);
		VEHICLE1.setName("vehicle1");
	}

	@BeforeEach
	public void init()throws InvalidVehicleException {
		registryService.add(VEHICLE1);
	}

	@Test
	public void testDelete_ExistingVehicle_shouldDelete() throws InterruptedException {
		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_DELETE,VEHICLE1);

		Thread.sleep(100);

		assertThrows(NotFoundException.class,() -> registryService.find(VEHICLE1.getId()));

	}

}
