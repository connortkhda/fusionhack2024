package program.controller;

import program.scene.MainScene;
import java.io.*; 
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

  //https://www.geeksforgeeks.org/reading-text-file-into-java-hashmap/
  public Map<String, Float> getStockData() {
    Map<String, Float> map = new HashMap<String, Float>(); 
    BufferedReader br = null; 
    try { 
      File file = new File("src/main/resources/data/AMDData.csv"); 
      br = new BufferedReader(new FileReader(file)); 
      String line = br.readLine(); //Gets rid of first line
      while ((line = br.readLine()) != null) { 
        String[] parts = line.split(","); 
        String name = parts[0].trim(); 
        Float number = Float.parseFloat(parts[1].trim()); 
        map.put(name, number); 
      } 
    } catch (Exception e) { 
      e.printStackTrace(); 
    } finally { 
      if (br != null) { 
        try { 
          br.close(); 
        } catch (Exception e) { 
          System.out.println("Error reading CSV data to HashMap");
        }; 
      } 
    } 
    return map;  
  }

  public Map<Date, Float> getStockDataDates() {
    Map<Date, Float> map = new HashMap<Date, Float>();
    for (Map.Entry<String, Float> entry : getStockData().entrySet()) { 
      map.put(parseDate(entry.getKey()), entry.getValue());
      System.out.println(entry.getKey() + " : "+ entry.getValue()); 
    }
    return map;
  }

  public static Date parseDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {  
      return null;
    }
  }
}
