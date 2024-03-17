package program.ui;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AstrologyScreen extends StackPane {
  final Date START_DATE = Controller.parseDate("2019-03-22");
  final Date END_DATE = Controller.getLatest();
  Map<Date, Float> stockData;

  public AstrologyScreen(Controller controller) {
    VBox display = new VBox();

    HBox inputs = new HBox();
    TextField ticker = new TextField();
    ticker.setPromptText("Input stock ticker");
    ComboBox<String> event = new ComboBox<>();
    event.setPromptText("Select celestial event");
    event.getItems().add("Mercury in retrograde");
    Button submit = new Button("Submit");

    inputs.getChildren().addAll(ticker,event,submit);

    display.getChildren().add(inputs);
    getChildren().add(display);

    HBox chartAndData = new HBox();

    VBox data = new VBox();
    data.setAlignment(Pos.TOP_CENTER);
    data.setSpacing(10);
    Label information = new Label("Information");
    ComboBox<String> eventDate = new ComboBox<>();
    eventDate.setPromptText("Select Event Date");
    TextField holdPeriod = new TextField();
    holdPeriod.setPromptText("Holding period");
    holdPeriod.setText("0");
    Label priceAtStart = new Label("Price at start of range: ");
    Label priceAtEnd = new Label("Price at end of range: ");
    Label increaseAtEnd = new Label("$.. ..%");
    Label priceAtEvent = new Label("Price at event: ");
    Label priceEODEvent = new Label("Price at EOD of event: ");
    Label increaseEODEvent = new Label("$.. ..%");
    Label priceAfterHolding = new Label("Price after holding: ");
    Label increaseAfterHolding = new Label("$.. ..%");

    data.getChildren().addAll(information, eventDate, holdPeriod, priceAtStart, priceAtEnd, increaseAtEnd, priceAtEvent, priceEODEvent, increaseEODEvent, priceAfterHolding, increaseAfterHolding);
    data.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

    display.getChildren().add(chartAndData);

    // TODO: Implement screen switching listener and cancel event if triggered
    //https://stackoverflow.com/questions/44398611/running-a-process-in-a-separate-thread-so-rest-of-java-fx-application-is-usable
    submit.setOnMouseClicked(ev -> { 
      controller.createStockGraph(ticker.getText()); 
    Task<Void> executeAppTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        displayGraph();
        return null;
      }
    };

    executeAppTask.setOnSucceeded(e -> { //TODO: THIS PROBABLY ALL NEEDS TO BE IN A TRY CATCH BLOCK
      ImageView chart = new ImageView(new Image(this.getClass().getResource("/images/"+ ticker.getText() + ".png").toExternalForm())); 

      // for (Map.Entry<String, Float> entry : controller.getStockData().entrySet()) { 
      //   System.out.println(entry.getKey() + " : "+ entry.getValue()); 
      // }

      //Map<String, Float> stockData = controller.getStockData();
      //System.out.println(stockData.get("2022-08-02"));
      this.stockData = controller.getStockDataDates(ticker.getText());
      System.out.println(END_DATE.toString());
      System.out.println(START_DATE.toString());
      chartAndData.getChildren().add(chart); 
      chartAndData.getChildren().add(data);

      priceAtStart.setText("Price at start of range: " + stockData.get(START_DATE));
      priceAtEnd.setText("Price at end of range: " + stockData.get(END_DATE));

      DecimalFormat twoDP = new DecimalFormat("#.##");
      increaseAtEnd.setText("$" + (stockData.get(END_DATE) - stockData.get(START_DATE)) + " " + Double.valueOf(twoDP.format((stockData.get(END_DATE) - stockData.get(START_DATE))/stockData.get(START_DATE)*100)) + "%");

    });

    executeAppTask.setOnFailed(e -> {
      System.out.println("f");
    });

    executeAppTask.setOnCancelled(e -> {
      System.out.println("c");
    });

    Thread thread = new Thread(executeAppTask);
    thread.start();
    });

    event.setOnAction(ev -> {
      if (event.getValue() != null) {
        eventDate.getItems().clear();
        eventDate.getItems().addAll(Controller.getMercuryRetrogradeStrings());
      }
    });

    eventDate.setOnAction(ev -> {
      if (eventDate.getValue() != null) {
        String[] yearMonthDay = eventDate.getValue().split("-");
        Calendar dayBefore = Calendar.getInstance();
        dayBefore.set(Calendar.YEAR, Integer.parseInt(yearMonthDay[0]));
        dayBefore.set(Calendar.MONTH, Integer.parseInt(yearMonthDay[1]));
        dayBefore.set(Calendar.DAY_OF_MONTH, Integer.parseInt(yearMonthDay[2]));
        dayBefore.add(Calendar.DAY_OF_MONTH, -1); //TODO: If two things are trying to find the same call here (eg day of and day before), they get the same thing

        DecimalFormat twoDP = new DecimalFormat("#.##");

        //Date dayBeforeDate = Controller.parseDate(dayBefore.get(Calendar.YEAR) + "-" + dayBefore.get(Calendar.MONTH) + "-" + dayBefore.get(Calendar.DAY_OF_MONTH));
        //Date dateOfEvent = Controller.parseDate(eventDate.getValue());
        Date dayBeforeDate = Controller.getClosestMarketDayPrior(dayBefore.get(Calendar.YEAR) + "-" + dayBefore.get(Calendar.MONTH) + "-" + dayBefore.get(Calendar.DAY_OF_MONTH), stockData);
        Date dateOfEvent = Controller.getClosestMarketDayPrior(eventDate.getValue(), stockData);


        priceAtEvent.setText("Price at event: " + stockData.get(dayBeforeDate));
        priceEODEvent.setText("Price at EOD of event: " + stockData.get(dateOfEvent));
        increaseEODEvent.setText("$" + Double.valueOf(twoDP.format(stockData.get(dateOfEvent) - stockData.get(dayBeforeDate)) )+ " " + Double.valueOf(twoDP.format((stockData.get(dateOfEvent) - stockData.get(dayBeforeDate))/stockData.get(dayBeforeDate)*100)) + "%");

        Calendar dateAfterHolding = Calendar.getInstance();
        dateAfterHolding.set(Calendar.YEAR, Integer.parseInt(yearMonthDay[0]));
        dateAfterHolding.set(Calendar.MONTH, Integer.parseInt(yearMonthDay[1]));
        dateAfterHolding.set(Calendar.DAY_OF_MONTH, Integer.parseInt(yearMonthDay[2]));
        dateAfterHolding.add(Calendar.DAY_OF_MONTH, Integer.parseInt(holdPeriod.getText()));
        //Date dateHolding = Controller.parseDate(dateAfterHolding.get(Calendar.YEAR) + "-" + dateAfterHolding.get(Calendar.MONTH) + "-" + dateAfterHolding.get(Calendar.DAY_OF_MONTH));
        Date dateHolding = Controller.getClosestMarketDayPrior((dateAfterHolding.get(Calendar.YEAR) + "-" + dateAfterHolding.get(Calendar.MONTH) + "-" + dateAfterHolding.get(Calendar.DAY_OF_MONTH)), stockData);

        priceAfterHolding.setText("Price after holding: " + (stockData.get(dateHolding)));
        increaseAfterHolding.setText("$" + Double.valueOf(twoDP.format((stockData.get(dateHolding) - stockData.get(dayBeforeDate)))) + " " + Double.valueOf(twoDP.format((stockData.get(dateHolding) - stockData.get(dayBeforeDate))/stockData.get(dayBeforeDate)*100)) + "%");
        
        System.out.println(Controller.getClosestMarketDayPrior("2019-10-25", stockData));
      }
    });


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
