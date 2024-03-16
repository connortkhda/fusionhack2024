package program.scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import program.ui.SideMenu;

public class MainScene extends StackPane {

  public MainScene() {
    HBox mainDisplay = new HBox();

    SideMenu sideMenu = new SideMenu();

    mainDisplay.getChildren().add(sideMenu);

    getChildren().add(mainDisplay);
  }
  
}
