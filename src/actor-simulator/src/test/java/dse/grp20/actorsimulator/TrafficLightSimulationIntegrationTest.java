package dse.grp20.actorsimulator;


import dse.grp20.actorsimulator.service.Constants;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.actorsimulator.service.impl.TrafficLightSimulationService;
import dse.grp20.actorsimulator.service.impl.VehicleSimulationService;
import dse.grp20.common.dto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("dev")
class TrafficLightSimulationIntegrationTest {

	@Autowired
	private TrafficLightSimulationService trafficLightSimulationService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ITimeService timeService;

	@BeforeEach
	public void startSimulation() {
		trafficLightSimulationService.restartSimulation();
	}

	@AfterEach
	public void stopSimulation() {
		trafficLightSimulationService.stopSimulation();

		// discard all messages
		while (rabbitTemplate.receive("trafficlight.register") != null) {}
		while (rabbitTemplate.receive("trafficlight.update") != null) {}
		while (rabbitTemplate.receive("trafficlight.schedule") != null) {}
		while (rabbitTemplate.receive("trafficlight.control") != null) {}
	}

	@Test
	public void testRestartSimulation_shouldRegisterTrafficLight() throws InterruptedException, IOException, ClassNotFoundException {

		TrafficLightDTO trafficLightDTO = (TrafficLightDTO) rabbitTemplate.receiveAndConvert("trafficlight.register");
		assertEquals(trafficLightDTO.getId(), Constants.TRAFFICLIGHT1.getId());
		assertEquals(trafficLightDTO.getScanRadius(), Constants.TRAFFICLIGHT1.getScanRadius());
	}

	@Test
	public void testRestartSimulation_shouldScheduleTrafficLight() throws InterruptedException, IOException, ClassNotFoundException {

		List<TrafficLightStatusDTO> statusList = (List<TrafficLightStatusDTO>)
				rabbitTemplate.receiveAndConvert("trafficlight.schedule", 10000);

		assertEquals(statusList.size(),30);

		TrafficLightStatusDTO s0 = statusList.get(0);
		TrafficLightStatusDTO s1 = statusList.get(1);
		TrafficLightStatusDTO s2 = statusList.get(2);
		TrafficLightStatusDTO s3 = statusList.get(3);

		assertEquals(s1.getFrom() - s0.getFrom(), 20000);
		assertEquals(s2.getFrom() - s1.getFrom(), 20000);
		assertEquals(s3.getFrom() - s2.getFrom(), 20000);

		assertEquals(s0.getLight(), LightDTO.RED);
		assertEquals(s1.getLight(), LightDTO.GREEN);
		assertEquals(s2.getLight(), LightDTO.RED);
		assertEquals(s3.getLight(), LightDTO.GREEN);
	}

	@Test
	public void testRestartSimulation_controlTrafficLight_shouldUpdateSchedule() throws InterruptedException, IOException, ClassNotFoundException {

		Thread.sleep(5000);
		while (rabbitTemplate.receive("trafficlight.schedule") != null) {}

		TrafficLightControlDTO controlDTO = new TrafficLightControlDTO();
		controlDTO.setTrafficLightId(Constants.TRAFFICLIGHT1.getId());

		// we need green in 10 seconds
		controlDTO.setFrom(timeService.getTime() + 10000);
		controlDTO.setLightDTO(LightDTO.GREEN);

		trafficLightSimulationService.controlTrafficLight(controlDTO);


		List<TrafficLightStatusDTO> statusList = (List<TrafficLightStatusDTO>)
				rabbitTemplate.receiveAndConvert("trafficlight.schedule", 1000000);


		assertEquals(statusList.get(0).getFrom(), controlDTO.getFrom());



	}
}
