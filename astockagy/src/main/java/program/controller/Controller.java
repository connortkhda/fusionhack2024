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

  public void createStockGraph(String ticker) {
    try {
      @SuppressWarnings({"deprecation", "unused"})
      Process runtime = Runtime.getRuntime().exec("python src\\main\\java\\program\\model\\stockData.py " + ticker);
    } catch (IOException e) {
      System.out.println("Something went wrong with the stock graph");
    }
  }

  //https://www.geeksforgeeks.org/reading-text-file-into-java-hashmap/
  public Map<String, Float> getStockData(String ticker) {
    Map<String, Float> map = new HashMap<String, Float>(); 
    BufferedReader br = null; 
    try { 
      File file = new File("src/main/resources/data/" + ticker + "Data.csv"); 
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

  public Map<Date, Float> getStockDataDates(String ticker) {
    Map<Date, Float> map = new HashMap<Date, Float>();
    for (Map.Entry<String, Float> entry : getStockData(ticker).entrySet()) { 
      map.put(parseDate(entry.getKey()), entry.getValue());
      //System.out.println(entry.getKey() + " : "+ entry.getValue()); 
    }
    return map;
  }

  //https://stackoverflow.com/questions/22326339/how-create-date-object-with-values-in-java
  public static Date parseDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {  
      return null;
    }
  }

  public static Date getLatest() {
    Calendar latest = Calendar.getInstance();
    latest.add(Calendar.MONTH, 1);
    latest.add(Calendar.DAY_OF_MONTH, -4);

    return parseDate(latest.get(Calendar.YEAR) + "-" + latest.get(Calendar.MONTH) + "-" + latest.get(Calendar.DAY_OF_MONTH));
  }

  public static Date[] getMercuryRetrogradeDates() {
    Date[] dates = {parseDate("2015-03-05"),parseDate("2015-07-07"),parseDate("2015-10-31"),parseDate("2020-02-16"),parseDate("2020-06-17"),
    parseDate("2020-10-13"),parseDate("2021-01-30"),parseDate("2021-05-29"),parseDate("2021-09-27"),parseDate("2022-01-14"),parseDate("2022-05-10"),
    parseDate("2022-09-09"),parseDate("2022-12-29"),parseDate("2023-04-21"),parseDate("2023-08-23"),parseDate("2023-12-13")};
    return dates;
  }
}
