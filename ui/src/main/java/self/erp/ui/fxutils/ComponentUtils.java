package self.erp.ui.fxutils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
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
}
