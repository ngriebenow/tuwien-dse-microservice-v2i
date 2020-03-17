package dse.grp20.vit;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/echo")
public class TestEndpoint {

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "Hello World";
    }

}
