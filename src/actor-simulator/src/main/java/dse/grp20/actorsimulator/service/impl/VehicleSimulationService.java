package dse.grp20.actorsimulator.service.impl;

import dse.grp20.actorsimulator.entity.Vehicle;
import dse.grp20.actorsimulator.entity.VehicleControl;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import dse.grp20.actorsimulator.external.IActorRegistryService;
import dse.grp20.actorsimulator.external.IStatusTrackingService;
import dse.grp20.actorsimulator.service.Constants;
import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.actorsimulator.service.IVehicleSimulationService;
import dse.grp20.common.dto.VehicleControlDTO;
import dse.grp20.common.dto.VehicleDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class VehicleSimulationService implements IVehicleSimulationService {

    @Autowired
    private IActorRegistryService actorRegistryService;

    @Autowired
    private IStatusTrackingService statusTrackingService;

    @Autowired
    private ITimeService timeService;

    private static Logger LOGGER = LoggerFactory.getLogger(VehicleSimulationService.class);

    private Map<String, VehicleSimulator> simulators = new HashMap<>();

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public void controlVehicle(VehicleControlDTO vehicleControlDTO) {
        VehicleControl control = modelMapper.map(vehicleControlDTO, VehicleControl.class);
        simulators.get(vehicleControlDTO.getVin()).receiveControlStatus(control);
    }

    @Override
    public void stopSimulation() {
        LOGGER.info("stopping vehicle simulations");
        simulators.entrySet().forEach(e -> {
            e.getValue().stop();
        });
    }

    @Override
    public void restartSimulation() {
        Vehicle vehicle1 = Constants.VEHICLE1;

        // register vehicle
        actorRegistryService.registerVehicle(modelMapper.map(vehicle1, VehicleDTO.class));

        VehicleStatus initialStatus = new VehicleStatus();
        initialStatus.setLocation(Constants.VEHICLE1_INITIAL_POSITION);
        initialStatus.setDirection(Constants.VEHICLE1_ENTRY_A_POSITION.minus(Constants.VEHICLE1_INITIAL_POSITION));
        initialStatus.setSpeed(20);
        initialStatus.setVin(vehicle1.getVin());
        VehicleSimulator vs1 = new VehicleSimulator(null, new ArrayList<>(Constants.VEHICLE1_ROUTE), initialStatus,
                timeService, statusTrackingService);
        simulators.put(vehicle1.getVin(), vs1);

        Thread t = new Thread(vs1::simulate);
        t.start();
    }
}
