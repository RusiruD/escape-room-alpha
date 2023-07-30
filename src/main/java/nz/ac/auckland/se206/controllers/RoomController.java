package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.controllers.SceneManagerAi.AppUi;

/** Controller class for the room view. */
public class RoomController {
  @FXML private Button btnStart;
  @FXML private Button btnGoToSafe;

  @FXML private Rectangle door;
  @FXML protected Button bench;

  @FXML private Rectangle window;
  @FXML private Rectangle vase;
  @FXML private Button key;

  @FXML private Button coatHanger;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    // Initialization code goes here
    System.out.println("*** Initializing CounterController ***" + this);
    System.out.println("*** Initializing CounterController ***" + this);
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
  }

  @FXML
  public void PickUpKey() {
    System.out.println("key picked up");
    key.setDisable(true);
    key.setVisible(false);
    GameState.isKeyFound = true;
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void Start(ActionEvent event) throws IOException {
    bench.setDisable(false);

    btnGoToSafe.setDisable(false);

    btnStart.setDisable(true);
    btnStart.setVisible(false);
    door.setDisable(false);
    bench.setDisable(false);
    // bench.setVisible(true);

    coatHanger.setDisable(false);

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FIRST_RIDDLE));
  }

  public void clickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");

    // if (!GameState.isRiddleResolved) {
    // showDialog("Info", "Riddle", "You need to resolve the riddle!");
    // App.setRoot("chat");
    // return;
    // }
    // if (!GameState.isRiddleResolved) {
    // showDialog("Info", "Riddle", "You need to resolve the riddle!");
    // App.setRoot("chat");
    // return;
    // }

    if (!GameState.isKeyFound) {
      showDialog("Info", "Find the key!", " the key is.");
      showDialog("Info", "Find the key!", " the key is.");
    } else {

      showDialog("Info", "You Won!", "Good Job!");

      bench.setDisable(true);
      bench.setVisible(false);

      bench.setDisable(true);
      bench.setVisible(false);
    }
  }

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickVase(MouseEvent event) {
    System.out.println("vase clicked");
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  int x = 0;

  @FXML
  public void coatHangerClicked(ActionEvent event) {

    x++;
    System.out.println("coat hanger clicked");
    if (x == 3) {
      // showDialog("Info", "You Lost!", "You broke the window!");
      // System.exit(0);

      Button button = (Button) event.getSource();
      Scene sceneButtonIsIn = button.getScene();
      sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.PASSCODEHINT));
    }
  }

  @FXML
  public void clickWindow(MouseEvent event) {
    System.out.println("window clicked");
    x++;
    if (x == 3) {
      // showDialog("Info", "You Lost!", "You broke the window!");
      // System.exit(0);

    }
  }

  @FXML
  public void benchClicked(ActionEvent event) throws IOException {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FOLDER));
    // System.out.println("bench clicked");
    // x++;
    //      System.out.println("dsss");
    // }
  }

  @FXML
  public void GoToSafe(ActionEvent event) throws IOException {
    if (!GameState.isSafeOpen) {
      Button button = (Button) event.getSource();
      Scene sceneButtonIsIn = button.getScene();
      sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.SAFE));
    } else {
      btnGoToSafe.setDisable(true);
      btnGoToSafe.setVisible(false);
      key.setVisible(true);
      key.setDisable(false);
    }
  }
}
