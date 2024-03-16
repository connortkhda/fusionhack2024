package program.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

    //System.setProperty("user.dir", "D:/FusionHack 2024/fusionhack2024/astockagy/src/main/java/program/model");
    System.out.println("Test");
    try {
      @SuppressWarnings("deprecation")
      Process runtime = Runtime.getRuntime().exec("python src\\main\\java\\program\\model\\stockData.py AMD");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // ProcessBuilder pb = new ProcessBuilder("python src\\main\\java\\program\\model\\stockData.py AMD");
    // try {
    //   Process p = pb.start();
    //   //p.waitFor();
    // } catch (IOException e) {
    //   // TODO Auto-generated catch block
    //   e.printStackTrace();
    // }
    // System.out.println(System.getProperty("user.dir"));
    //launchCommand();


  }


private final ExecutorService exec = Executors.newCachedThreadPool();

private String executeCommand(String command) {
  StringBuffer output = new StringBuffer();
  Process p;
  try {
      System.out.println(command);
      p = Runtime.getRuntime().exec(command);
      p.waitFor();
      BufferedReader reader =
              new BufferedReader(new InputStreamReader(p.getInputStream()));
      String line = "";
      while ((line = reader.readLine())!= null) {
          output.append(line + "\n");
      }
  } catch (Exception e) {
      e.printStackTrace();
  }
  return output.toString();
}

public void launchCommand() {
  String command = "python src\\main\\java\\program\\model\\stockData.py AMD";
  Task<String> commandTask = new Task<String>() {
      @Override
      protected String call() {
          return executeCommand(command);
      }
  };
  commandTask.setOnSucceeded(event -> {
      // this is executed on the FX Application Thread, 
      // so it is safe to update the UI here if you need
      System.out.println(commandTask.getValue());
      commandTask.cancel();
      
  });
  commandTask.setOnFailed(event -> {
      commandTask.getException().printStackTrace();
      commandTask.cancel();
  });
  exec.execute(commandTask);

}

}
