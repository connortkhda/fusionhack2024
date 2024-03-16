package program.controller;

import java.io.IOException;
import program.scene.MainScene;

public class Controller {
  private MainScene mainScene;

  public Controller() {

  }

  public void setMainScene(MainScene mainScene) {
    this.mainScene = mainScene;
  }

  public MainScene getMainScene() {
    return mainScene;
  }

  public void showWelcomeScreen() {
    mainScene.showWelcomeScreen();
  }

  public void showAstrologyScreen() {
    mainScene.showAstrologyScreen();
  }

  public void showSwiftScreen() {
    mainScene.showSwiftScreen();
  }

  public void showWeatherScreen() {
    mainScene.showWeatherScreen();
  }

  public void showPigeonScreen() {
    mainScene.showPigeonScreen();
  }

  public void createStockGraph() {
    try {
      @SuppressWarnings({"deprecation", "unused"})
      Process runtime = Runtime.getRuntime().exec("python src\\main\\java\\program\\model\\stockData.py AMD");
    } catch (IOException e) {
      System.out.println("Something went wrong with the stock grpah");
    }
  }

}
