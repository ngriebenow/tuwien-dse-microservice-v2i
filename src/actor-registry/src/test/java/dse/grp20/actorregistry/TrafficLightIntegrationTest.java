package dse.grp20.actorregistry;

import dse.grp20.actorregistry.entity.Geo;
import dse.grp20.actorregistry.entity.TrafficLight;
import dse.grp20.actorregistry.exception.InvalidTrafficLightException;
import dse.grp20.actorregistry.exception.NotFoundException;
import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import dse.grp20.common.dto.GeoDTO;
import dse.grp20.common.dto.TrafficLightDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("dev")
class TrafficLightIntegrationTest {

	@Autowired
	private ITrafficLightRegistryService registryService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private static long WAITING_TIME = 300;

	private static String QUEUE_TRAFFICLIGHT_DELETE = "trafficlight.delete";

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

	@BeforeEach
	public void init() throws InvalidTrafficLightException {
		registryService.add(TRAFFICLIGHT1);
	}

	@Test
	public void testDelete_ExistingVehicle_shouldDelete() throws InterruptedException, NotFoundException {
		assertNotNull(registryService.find(TRAFFICLIGHT1.getId()));

		rabbitTemplate.convertAndSend(QUEUE_TRAFFICLIGHT_DELETE,TRAFFICLIGHT1);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(TRAFFICLIGHT1.getId()));

	}

	@Test
	public void testDelete_NonExistingVehicle_shouldIgnore() throws InterruptedException, NotFoundException {
		assertNotNull(registryService.find(TRAFFICLIGHT1.getId()));

		rabbitTemplate.convertAndSend(QUEUE_TRAFFICLIGHT_DELETE,NON_EXISTING_TRAFFICLIGHT);

		Thread.sleep(WAITING_TIME);

		assertThrows(NotFoundException.class,() -> registryService.find(789L));

	}

}
