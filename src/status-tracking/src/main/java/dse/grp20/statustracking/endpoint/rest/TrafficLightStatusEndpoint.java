package dse.grp20.statustracking.endpoint.rest;


import dse.grp20.common.dto.TrafficLightStatusDTO;
import dse.grp20.statustracking.service.ITrafficLightTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TrafficLightStatusEndpoint {

    @Autowired
    ITrafficLightTrackingService trafficLightTrackingService;

    @RequestMapping(value = "status/trafficlight/{id}", method = RequestMethod.GET)
    public TrafficLightStatusDTO findByIdLatest(@PathParam("id") long id) {
        return this.trafficLightTrackingService.findByIdLatest(id);
    }

    @RequestMapping(value = "status/trafficlight", method = RequestMethod.GET)
    public List<TrafficLightStatusDTO> findAllLatest() {
        return this.trafficLightTrackingService.findAllLatest();
    }
}
