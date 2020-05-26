package dse.grp20.statustracking.endpoint.rest;

import dse.grp20.common.dto.VehicleStatusDTO;
import dse.grp20.statustracking.service.IVehicleTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class VehicleStatusEndpoint {

    @Autowired
    IVehicleTrackingService vehicleTrackingService;

    @RequestMapping(value = "status/vehicle/{id}", method = RequestMethod.GET)
    public VehicleStatusDTO findByIdLatest(@PathParam("id") String id) {
       return this.vehicleTrackingService.findByIdLatest(id);
    }

    @RequestMapping(value = "status/vehicle", method = RequestMethod.GET)
    public List<VehicleStatusDTO> findAllLatest() {
        return this.vehicleTrackingService.findAllLatest();
    }
}
