package dse.grp20.actorregistry;

import dse.grp20.actorregistry.dao.Vehicle;
import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.repository.VehicleRepository;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("dev")
class VehicleIntegrationTest {

	@Autowired
	private IVehicleRegistryService registryService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static long WAITING_TIME = 300;

	private static String QUEUE_VEHICLE_DELETE = "vehicle.delete";

	private static Vehicle VEHICLE1;
	private static Vehicle NON_EXISTING_VEHICLE;

	@BeforeAll
	static void initAll(){
		VEHICLE1 = new Vehicle();
		VEHICLE1.setId(1L);
		VEHICLE1.setName("vehicle1");

		NON_EXISTING_VEHICLE = new Vehicle();
		NON_EXISTING_VEHICLE.setId(2L);
		NON_EXISTING_VEHICLE.setName("non_existing");

	}

	@BeforeEach
	public void init() throws InvalidVehicleException {
		registryService.add(VEHICLE1);
	}

	@Test
	public void testDelete_ExistingVehicle_shouldDelete() throws InterruptedException, NotFoundException {
		assertNotNull(registryService.find(VEHICLE1.getId()));

		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_DELETE,VEHICLE1);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(VEHICLE1.getId()));

	}

	@Test
	public void testDelete_NonExistingVehicle_shouldIgnore() throws InterruptedException, NotFoundException {
		assertNotNull(registryService.find(VEHICLE1.getId()));

		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_DELETE,NON_EXISTING_VEHICLE);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(789L));

	}

}
