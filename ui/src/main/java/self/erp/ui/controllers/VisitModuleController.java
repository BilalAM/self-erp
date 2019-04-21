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
    private void saveVisitAct() {
        String a = "halla";
        restfulHelper.post("http://localhost:8880/erp/visit/visitors", a);
    }

}
