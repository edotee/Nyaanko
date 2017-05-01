package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author edotee
 */
public class NyaankoGUI extends Application {

    private final double WIDTH = 480.0f;
    private final double HEIGHT = 854.0f;

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("Nyaanko");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setPrefSize(WIDTH, HEIGHT);


        Scene mainScene = new Scene(mainLayout);
        mainStage.setScene(mainScene);
        mainStage.sizeToScene();
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
