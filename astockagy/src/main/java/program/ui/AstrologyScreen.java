package program.ui;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import program.controller.Controller;
import java.nio.file.*;

public class AstrologyScreen extends StackPane {

  public AstrologyScreen(Controller controller) {
    controller.createStockGraph();

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
      getChildren().add(chart); 
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
