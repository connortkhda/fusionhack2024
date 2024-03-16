package program.ui;

import javafx.scene.layout.StackPane;
import program.controller.Controller;

public class AstrologyScreen extends StackPane {

  public AstrologyScreen(Controller controller) {
    controller.createStockGraph();
  }

}
