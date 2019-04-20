package self.erp.visitorservice.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.erp.visitorservice.repositories.Visit;
import self.erp.visitorservice.repositories.VisitRepository;

import java.util.List;
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

    /**
     * Gets all the visits from the underlying datasource according to the sorting field . If no sorting field is
     * specified , then default is according to the visitor name.
     *
     * @param sortByField
     *            : The field to which sort the data.
     * @return : A list of all the visits according to th sorting field.
     */
    @RequestMapping(value = "/visitors", method = RequestMethod.GET)
    public ResponseEntity<List<Visit>> getAll(@RequestParam(required = false) String sortByField) {
        List<Visit> allVisits;
        try {
            if (StringUtils.isEmpty(sortByField)) {
                LOGGER.log(Level.WARNING, "No sorting field found");
                allVisits = visitRepository.findAll(Sort.by(Sort.Order.by("visitorName")));
            } else {
                LOGGER.log(Level.WARNING, "Bringing records according to [ " + sortByField + " ]");
                allVisits = visitRepository.findAll(Sort.by(Sort.Order.by(sortByField)));
            }
        } catch (Exception ioE) {
            String errorMessage = "Something has gone horribly wrong :(";
            LOGGER.log(Level.SEVERE, errorMessage, ioE);
            throw new RuntimeException(errorMessage);
        }
        return new ResponseEntity<>(allVisits, HttpStatus.OK);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Visit> getOne() {
        return null;
    }
}
