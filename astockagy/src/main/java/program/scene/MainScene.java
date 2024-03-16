package program.scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import program.controller.Controller;
import program.ui.AstrologyScreen;
import program.ui.PigeonScreen;
import program.ui.SideMenu;
import program.ui.SwiftScreen;
import program.ui.WeatherScreen;
import program.ui.WelcomeScreen;

public class MainScene extends StackPane {
  HBox mainDisplay;
  Controller controller;

  public MainScene() {
    controller = new Controller();
    controller.setMainScene(this);
    
    mainDisplay = new HBox();

    SideMenu sideMenu = new SideMenu(controller);

    mainDisplay.getChildren().add(sideMenu);
    
    WelcomeScreen welcomeScreen = new WelcomeScreen(controller);
    HBox.setHgrow(welcomeScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(welcomeScreen);

    getChildren().add(mainDisplay);
  }

  public void showWelcomeScreen() {
    mainDisplay.getChildren().remove(1);

    WelcomeScreen welcomeScreen = new WelcomeScreen(controller);
    HBox.setHgrow(welcomeScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(welcomeScreen);
  }

  public void showAstrologyScreen() {
    mainDisplay.getChildren().remove(1);

    AstrologyScreen astrologyScreen = new AstrologyScreen(controller);
    HBox.setHgrow(astrologyScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(astrologyScreen);
  }

  public void showSwiftScreen() {
    mainDisplay.getChildren().remove(1);

    SwiftScreen swiftScreen = new SwiftScreen(controller);
    HBox.setHgrow(swiftScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(swiftScreen);
  }

  public void showWeatherScreen() {
    mainDisplay.getChildren().remove(1);

    WeatherScreen weatherScreen = new WeatherScreen(controller);
    HBox.setHgrow(weatherScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(weatherScreen);
  }

  public void showPigeonScreen() {
    mainDisplay.getChildren().remove(1);

    PigeonScreen pigeonScreen = new PigeonScreen(controller);
    HBox.setHgrow(pigeonScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(pigeonScreen);
  }


  
}
