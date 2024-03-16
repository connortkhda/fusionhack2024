package program;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import program.scene.MainScene;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new MainScene(), 975, 800);
        stage.setScene(scene);
        stage.setTitle("Astockagy");
        stage.getIcons().add(new Image(this.getClass().getResource("/images/logo.png").toExternalForm()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}