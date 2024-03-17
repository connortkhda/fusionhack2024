package program.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import program.controller.Controller;

public class SideMenu extends VBox {
  Controller controller;

  public SideMenu(Controller controller) {
    this.controller = controller;

    setMaxWidth(170);
    setMaxHeight(Double.MAX_VALUE);
    setSpacing(40);
    setBackground(new Background(new BackgroundFill(Color.rgb(144, 65, 193), CornerRadii.EMPTY, Insets.EMPTY)));
    setPadding(new Insets(20, 20, 20, 20));
    //setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

    ImageView logo = new ImageView(new Image(this.getClass().getResource("/images/logo.png").toExternalForm()));
    logo.setFitHeight(150);
    logo.setFitWidth(150);

    VBox buttons = new VBox();
    buttons.setAlignment(Pos.CENTER);
    //buttons.setFillWidth(true);
    buttons.setSpacing(20);
    Button welcome = sideButton("Welcome");
      welcome.setOnMouseClicked(ev -> {
      controller.showWelcomeScreen();
    });

    Button astrology = sideButton("Astrology");
    astrology.setOnMouseClicked(ev -> {
      controller.showAstrologyScreen();
    });

    Button weather = sideButton("Weather");
      weather.setOnMouseClicked(ev -> {
      controller.showWeatherScreen();
    });

    weather.setDisable(true);
    
    buttons.getChildren().addAll(welcome, astrology, weather);
    getChildren().addAll(logo, buttons);
  }

  private Button sideButton(String text) {
    Button button = new Button(text);
    button.setStyle("-fx-background-color: #c280ea");
    button.setPrefWidth(Double.MAX_VALUE);
    
    button.setOnMouseEntered(ev -> {
      button.setStyle("-fx-background-color: #b84df8");
    });

    button.setOnMouseExited(ev -> {
      button.setStyle("-fx-background-color: #c280ea");
    });
    return button;
  }
}
