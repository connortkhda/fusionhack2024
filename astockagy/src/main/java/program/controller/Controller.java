package program.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    //try {
    //   @SuppressWarnings({"deprecation", "unused"})
    //   Process runtime = Runtime.getRuntime().exec("python src\\main\\java\\program\\model\\stockData.py AMD");
    // } catch (IOException e) {
    //   System.out.println("Something went wrong with the stock grpah");
    // }

    try {
      String filePath = "src\\main\\java\\program\\model\\stockData.py";      
      ProcessBuilder pb = new ProcessBuilder().command("python", filePath, "AMD");        
      Process p = pb.start(); 
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      StringBuilder buffer = new StringBuilder();     
      String line = null;
      while ((line = in.readLine()) != null){           
        buffer.append(line);
      }
      int exitCode = p.waitFor();
      System.out.println("Value is: "+buffer.toString());                
      System.out.println("Process exit value:"+exitCode);        
      in.close();
    } catch (Exception e) {
      System.out.println("Something went wrong reading python data");
    }
  }
}
