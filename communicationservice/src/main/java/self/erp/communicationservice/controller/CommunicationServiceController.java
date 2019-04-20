package self.erp.communicationservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/communication")
public class CommunicationServiceController {
    @RequestMapping(value = "/communicate", method = RequestMethod.GET)
    public String communicate() {
        return "hello from communication service module controller";
    }
}
