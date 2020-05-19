package dse.grp20.actorsimulator.service;

import dse.grp20.actorsimulator.entity.Vehicle;
import dse.grp20.actorsimulator.entity.VehicleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Simulator {

    private static Logger LOGGER = LoggerFactory.getLogger(Simulator.class);

    private List<VehicleSimulator> simulators = new ArrayList<>();

    public void runSimulation() throws InterruptedException {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setVin("WVWZZZ1JZ3W386752");
        vehicle1.setModelType("Volkswagen Golf IV");
        VehicleStatus initialStatus = new VehicleStatus();
        initialStatus.setLocation(Constants.VEHICLE1_INITIAL_POSITION);
        initialStatus.setDirection(Constants.VEHICLE1_ENTRY_A_POSITION.minus(Constants.VEHICLE1_INITIAL_POSITION));
        initialStatus.setSpeed(20);
        initialStatus.setVehicleId(vehicle1.getVin());
        VehicleSimulator vs1 = new VehicleSimulator(null, Constants.VEHICLE1_ROUTE, initialStatus);
        simulators.add(vs1);

        Thread t = new Thread(() -> {
            try {
                vs1.simulate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public List<VehicleStatus> getVehicleStati() {
        return simulators.stream().map(VehicleSimulator::getCurrentStatus).collect(Collectors.toList());
    }

    public void setControlStatus(String id, VehicleStatus controlStatus) {
        simulators.stream().filter(vs -> vs.getCurrentStatus().getVehicleId().equals(id)).findFirst().ifPresent(
                vs -> vs.receiveControlStatus(controlStatus)
        );
    }

}
