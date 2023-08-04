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

  @FXML private Rectangle ball;
  @FXML protected Button bench;

  @FXML private Rectangle window;
  @FXML private Rectangle vase;
  @FXML private Button key;

  @FXML private Button clock;
  @FXML private Rectangle weight1;
  @FXML private ProgressIndicator time;
  @FXML private Button btnReturnToFirstRiddle;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    System.out.println("initialixe");
    btnReturnToFirstRiddle.setDisable(true);
    btnReturnToFirstRiddle.setVisible(false);
    // Initialization code goes here

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
    long startTime = System.currentTimeMillis();
    time.setProgress(0);
    textToSpeech = new TextToSpeech();

    Task<Void> TwoMinutes =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech.speak("You have 2 minutes remaining", "to escape the gym");
            return null;
          }
        };
    Thread reminder1 = new Thread(TwoMinutes, "Search Thread");
    reminder1.start();
    Task<Void> OneMinute =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech.speak("You have 1 minute remaining", "to escape the gym");
            return null;
          }
        };

    Task<Void> ThirtySeconds =
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

            if (i == 120) {
              Platform.exit();
              myTimer.cancel();
              long time = System.currentTimeMillis() - startTime;

              System.out.println(" took " + time + "ms");
              System.out.println("endrld");
            }
            if (i == 60) {
              Thread reminder2 = new Thread(OneMinute, "Search Thread");
              reminder2.start();
            }
            if (i == 90) {
              Thread reminder3 = new Thread(ThirtySeconds, "Search Thread");
              reminder3.start();
            }
            System.out.println(i);

            time.setProgress(i / 120);
            i++;
          }
        },
        0,
        1000);
    /*myTimer.schedule(
    new TimerTask() {

      @Override
      public void run() {
        if (myTimer.equals(4000)) {
          System.out.println("4");
        }
        System.out.println("endrld");
        Platform.exit();
      }
    },
    120000);*/

    btnGoToSafe.setDisable(false);
    clock.setDisable(false);
    clock.setVisible(true);
    btnStart.setDisable(true);
    btnStart.setVisible(false);
    ball.setDisable(false);
    bench.setDisable(false);
    // bench.setVisible(true);
    btnReturnToFirstRiddle.setDisable(false);
    btnReturnToFirstRiddle.setVisible(true);
    clock.setDisable(false);

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FIRST_RIDDLE));
  }

  public void clickDoor(MouseEvent event) throws IOException {
    if (GameState.isRiddleResolved) {
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
    System.out.println("weight1 clicked");
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  int x = 0;

  @FXML
  public void clockClicked(ActionEvent event) {
    if (GameState.isRiddleResolved) {
      x++;
      System.out.println("clock clicked");
      if (x == 3) {
        x = 0;
        // showDialog("Info", "You Lost!", "You broke the window!");
        // System.exit(0);

        Button button = (Button) event.getSource();
        Scene sceneButtonIsIn = button.getScene();
        sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.PASSCODEHINT));
      }
    }
  }

  @FXML
  public void clickPoster(MouseEvent event) {
    System.out.println("poster clicked");
  }

  @FXML
  public void benchClicked(ActionEvent event) throws IOException {
    System.out.println("bench clicked");
    if (GameState.isRiddleResolved) {
      Button button = (Button) event.getSource();
      Scene sceneButtonIsIn = button.getScene();
      sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FOLDER));
    }
    // System.out.println("bench clicked");
    // x++;
    //      System.out.println("dsss");
    // }
  }

  @FXML
  public void GoToSafe(ActionEvent event) throws IOException {
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
}
