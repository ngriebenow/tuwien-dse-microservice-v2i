package dse.grp20.actorsimulator;


import dse.grp20.actorsimulator.service.Constants;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.actorsimulator.service.impl.TrafficLightSimulationService;
import dse.grp20.actorsimulator.service.impl.VehicleSimulationService;
import dse.grp20.common.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@ActiveProfiles("dev-with-rabbitmq")
class TrafficLightSimulationIntegrationTest {

	@Autowired
	private TrafficLightSimulationService trafficLightSimulationService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ITimeService timeService;

	public void clear() {
		while (rabbitTemplate.receive("trafficlight.register") != null) {}
		while (rabbitTemplate.receive("trafficlight.update") != null) {}
		while (rabbitTemplate.receive("trafficlight.schedule") != null) {}
		while (rabbitTemplate.receive("trafficlight.control") != null) {}
		while (rabbitTemplate.receive("trafficlight.scan") != null) {}
	}

	@BeforeEach
	public void startSimulation() {
		clear();
		timeService.setSimulationSpeed(100.);
		timeService.restartSimulation();
		trafficLightSimulationService.restartSimulation();
	}

	@AfterEach
	public void stopSimulation() {
		trafficLightSimulationService.stopSimulation();
		clear();
	}

	@Test
	public void testRestartSimulation_shouldRegisterTrafficLight() throws InterruptedException, IOException, ClassNotFoundException {

		TrafficLightDTO trafficLightDTO = (TrafficLightDTO) rabbitTemplate.receiveAndConvert("trafficlight.register",10000);
		assertEquals(trafficLightDTO.getId(), Constants.TRAFFICLIGHT1.getId());
		assertEquals(trafficLightDTO.getScanRadius(), Constants.TRAFFICLIGHT1.getScanRadius());
	}

	@Test
	public void testRestartSimulation_shouldScheduleTrafficLight() throws InterruptedException, IOException, ClassNotFoundException {

		List<TrafficLightStatusDTO> statusList;
		while (true) {
			statusList = (List<TrafficLightStatusDTO>)
					rabbitTemplate.receiveAndConvert("trafficlight.schedule",10000);
			if (statusList == null) fail();
			else if (statusList.get(0).getTrafficLightId() == Constants.TRAFFICLIGHT1.getId()) {
				break;
			}
		}



		assertEquals(30, statusList.size());

		TrafficLightStatusDTO s0 = statusList.get(0);
		TrafficLightStatusDTO s1 = statusList.get(1);
		TrafficLightStatusDTO s2 = statusList.get(2);
		TrafficLightStatusDTO s3 = statusList.get(3);

		assertEquals(20000,s1.getFrom() - s0.getFrom());
		assertEquals(20000,s2.getFrom() - s1.getFrom());
		assertEquals(20000,s3.getFrom() - s2.getFrom());

		assertEquals(LightDTO.GREEN, s0.getLight());
		assertEquals(LightDTO.RED, s1.getLight());
		assertEquals(LightDTO.GREEN, s2.getLight());
		assertEquals(LightDTO.RED, s3.getLight());
	}

	@Test
	public void testRestartSimulation_controlTrafficLight_shouldControl() throws InterruptedException, IOException, ClassNotFoundException {

		// empty schedule queue
		while (rabbitTemplate.receive("trafficlight.update") != null) {}

		// send control to ask for green
		TrafficLightControlDTO controlDTO = new TrafficLightControlDTO();
		controlDTO.setTrafficLightId(Constants.TRAFFICLIGHT1.getId());
		// we need green in 5 seconds
		controlDTO.setFrom(timeService.getTime()+5000);
		controlDTO.setLightDTO(LightDTO.GREEN);
		trafficLightSimulationService.controlTrafficLight(controlDTO);


		// check for trafficlight.update
		TrafficLightStatusDTO updatedStatus;
		while (true) {
			updatedStatus = (TrafficLightStatusDTO)
					rabbitTemplate.receiveAndConvert("trafficlight.update", 10000);
			if (updatedStatus == null) {
				fail();
			} else if (updatedStatus.getTrafficLightId() == Constants.TRAFFICLIGHT1.getId() &&
						updatedStatus.getFrom() != 0) {
				break;
			}
		}
		assertEquals(controlDTO.getFrom(), updatedStatus.getFrom());
	}


	@Test
	public void testRestartSimulation_controlTrafficLight_shouldUpdateSchedule() throws InterruptedException, IOException, ClassNotFoundException {

		// send control to ask for green
		TrafficLightControlDTO controlDTO = new TrafficLightControlDTO();
		controlDTO.setTrafficLightId(Constants.TRAFFICLIGHT1.getId());
		// we need green in 5 seconds
		controlDTO.setFrom(timeService.getTime()+5000);
		controlDTO.setLightDTO(LightDTO.GREEN);
		trafficLightSimulationService.controlTrafficLight(controlDTO);

		// check for updated schedule
		List<TrafficLightStatusDTO> updatedStatusList;
		while (true) {
			List<TrafficLightStatusDTO> statusList = (List<TrafficLightStatusDTO>)
					rabbitTemplate.receiveAndConvert("trafficlight.schedule", 10000);
			if (statusList == null || statusList.size() == 0) {
				Assertions.fail();
			} else if (statusList.get(0).getTrafficLightId() == Constants.TRAFFICLIGHT1.getId()) {
				// do not consider first schedule
				if (statusList.get(0).getFrom() != 20000) {
					updatedStatusList = statusList;
					break;
				}
			}
		}

		assertEquals(controlDTO.getFrom(), updatedStatusList.get(0).getFrom());

	}


}
