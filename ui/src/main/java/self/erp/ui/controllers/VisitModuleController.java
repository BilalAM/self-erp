package self.erp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import self.erp.commons.restful.RestfulHelper;
import self.erp.visitorservice.repositories.Visit;

import java.util.List;
import java.util.logging.Logger;

@Component
public class VisitModuleController {
    private static final Logger LOGGER = Logger.getLogger("VisitModuleController");

    @Autowired
    private RestfulHelper restfulHelper;

    @FXML
    private TextField visitorNameField;
    @FXML
    private TextField visitorVisitPurposeField;
    @FXML
    private TextArea visitorVisitPurposeDescriptionField;
    @FXML
    private LocalDateTimeTextField fromDateField;
    @FXML
    private LocalDateTimeTextField toDateField;
    @FXML
    private RadioButton completedFlag;
    @FXML
    private RadioButton inProgressFlag;
    @FXML
    private Button saveBtn;

    @FXML
    private void getVisitsAction() {
        List<Visit> visitors = (List<Visit>) restfulHelper.get("http://localhost:8880/erp/visit/visitors");
        for (Visit visit : visitors) {
            visitorNameField.appendText(visit.toString());
        }
    }
}
