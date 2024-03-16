package program.scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import program.controller.Controller;
import program.ui.SideMenu;
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

    WelcomeScreen welcomeScreen = new WelcomeScreen(controller);
    HBox.setHgrow(welcomeScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(welcomeScreen);
  }

  public void showSwiftScreen() {
    mainDisplay.getChildren().remove(1);

    WelcomeScreen welcomeScreen = new WelcomeScreen(controller);
    HBox.setHgrow(welcomeScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(welcomeScreen);
  }

  public void showWeatherScreen() {
    mainDisplay.getChildren().remove(1);

    WelcomeScreen welcomeScreen = new WelcomeScreen(controller);
    HBox.setHgrow(welcomeScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(welcomeScreen);
  }

  public void showPigeonScreen() {
    mainDisplay.getChildren().remove(1);

    WelcomeScreen welcomeScreen = new WelcomeScreen(controller);
    HBox.setHgrow(welcomeScreen, Priority.ALWAYS);

    mainDisplay.getChildren().add(welcomeScreen);
  }


  
}
