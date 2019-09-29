package self.erp.visitorservice.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import self.erp.visitorservice.repositories.Visit;
import self.erp.visitorservice.repositories.VisitRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/visit")
public class VisitController {

    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger("VisitController");

    @Autowired
    private VisitRepository visitRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insert(@RequestBody Visit visit) {
        LOGGER.info("Adding [ " + visit.toString() + " ]");
        visitRepository.save(visit);
        return new ResponseEntity<>("Added", HttpStatus.OK);
    }

    @RequestMapping(value = "/visitor/{id}", method = RequestMethod.GET)
    public ResponseEntity<Visit> getOne(@PathVariable(name = "id") int id) {
        Optional<Visit> visit;
        LOGGER.info("Looking up visitor id [ " + id + " ]");
        visit = visitRepository.findById(id);
        if (visit.isPresent()) {
            return new ResponseEntity<Visit>(visit.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(visit.orElse(new Visit()), HttpStatus.NOT_FOUND);
        }
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
                LOGGER.warn("No sorting field found");
                allVisits = visitRepository.findAll(Sort.by(Sort.Order.by("visitorName")));
            } else {
                LOGGER.warn("Bringing records according to [ " + sortByField + " ]");
                allVisits = visitRepository.findAll(Sort.by(Sort.Order.by(sortByField)));
            }
        } catch (Exception ioE) {
            String errorMessage = "Something has gone horribly wrong :(";
            LOGGER.error(errorMessage, ioE);
            throw new RuntimeException(errorMessage);
        }
        return new ResponseEntity<>(allVisits, HttpStatus.OK);
    }

    @RequestMapping(value = "/lastVisitID", method = RequestMethod.GET)
    public ResponseEntity<Integer> getLastVisitID() {
        int lastVisitID;
        try {
            lastVisitID = visitRepository.getlastVisitID();
            return new ResponseEntity<>(lastVisitID, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Unable to find last ID of visitor record";
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage);
        }
    }
}
