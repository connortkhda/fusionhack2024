package program.scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import program.controller.Controller;
import program.ui.SideMenu;

public class MainScene extends StackPane {

  public MainScene() {
    Controller controller = new Controller();
    
    HBox mainDisplay = new HBox();

    SideMenu sideMenu = new SideMenu(controller);

    mainDisplay.getChildren().add(sideMenu);

    getChildren().add(mainDisplay);
  }
  
}
