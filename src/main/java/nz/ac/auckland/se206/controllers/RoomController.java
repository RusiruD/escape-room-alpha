package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.controllers.SceneManagerAi.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller class for the room view. */
public class RoomController {
  private TextToSpeech textToSpeech;
  @FXML private Button btnStart;
  @FXML private Button btnGoToSafe;

  @FXML private Rectangle bottle;
  @FXML protected Button btnYogaBall;

  @FXML private Button key;
  @FXML private Rectangle window;
  @FXML private Button boxingBag;
  @FXML private Rectangle weight1;
  @FXML private Rectangle towels;
  @FXML private Rectangle weight2;
  @FXML private ProgressIndicator time;
  @FXML private Button btnReturnToFirstRiddle;

  /** Initializes the room view, it is called when the room loads. */

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  @FXML
  public void ReturnToFirstRiddle(ActionEvent event) throws IOException {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FIRST_RIDDLE));
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
  public void start(ActionEvent event) throws IOException {
    long startTime = System.currentTimeMillis();
    time.setProgress(0);
    textToSpeech = new TextToSpeech();

    Task<Void> twoMinutes =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech.speak("You have 2 minutes remaining", "to escape the gym");
            return null;
          }
        };
    Thread reminder1 = new Thread(twoMinutes, "Search Thread");
    reminder1.start();
    Task<Void> oneMinute =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech.speak("You have 1 minute remaining", "to escape the gym");
            return null;
          }
        };

    Task<Void> thirtySeconds =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech.speak("You have 30 seconds remaining to escape", "Tick Tock");
            return null;
          }
        };

    Timer myTimer = new Timer();
    myTimer.scheduleAtFixedRate(
        new TimerTask() {
          double i = 0;

          @Override
          public void run() {
            if (GameState.isGameActive == true) {
              if (i == 120) {
                Platform.exit();
                myTimer.cancel();

                long time = System.currentTimeMillis() - startTime;

                System.out.println(" took " + time + "ms");
                System.out.println("endrld");
              }
              if (i == 60) {
                Thread reminder2 = new Thread(oneMinute, "Search Thread");
                reminder2.start();
              }
              if (i == 90) {
                Thread reminder3 = new Thread(thirtySeconds, "Search Thread");
                reminder3.start();
              }
              System.out.println(i);

              time.setProgress(i / 120);
              i++;
            }
          }
        },
        0,
        1000);

    btnGoToSafe.setDisable(false);
    boxingBag.setDisable(false);
    boxingBag.setVisible(true);
    btnStart.setDisable(true);
    btnStart.setVisible(false);
    bottle.setDisable(false);
    window.setDisable(false);
    btnYogaBall.setDisable(false);

    btnReturnToFirstRiddle.setDisable(false);
    btnReturnToFirstRiddle.setVisible(true);

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FIRST_RIDDLE));
  }

  public void clickWindow(MouseEvent event) throws IOException {
    if (GameState.isRiddleResolved) {
      System.out.println("door clicked");

      if (!GameState.isKeyFound) {

        showDialog("Info", "Find the key!", "");

      } else {

        showDialog("Info", "You Won!", "Good Job!");

        btnYogaBall.setDisable(true);
        btnYogaBall.setVisible(false);
      }
    }
  }

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickWeight(MouseEvent event) {
    System.out.println("weight clicked");
  }

  @FXML
  public void clickWeight1(MouseEvent event) {
    System.out.println("weight clicked");
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  int x = 0;

  @FXML
  public void boxingBagClicked(ActionEvent event) {
    if (GameState.isRiddleResolved) {
      x++;
      System.out.println("boxing bag clicked");
      if (x == 3) {
        x = 0;

        Button button = (Button) event.getSource();
        Scene sceneButtonIsIn = button.getScene();
        sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.PASSCODEHINT));
      }
    }
  }

  @FXML
  public void clickTowels(MouseEvent event) {
    System.out.println("Towels clicked");
  }

  @FXML
  public void clickBottle(MouseEvent event) {
    System.out.println("Bottle  clicked");
  }

  @FXML
  public void ballClicked(ActionEvent event) throws IOException {
    System.out.println("yoga ball clicked");
    if (GameState.isRiddleResolved) {
      Button button = (Button) event.getSource();
      Scene sceneButtonIsIn = button.getScene();
      sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FOLDER));
    }
  }

  @FXML
  public void goToSafe(ActionEvent event) throws IOException {
    System.out.println("safe clicked");
    if (GameState.isRiddleResolved) {
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

  public void initialize() {

    btnReturnToFirstRiddle.setDisable(true);
    btnReturnToFirstRiddle.setVisible(false);
    // Initialization code goes here

  }

  @FXML
  public void pickUpKey() {
    System.out.println("key picked up");
    key.setDisable(true);
    key.setVisible(false);
    GameState.isKeyFound = true;
  }
}
