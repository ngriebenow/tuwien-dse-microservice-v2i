package dse.grp20.actorsimulator.endpoint.rest;

import dse.grp20.actorsimulator.service.ITimeService;
import dse.grp20.actorsimulator.service.ITrafficLightSimulationService;
import dse.grp20.actorsimulator.service.IVehicleSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
/**
 * This class receives incoming GET REST requests and returns the results from the service.
 */
public class SimulationEndpoint {

    @Autowired
    private ITimeService timeService;

    @Autowired
    private IVehicleSimulationService vehicleSimulationService;

    @Autowired
    private ITrafficLightSimulationService trafficLightSimulationService;

    @RequestMapping(value = "/simulation", method = RequestMethod.POST)
    public void restartSimulation(@RequestParam double factor) {
        vehicleSimulationService.stopSimulation();
        trafficLightSimulationService.stopSimulation();

        timeService.setSimulationSpeed(factor);
        timeService.restartSimulation();
        vehicleSimulationService.restartSimulation();
        trafficLightSimulationService.restartSimulation();

    }

}
