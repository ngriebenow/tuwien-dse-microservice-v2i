package dse.grp20.actorregistry.endpoint.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
/**
 * This class receives incoming GET REST requests and returns the results from the services.
 */
public class ActorRegistryEndpoint {

    // TODO: add REST endpoints
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public String get() {
        return "Hello World";
    }

}
