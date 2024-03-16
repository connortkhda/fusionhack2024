package program.controller;

import java.util.HashMap;
import java.util.Map;
import program.scene.MainScene;
import java.io.*; 
import java.util.*; 

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

  public Map<String, String> test() {
    Map<String, String> mapFromFile = HashMapFromTextFile(); 
  
        // iterate over HashMap entries 
    for (Map.Entry<String, String> entry : mapFromFile.entrySet()) { 
      System.out.println(entry.getKey() + " : "+ entry.getValue()); 
    }
    return mapFromFile; 
  } 
  
    public static Map<String, String> HashMapFromTextFile() { 
        Map<String, String> map 
            = new HashMap<String, String>(); 
        BufferedReader br = null; 
  
        try { 
  
            // create file object 
            File file = new File("src/main/resources/data/AMDData.csv"); 
  
            // create BufferedReader object from the File 
            br = new BufferedReader(new FileReader(file)); 
  
            String line = null; 
  
            // read file line by line 
            while ((line = br.readLine()) != null) { 
  
              // split the line by : 
              String[] parts = line.split(","); 
  
              // first part is name, second is number 
              String name = parts[0].trim(); 
              String number = parts[1].trim(); 
  
              // put name, number in HashMap if they are 
              // not empty 
              map.put(name, number); 
            } 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
        finally { 
  
            // Always close the BufferedReader 
            if (br != null) { 
                try { 
                    br.close(); 
                } 
                catch (Exception e) { 
                }; 
            } 
        } 
    return map;  
  }
}
