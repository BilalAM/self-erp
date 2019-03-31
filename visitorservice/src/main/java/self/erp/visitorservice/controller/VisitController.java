package self.erp.visitorservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import self.erp.visitorservice.repositories.Visit;
import self.erp.visitorservice.repositories.VisitRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/visit")
public class VisitController {

    private static final Logger LOGGER = Logger.getLogger("VisitController");

    @Autowired
    private VisitRepository visitRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity<String> insert(@RequestBody Visit visit) {
        LOGGER.log(Level.INFO, "Adding [ " + visit.toString() + " ]");
        visitRepository.save(visit);
        return new ResponseEntity<>("Added", HttpStatus.OK);
    }
}
