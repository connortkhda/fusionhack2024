package program.ui;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import program.controller.Controller;
import java.nio.file.*;
import java.util.Map;

public class AstrologyScreen extends StackPane {

  public AstrologyScreen(Controller controller) {
    controller.createStockGraph();

    VBox display = new VBox();

    HBox inputs = new HBox();
    TextField ticker = new TextField();
    ticker.setPromptText("Input stock ticker");
    ComboBox<String> event = new ComboBox<>();
    event.setPromptText("Select celestial event");
    Button submit = new Button("Submit");
    inputs.getChildren().addAll(ticker,event,submit);

    display.getChildren().add(inputs);
    getChildren().add(display);

    HBox chartAndData = new HBox();
    VBox data = new VBox();
    data.setSpacing(10);
    data.getChildren().addAll(new Button("Testtttttt"), new Button());
    data.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

    display.getChildren().add(chartAndData);

    // TODO: Implement screen switching listener and cancel event if triggered
    //https://stackoverflow.com/questions/44398611/running-a-process-in-a-separate-thread-so-rest-of-java-fx-application-is-usable
    Task<Void> executeAppTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        displayGraph();
        return null;
      }
    };

    executeAppTask.setOnSucceeded(e -> {
      ImageView chart = new ImageView(new Image(this.getClass().getResource("/images/AMD.png").toExternalForm())); // TODO: Change from fixed value

      // for (Map.Entry<String, Float> entry : controller.getStockData().entrySet()) { 
      //   System.out.println(entry.getKey() + " : "+ entry.getValue()); 
      // }

      //Map<String, Float> stockData = controller.getStockData();
      //System.out.println(stockData.get("2022-08-02"));
      controller.getStockDataDates();

      chartAndData.getChildren().add(chart); 
      chartAndData.getChildren().add(data);
    });

    executeAppTask.setOnFailed(e -> {
      System.out.println("f");
    });

    executeAppTask.setOnCancelled(e -> {
      System.out.println("c");
    });

    Thread thread = new Thread(executeAppTask);
    thread.start();
  }

  private void displayGraph() {
    try {
      Path directoryPath = Paths.get("src/main/resources/images");
      WatchService watchService = FileSystems.getDefault().newWatchService();
      directoryPath.register(watchService,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_DELETE,
        StandardWatchEventKinds.ENTRY_MODIFY);

      System.out.println("Watching directory: " + directoryPath);

      boolean noUpdates = true;
      while (noUpdates) {
        WatchKey key = watchService.take();
          for (WatchEvent<?> event : key.pollEvents()) {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
              System.out.println("File created: " + event.context());
              noUpdates = false; 
            } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
              System.out.println("File deleted: " + event.context());
              noUpdates = false;
            } else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
              System.out.println("File modified: " + event.context());
              noUpdates = false;
            }
          }
        key.reset();
      }
      Thread.sleep(2000);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  } 
}
