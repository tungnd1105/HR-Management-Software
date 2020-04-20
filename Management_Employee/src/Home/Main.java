
package Home;

import Home.helper.Share;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Share.primaryStage = stage;
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Home/gui/Flash.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
