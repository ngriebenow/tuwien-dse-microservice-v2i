package dse.grp20.statustracking.endpoint.rest;

import dse.grp20.statustracking.service.ITimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/")
public class TimeServiceEndpoint {

    @Autowired
    ITimeService timeService;

    @RequestMapping(value = "/time/{time}/{speed}", method = RequestMethod.GET)
    public void setTime(@PathParam("time") long time, @PathParam("speed") double speed) {
        this.timeService.setTime(time,speed);
    }
}
