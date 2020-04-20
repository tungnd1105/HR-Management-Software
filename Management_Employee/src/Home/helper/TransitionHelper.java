package Home.helper;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TransitionHelper {

    public static ParallelTransition createTransition(long delay, long duration, double transalteX, Node node) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(duration));
        translate.setFromX(transalteX);
        translate.setToX(0);

        FadeTransition fade = new FadeTransition(Duration.millis(duration));
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(translate, fade);
        parallelTransition.setNode(node);
        parallelTransition.setDelay(Duration.millis(delay));

        return parallelTransition;
    }
    
    public static FadeTransition creFadeInTransition(long duration, Node node){
        FadeTransition fade = new FadeTransition(Duration.millis(duration));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setNode(node);
        
        return fade;
    }
    
    public static void fadeOutStage(long duration, Stage stage){
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(duration), new KeyValue(stage.getScene().getRoot().opacityProperty(), 0.01));
        timeline.getKeyFrames().add(key);
        timeline.setOnFinished((ae) -> stage.close());
        timeline.play();
    }
    
    public static void fadeInStage(long duration, Stage stage){
        Timeline timeline = new Timeline();
        KeyFrame key = new KeyFrame(Duration.millis(duration), new KeyValue(stage.getScene().getRoot().opacityProperty(), 1));
        timeline.getKeyFrames().add(key);
        timeline.play();
    }
}
