package dse.grp20.actorregistry;

import dse.grp20.actorregistry.exception.InvalidVehicleException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import dse.grp20.common.dto.VehicleDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
class VehicleIntegrationTest {

	@Autowired
	private IVehicleRegistryService registryService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static long WAITING_TIME = 300;

	private static String QUEUE_VEHICLE_DELETE = "vehicle.delete";
	private static String QUEUE_VEHICLE_REGISTER = "vehicle.register";

	private static VehicleDTO VEHICLE1;
	private static VehicleDTO NON_EXISTING_VEHICLE;

	@BeforeAll
	static void initAll(){
		VEHICLE1 = new VehicleDTO();
		VEHICLE1.setVin("v1");
		VEHICLE1.setModelType("vehicle1");

		NON_EXISTING_VEHICLE = new VehicleDTO();
		NON_EXISTING_VEHICLE.setVin("v2");
		NON_EXISTING_VEHICLE.setModelType("non_existing");

	}

	@Test
	public void testRegister_NewVehicle_shouldBeSaved() throws InterruptedException, NotFoundException {
		assertThrows(NotFoundException.class, () -> registryService.find(VEHICLE1.getVin()));
		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_REGISTER,VEHICLE1);
		Thread.sleep(WAITING_TIME);
		VehicleDTO vehicleDTO = registryService.find(VEHICLE1.getVin());
		assertNotNull(vehicleDTO);
	}

	@Test
	public void testDelete_ExistingVehicle_shouldDelete() throws InterruptedException, NotFoundException, InvalidVehicleException {
		registryService.register(VEHICLE1);
		assertNotNull(registryService.find(VEHICLE1.getVin()));

		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_DELETE,VEHICLE1);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(VEHICLE1.getVin()));

	}

	@Test
	public void testDelete_NonExistingVehicle_shouldIgnore() throws InterruptedException, NotFoundException, InvalidVehicleException {
		registryService.register(VEHICLE1);
		assertNotNull(registryService.find(VEHICLE1.getVin()));

		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_DELETE,NON_EXISTING_VEHICLE);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find("32452353245"));

	}

}
