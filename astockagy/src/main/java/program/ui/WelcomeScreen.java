package program.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import program.controller.Controller;

public class WelcomeScreen extends StackPane {
  
  public WelcomeScreen(Controller controller) {
    setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

    Label welcomeText = new Label("Welcome to Astockology, please select a button on the left hand side to continue.");

    getChildren().add(welcomeText);
  }
}
