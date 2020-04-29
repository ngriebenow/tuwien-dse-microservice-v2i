package dse.grp20.actorregistry;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dse.grp20.actorregistry.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@ActiveProfiles("dev")
class ActorRegistryApplicationTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final static String QUEUE_VEHICLE_DELETE = "vehicle.delete";


	@Test
	void contextLoads() throws IOException, TimeoutException {

		Vehicle vehicle = new Vehicle();
		vehicle.setName("v1");
		vehicle.setId(1L);
		rabbitTemplate.convertAndSend(QUEUE_VEHICLE_DELETE,vehicle);

	}

}
