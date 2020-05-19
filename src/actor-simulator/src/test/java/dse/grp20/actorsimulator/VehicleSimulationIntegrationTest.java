package dse.grp20.actorsimulator;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dse.grp20.actorsimulator.service.Constants;
import dse.grp20.actorsimulator.service.impl.VehicleSimulationService;
import dse.grp20.common.dto.VehicleDTO;
import dse.grp20.common.dto.VehicleStatusDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("dev")
class VehicleSimulationIntegrationTest {

	@Autowired
	private VehicleSimulationService vehicleSimulationService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void testRestartSimulation_shouldRegisterVehicle() throws InterruptedException, IOException, ClassNotFoundException {
		vehicleSimulationService.restartSimulation();

		VehicleDTO vehicleDTO = (VehicleDTO)rabbitTemplate.receiveAndConvert("vehicle.register");
		assertEquals(vehicleDTO.getVin(), Constants.VEHICLE1.getVin());
		assertEquals(vehicleDTO.getModelType(), Constants.VEHICLE1.getModelType());
		assertEquals(vehicleDTO.getOem(), Constants.VEHICLE1.getOem());
	}

	@Test
	public void testRestartSimulation_shouldSimulateVehicle() throws InterruptedException, IOException, ClassNotFoundException {
		vehicleSimulationService.restartSimulation();

		VehicleStatusDTO vehicleStatusDTO1 = (VehicleStatusDTO)rabbitTemplate.receiveAndConvert("vehicle.update", 10000);
		VehicleStatusDTO vehicleStatusDTO2 = (VehicleStatusDTO)rabbitTemplate.receiveAndConvert("vehicle.update", 10000);
		VehicleStatusDTO vehicleStatusDTO3 = (VehicleStatusDTO)rabbitTemplate.receiveAndConvert("vehicle.update", 10000);

		Assertions.assertNotEquals(vehicleStatusDTO1.getLocation(), vehicleStatusDTO2.getLocation());
		Assertions.assertNotEquals(vehicleStatusDTO2.getLocation(), vehicleStatusDTO3.getLocation());

		Assertions.assertTrue(vehicleStatusDTO1.getTime() < vehicleStatusDTO2.getTime());
		Assertions.assertTrue(vehicleStatusDTO2.getTime() < vehicleStatusDTO3.getTime());

	}


}
