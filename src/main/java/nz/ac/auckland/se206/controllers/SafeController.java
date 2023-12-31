package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.controllers.SceneManagerAi.AppUi;

/** Controller class for the room view. */
public class SafeController {
  @FXML private Button btnGoBack;
  @FXML private Button btnUnlockSafe;

  @FXML private PasswordField passcode;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
  }

  @FXML
  private void onBackButtonClicked(ActionEvent event) throws IOException {

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.ESCAPE_ROOM));
  }

  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  @FXML
  private void onPasscodeEntered() throws IOException {

    String s = passcode.getText();
    if (s != null && s.equals("76942")) {
      System.out.println("passed");
      GameState.isSafeOpen = true;
      btnUnlockSafe.setDisable(true);
      passcode.setDisable(true);

      showDialog("Info", "Unlocked!", " The safe is now unlocked");
    } else {

      showDialog("Info", "Locked!", "Incorrect Passcode. Try Again!");
    }
  }
}
