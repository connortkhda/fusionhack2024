package program;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    //@SuppressWarnings("exports")
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new StackPane(), 975, 800);
        stage.setScene(scene);
        stage.setTitle("Runway Redeclaration Tool");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}