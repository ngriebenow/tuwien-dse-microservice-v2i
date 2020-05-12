package dse.grp20.actorsimulator;

import dse.grp20.actorsimulator.exception.InvalidTrafficLightException;
import dse.grp20.actorsimulator.exception.NotFoundException;
import dse.grp20.actorsimulator.service.ITrafficLightRegistryService;
import dse.grp20.common.dto.GeoDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
class TrafficLightIntegrationTest {

	@Autowired
	private ITrafficLightRegistryService registryService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static long WAITING_TIME = 300;

	private static String QUEUE_TRAFFICLIGHT_DELETE = "trafficlight.delete";
	private static String QUEUE_TRAFFICLIGHT_REGISTER = "trafficlight.register";

	private static TrafficLightDTO TRAFFICLIGHT1;
	private static TrafficLightDTO NON_EXISTING_TRAFFICLIGHT;

	@BeforeAll
	static void initAll(){
		TRAFFICLIGHT1 = new TrafficLightDTO();
		TRAFFICLIGHT1.setId(1L);
		TRAFFICLIGHT1.setLocation(new GeoDTO(3.,4.));
		TRAFFICLIGHT1.setScanRadius(30.);

		NON_EXISTING_TRAFFICLIGHT = new TrafficLightDTO();
		NON_EXISTING_TRAFFICLIGHT.setId(2L);
		NON_EXISTING_TRAFFICLIGHT.setLocation(new GeoDTO(3.,4.));
		NON_EXISTING_TRAFFICLIGHT.setScanRadius(30.);

	}

	@Test
	public void testRegister_NewTrafficlight_shouldBeSaved() throws NotFoundException, InterruptedException {
		assertThrows(NotFoundException.class, () -> registryService.find(TRAFFICLIGHT1.getId()));
		rabbitTemplate.convertAndSend(QUEUE_TRAFFICLIGHT_REGISTER,TRAFFICLIGHT1);
		Thread.sleep(WAITING_TIME);
		TrafficLightDTO trafficLightDTO = registryService.find(TRAFFICLIGHT1.getId());
		assertNotNull(trafficLightDTO);
	}

	@Test
	public void testDelete_ExistingTrafficlight_shouldDelete() throws InterruptedException, NotFoundException, InvalidTrafficLightException {
		registryService.register(TRAFFICLIGHT1);
		assertNotNull(registryService.find(TRAFFICLIGHT1.getId()));

		rabbitTemplate.convertAndSend(QUEUE_TRAFFICLIGHT_DELETE,TRAFFICLIGHT1);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(TRAFFICLIGHT1.getId()));

	}

	@Test
	public void testDelete_NonExistingTrafficlight_shouldIgnore() throws InterruptedException, NotFoundException, InvalidTrafficLightException {
		registryService.register(TRAFFICLIGHT1);
		assertNotNull(registryService.find(TRAFFICLIGHT1.getId()));

		rabbitTemplate.convertAndSend(QUEUE_TRAFFICLIGHT_DELETE,NON_EXISTING_TRAFFICLIGHT);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(789L));

	}

}
