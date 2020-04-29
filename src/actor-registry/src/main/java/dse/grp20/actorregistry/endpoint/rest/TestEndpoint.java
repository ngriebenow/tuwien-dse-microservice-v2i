package dse.grp20.actorregistry.endpoint.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TestEndpoint {

    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public String get() {
        return "Hello World";
    }

}
