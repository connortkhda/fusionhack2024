package program.ui;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import program.controller.Controller;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AstrologyScreen extends StackPane {
  final Date START_DATE = Controller.parseDate("2019-03-18");
  final Date END_DATE = Controller.getLatest();
  Map<Date, Float> stockData;
  boolean eventsShown = false;

  public AstrologyScreen(Controller controller) {
    VBox display = new VBox();

    HBox inputs = new HBox();
    TextField ticker = new TextField();
    ticker.setPromptText("Input stock ticker");

    ComboBox<String> event = new ComboBox<>();
    event.setStyle("-fx-background-color: #c280ea");
    event.setPromptText("Select celestial event");
    event.getItems().add("Mercury in retrograde");
    event.getItems().add("Solar eclipse");
    event.getItems().add("Lunar eclipse");

    Button submit = new Button("Submit");
    submit.setStyle("-fx-background-color: #c280ea");

    inputs.getChildren().addAll(ticker,event,submit);
    inputs.setSpacing(20);
    inputs.setPadding(new Insets(20,20,20,20));
    inputs.setBackground(new Background(new BackgroundFill(Color.rgb(144, 65, 193), CornerRadii.EMPTY, Insets.EMPTY)));

    display.getChildren().add(inputs);
    getChildren().add(display);

    HBox chartAndData = new HBox();

    VBox data = new VBox();
    data.setMaxWidth(200);
    data.setAlignment(Pos.TOP_CENTER);
    data.setSpacing(10);
    data.setBackground(new Background(new BackgroundFill(Color.rgb(194, 128, 234), CornerRadii.EMPTY, Insets.EMPTY)));
    HBox.setHgrow(data, Priority.ALWAYS);

    Label information = new Label("Information");
    ComboBox<String> eventDate = new ComboBox<>();
    eventDate.setPromptText("Select Event Date");
    Label holdPeriodLabel = new Label("Set holding period: ");
    TextField holdPeriod = new TextField();
    holdPeriod.setMaxWidth(50);
    holdPeriod.setText("0");
    Label priceAtStart = new Label("Price at start of range: ");
    Label priceAtEnd = new Label("Price at end of range: ");
    Label increaseAtEnd = new Label("$.. ..%");
    Label priceAtEvent = new Label("Price at event: ");
    Label priceEODEvent = new Label("Price at EOD of event: ");
    Label increaseEODEvent = new Label("$.. ..%");
    Label priceAfterHolding = new Label("Price after holding: ");
    Label increaseAfterHolding = new Label("$.. ..%");
    Button showEventsButton = new Button("Show/Hide Events");

    data.getChildren().addAll(information, eventDate, holdPeriodLabel, holdPeriod, priceAtStart, priceAtEnd, increaseAtEnd, priceAtEvent, priceEODEvent, increaseEODEvent, priceAfterHolding, increaseAfterHolding, showEventsButton);

    display.getChildren().add(chartAndData);
    StackPane chartOverlay = new StackPane();


    // TODO: Implement screen switching listener and cancel event if triggered
    //https://stackoverflow.com/questions/44398611/running-a-process-in-a-separate-thread-so-rest-of-java-fx-application-is-usable
    submit.setOnMouseClicked(ev -> { 
      Label loading = new Label("Loading...");
      getChildren().add(loading);
      controller.createStockGraph(ticker.getText()); 
      Task<Void> executeAppTask = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          displayGraph();
          return null;
        }
      };

    executeAppTask.setOnSucceeded(e -> { //TODO: THIS PROBABLY ALL NEEDS TO BE IN A TRY CATCH BLOCK
      getChildren().remove(loading);
      chartOverlay.getChildren().clear();
      chartAndData.getChildren().clear();

      ImageView chart = new ImageView(new Image(this.getClass().getResource("/images/"+ ticker.getText() + ".png").toExternalForm())); 
      chartOverlay.getChildren().addAll(chart);


      this.stockData = controller.getStockDataDates(ticker.getText());

      chartAndData.getChildren().add(chartOverlay); 
      chartAndData.getChildren().add(data);

      priceAtStart.setText("Price at start of range: " + stockData.get(START_DATE));
      priceAtEnd.setText("Price at end of range: " + stockData.get(END_DATE));

      Float profit = (stockData.get(END_DATE) - stockData.get(START_DATE));

      DecimalFormat twoDP = new DecimalFormat("#.##");
      increaseAtEnd.setText("$" + profit + " " + Double.valueOf(twoDP.format(profit/stockData.get(START_DATE)*100)) + "%");
      if (profit > 0) {
        increaseAtEnd.setTextFill(Color.GREEN);
      } else {
        increaseAtEnd.setTextFill(Color.RED);
      }

      if (eventDate.getValue() != null) {
        System.out.println("fired top");
        eventDate.fireEvent(new ActionEvent());
      } else {
        eventDate.getSelectionModel().select(1); //0 causes errors most of the time
        System.out.println("Fired bottom");
        eventDate.fireEvent(new ActionEvent());
      }
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

    submit.setOnMouseEntered(ev -> {
      submit.setStyle("-fx-background-color: #b84df8");
    });

    submit.setOnMouseExited(ev -> {
      submit.setStyle("-fx-background-color: #c280ea");
    });


    event.setOnAction(ev -> { //TODO - Change this to react to appropriate events
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

        Date dayBeforeDate = Controller.getClosestMarketDayPrior(dayBefore.get(Calendar.YEAR) + "-" + dayBefore.get(Calendar.MONTH) + "-" + dayBefore.get(Calendar.DAY_OF_MONTH), stockData);
        Date dateOfEvent = Controller.getClosestMarketDayPrior(eventDate.getValue(), stockData);


        priceAtEvent.setText("Price at event: " + Double.valueOf(twoDP.format(stockData.get(dayBeforeDate))));
        priceEODEvent.setText("Price at EOD of event: " + Double.valueOf(twoDP.format(stockData.get(dateOfEvent))));

        Float profitEOD = stockData.get(dateOfEvent) - stockData.get(dayBeforeDate);
        increaseEODEvent.setText("$" + Double.valueOf(twoDP.format(profitEOD))+ " " + Double.valueOf(twoDP.format((profitEOD)/stockData.get(dayBeforeDate)*100)) + "%");
        if (profitEOD > 0) {
          increaseEODEvent.setTextFill(Color.GREEN);
        } else {
          increaseEODEvent.setTextFill(Color.RED);
        }

        Calendar dateAfterHolding = Calendar.getInstance();
        dateAfterHolding.set(Calendar.YEAR, Integer.parseInt(yearMonthDay[0]));
        dateAfterHolding.set(Calendar.MONTH, Integer.parseInt(yearMonthDay[1]));
        dateAfterHolding.set(Calendar.DAY_OF_MONTH, Integer.parseInt(yearMonthDay[2]));
        dateAfterHolding.add(Calendar.DAY_OF_MONTH, Integer.parseInt(holdPeriod.getText()));
        Date dateHolding = Controller.getClosestMarketDayPrior((dateAfterHolding.get(Calendar.YEAR) + "-" + dateAfterHolding.get(Calendar.MONTH) + "-" + dateAfterHolding.get(Calendar.DAY_OF_MONTH)), stockData);

        
        priceAfterHolding.setText("Price after holding: " + Double.valueOf(twoDP.format((stockData.get(dateHolding)))));

        Float profitHolding = (stockData.get(dateHolding) - stockData.get(dayBeforeDate));
        increaseAfterHolding.setText("$" + Double.valueOf(twoDP.format(profitHolding)) + " " + Double.valueOf(twoDP.format(profitHolding/stockData.get(dayBeforeDate)*100)) + "%");
        if (profitHolding > 0) {
          increaseAfterHolding.setTextFill(Color.GREEN);
        } else {
          increaseAfterHolding.setTextFill(Color.RED);
        }
      } else {
        System.out.println("Error - Null event date selected");
      }
    });

    showEventsButton.setOnMouseClicked(ev -> {
      if (!eventsShown) {
        for (String eventString : eventDate.getItems()) {
          chartOverlay.getChildren().add(eventLine(Controller.parseDate(eventString)));
        }
        eventsShown = true;
      } else {
        chartOverlay.getChildren().clear();
        ImageView chart = new ImageView(new Image(this.getClass().getResource("/images/"+ ticker.getText() + ".png").toExternalForm())); 
        chartOverlay.getChildren().addAll(chart);
        eventsShown = false;
      }
    });

    holdPeriod.setOnKeyPressed(ev -> {
      if (ev.getCode() == KeyCode.ENTER) {
        eventDate.fireEvent(new ActionEvent());
        System.out.println("The \"Enter\" key was pressed");
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

  private int daysBetween(Date from, Date to) {
    long difference = to.getTime() - from.getTime();
    return (int)TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
  }

  private Line eventLine(Date date) {
    int totalDays = daysBetween(START_DATE, END_DATE);
    int daysFromStart = daysBetween(START_DATE, date);

    float fraction = (float)daysFromStart/(float)totalDays;

    Line line = new Line(0, 0, 0, 350);
    line.setStrokeWidth(1);
    line.setTranslateX(-220+fraction*455);

    return line;
  }
}
