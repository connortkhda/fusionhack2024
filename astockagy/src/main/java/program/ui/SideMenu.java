package program.ui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideMenu extends VBox {
  
  public SideMenu() {
    setSpacing(5);
    setBackground(new Background(new BackgroundFill(Color.rgb(144, 65, 193), CornerRadii.EMPTY, Insets.EMPTY)));
    ImageView logo = new ImageView(new Image(this.getClass().getResource("/images/logo.png").toExternalForm()));
    logo.setFitHeight(100);
    logo.setFitWidth(100);
    getChildren().add(logo);
    //setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

  }
}
