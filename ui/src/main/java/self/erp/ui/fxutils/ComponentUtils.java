package self.erp.ui.fxutils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Common components of javafx utility methods here .
 *
 * @author : BilalAM (github.com/BilalAM)
 */
public class ComponentUtils {

    /**
     * Creates a transition effect to the specified component (Node) along with the duration of effect and whether it is
     * a fade-in or fade-out effect.
     *
     * @param nodeToApplyTransition
     *            : The component to which this effect will be applied to .
     * @param duration
     *            : The duration IN SECONDS of the effect.
     * @param isFadeIn
     *            : Do you want a appear-then-poshhh! effect or a posshh!!-then-appear effect ?
     * @return : A Transition effect object .
     */
    public static FadeTransition createTransition(Node nodeToApplyTransition, double duration, boolean isFadeIn) {
        FadeTransition transition = new FadeTransition(Duration.seconds(duration), nodeToApplyTransition);
        if (isFadeIn == true) {
            transition.setFromValue(0.0);
            transition.setToValue(1.0);
        } else {
            transition.setFromValue(1.0);
            transition.setToValue(0.0);
        }
        return transition;
    }

    /**
     * Clears textareas and textfields of its values .
     *
     * @param textInputControls
     *            : Can be either a single or multiple TextArea(s) or TextField(s)
     */
    public static void clearTextboxes(TextInputControl... textInputControls) {
        for (TextInputControl textInputControl : textInputControls) {
            textInputControl.clear();
        }
    }

    /**
     * Creates a standard update modal for any type of module . The size and the fields to include will be implemented
     * by the end user. This just creates a standard modal and freezes the background UI .
     * 
     * @param parentStage
     *            : The parent stage on which this modal is used .
     * @return : The modal as a Stage .
     */
    @Deprecated
    public static Stage createUpdateModal(AnchorPane parentStage) {
        Stage updateModal = new Stage();
        Button closeButton = new Button("Exit");
        closeButton.setMaxSize(50, 50);
        closeButton.setCancelButton(true);
        updateModal.setWidth(500);
        updateModal.setHeight(250);
        updateModal.setX(parentStage.getWidth() / 2 + updateModal.getWidth() / 6);
        updateModal.setY(parentStage.getHeight() / 2 - updateModal.getHeight() / 2);
        updateModal.initOwner(parentStage.getScene().getWindow());
        updateModal.initModality(Modality.APPLICATION_MODAL);
        updateModal.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(closeButton, 500, 250);
        updateModal.setScene(scene);
        return updateModal;
    }
}
