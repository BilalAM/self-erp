package self.erp.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import self.erp.commons.restful.RestfulHelper;
import self.erp.visitorservice.repositories.Visit;

import javax.ws.rs.core.Response;
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

    @FXML private void getVisitsAction() {
            Visit[] visitors = (Visit[]) restfulHelper.get("http://localhost:8880/erp/visit/visitors", Visit[].class);
            for (Visit visit : visitors) {
                    visitorNameField.appendText(visit.getVisitorName());
                    visitorVisitPurposeField.appendText(visit.getVisitPurpose());
                    visitorVisitPurposeDescriptionField.appendText(visit.getVisitPurposeDescription());
                    fromDateField.setText(visit.getFromDate().toString());
                    toDateField.setText(visit.getEndDate().toString());
            }
    }

        @FXML private void addVisitAction() {
                Visit visit = new Visit();
                visit.setVisitorName(visitorNameField.getText());
                visit.setVisitPurpose(visitorVisitPurposeField.getText());
                visit.setVisitPurposeDescription(visitorVisitPurposeDescriptionField.getText());
                visit.setFromDate(fromDateField.getLocalDateTime());
                visit.setEndDate(toDateField.getLocalDateTime());
                if (completedFlag.isSelected()) {
                        visit.setVisitPurposeStatusType("Completed");
                } else if (inProgressFlag.isSelected()) {
                        visit.setVisitPurposeStatusType("In Progress");
                }
                Response response = restfulHelper.post("http://localhost:8880/erp/visit/new", visit);
                if (!(response.getStatus() == HttpStatus.OK.value())) {
                        // display a red message on UI
                } else {
                        // display a green label , record [ record.toString() ] added
                }
        }
}
