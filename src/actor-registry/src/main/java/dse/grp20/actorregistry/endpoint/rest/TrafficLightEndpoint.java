package dse.grp20.actorregistry.endpoint.rest;

import dse.grp20.actorregistry.service.ITrafficLightRegistryService;
import dse.grp20.actorregistry.service.IVehicleRegistryService;
import dse.grp20.common.dto.TrafficLightDTO;
import dse.grp20.common.dto.VehicleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/")
/**
 * This class receives incoming GET REST requests and returns the results from the service.
 */
public class TrafficLightEndpoint {

    @Autowired
    private ITrafficLightRegistryService trafficLightRegistryService;

    @RequestMapping(value = "/trafficlight", method = RequestMethod.GET)
    public List<TrafficLightDTO> getTrafficlights() {
        return trafficLightRegistryService.findAll();
    }

}
