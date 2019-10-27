package self.erp.ui.controllers.visit;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTimeTextField;
import jfxtras.scene.control.LocalTimePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import self.erp.commons.restful.RestfulHelper;
import self.erp.ui.fxutils.ComponentUtils;
import self.erp.visitorservice.repositories.Visit;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class VisitModuleController {
    private static final Logger LOGGER = Logger.getLogger("VisitModuleController");
    private ObservableList<Visit> list = null;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
    private LocalTimePicker fromTimeField;

    @FXML
    private LocalTimePicker toTimeField;

    @FXML
    private RadioButton completedFlag;
    @FXML
    private RadioButton inProgressFlag;
    @FXML
    private Button saveBtn;

    @FXML
    private Label scsMsgLabel;

    @FXML
    private Label errorMsgLabel;

    @FXML
    private Label todayDateLabel;

    @FXML
    private TableView<Visit> visitGrid;

    @FXML
    private AnchorPane visitStage;

    @FXML
    public void initialize() {
        todayDateLabel.setText(LocalDate.now().format(DATE_FORMAT));
        Visit[] visitors = (Visit[]) restfulHelper.get("http://localhost:8880/erp/visit/visitors", Visit[].class);
        list = FXCollections.observableArrayList(visitors);
        visitGrid.setItems(list);
        visitGrid.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("visitorName"));
        visitGrid.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        visitGrid.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("endDate"));
        visitGrid.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("visitPurpose"));
    }

    @FXML
    private void updateVisitModal() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxmls/visits-update.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((visitStage.getScene().getWindow()));
        stage.show();
    }

    /**
     * Displays a green label message if the response is 200 (added successfully). Displays a red label message if the
     * response is not 200 (not added).
     */
    @FXML
    private void addVisitAction() {
        FadeTransition fadeTransition;
        LocalDate todayDateParsed = LocalDate.parse(todayDateLabel.getText(), DATE_FORMAT);
        LocalTime toTimeParsed = toTimeField.getLocalTime();
        LocalTime fromTimeParsed = fromTimeField.getLocalTime();
        LocalDateTime fromDateTimeParsed = LocalDateTime.of(todayDateParsed, fromTimeParsed);
        LocalDateTime toDateTimeParsed = LocalDateTime.of(todayDateParsed, toTimeParsed);

        Visit visit = new Visit();
        visit.setVisitorName(visitorNameField.getText());
        visit.setVisitPurpose(visitorVisitPurposeField.getText());
        visit.setVisitPurposeDescription(visitorVisitPurposeDescriptionField.getText());
        visit.setFromDate(fromDateTimeParsed);
        visit.setEndDate(toDateTimeParsed);

        if (completedFlag.isSelected()) {
            visit.setVisitPurposeStatusType("Completed");
        } else if (inProgressFlag.isSelected()) {
            visit.setVisitPurposeStatusType("In Progress");
        }
        Response response = null;
        try {
            response = restfulHelper.post("http://localhost:8880/erp/visit/new", visit);
        } catch (ProcessingException procException) {
            LOGGER.log(Level.SEVERE, "Error , see logs", procException);
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText("Connection error has occurred !");
        }
        // if all went OK with the request.
        if ((response.getStatus() == HttpStatus.OK.value())) {
            errorMsgLabel.setVisible(false);
            scsMsgLabel.setVisible(true);
            // play green transition
            fadeTransition = ComponentUtils.createTransition(scsMsgLabel, 1.9, false);
            fadeTransition.play();
            // update the grid.
            visitGrid.getItems().add(visit);
            // clear the fields.
            ComponentUtils.clearTextboxes(visitorNameField, visitorVisitPurposeField,
                    visitorVisitPurposeDescriptionField);

        } else {
            scsMsgLabel.setVisible(false);
            errorMsgLabel.setVisible(true);
            errorMsgLabel.setText("Unable to process request !");
            fadeTransition = ComponentUtils.createTransition(errorMsgLabel, 1.9, false);
            fadeTransition.play();
        }
    }
}
