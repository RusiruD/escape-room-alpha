package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import nz.ac.auckland.se206.controllers.SceneManagerAi.AppUi;

public class CalendarController {

  @FXML private AnchorPane rootPane;

  @FXML private Button goBackButton;

  @FXML private DatePicker datePicker;

  @FXML
  private void initialize() {
    // Initialization code, if needed
  }

  @FXML
  private void onCalendarGoBack(ActionEvent event) {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.ESCAPE_ROOM));
  }

  // You can add more event handler methods here as needed
}
