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
import javafx.scene.control.Separator;
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
  @FXML private Button btnCalendar;
  @FXML private Rectangle bottle;
  @FXML protected Button btnYogaBall;

  @FXML private Button key;
  @FXML private Rectangle window;
  @FXML private Button boxingBag;
  @FXML private Rectangle weight1;
  @FXML private Rectangle towels;
  @FXML private Rectangle weight2;
  @FXML private Rectangle door;
  @FXML private ProgressIndicator time;
  @FXML private Button btnReturnToFirstRiddle;
  @FXML private Separator seperatorOne;
  @FXML private Separator seperatorTwo;

  @FXML private Separator seperatorThree;

  @FXML private Separator seperatorFour;

  @FXML private Separator seperatorFive;

  /** Initializes the room view, it is called when the room loads. */

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */

  /**
   * Handles the click event on the vase.
   *
   * @param event the mouse event
   */

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   */
  private int boxingBagCounter = 0;

  private int windowCounter = 0;

  @FXML
  private void onTowelsClicked(MouseEvent event) {
    System.out.println("Towels clicked");
  }

  @FXML
  private void clickBottle(MouseEvent event) {
    System.out.println("Bottle  clicked");
  }

  public void initialize() {

    btnReturnToFirstRiddle.setDisable(true);
    btnReturnToFirstRiddle.setVisible(false);
    // Initialization code goes here

  }

  @FXML
  private void onDoorClicked() {
    door.hoverProperty()
        .addListener(
            l -> {
              System.out.println("door is being hovered");
            });
    if (GameState.isKeyFound) {
      showDialog("Info", "You have escaped", "You have escaped the gym, congratulations!");
      GameState.isGameWon = true;
    } else {
      showDialog("Info", "The door is locked", "You have to find the key to escape");
    }
  }

  @FXML
  private void onWindowClicked() throws IOException {
    System.out.println("window clicked");
    windowCounter++;
    if (windowCounter == 3) {
      seperatorThree.setVisible(true);
      seperatorTwo.setVisible(true);
      seperatorOne.setVisible(true);
    } else if (windowCounter > 3) {
      seperatorFive.setVisible(true);
      seperatorFour.setVisible(true);
    }
  }

  @FXML
  private void onKeyClicked() {
    System.out.println("key picked up");
    key.setDisable(true);
    key.setVisible(false);
    GameState.isKeyFound = true;
  }

  @FXML
  private void onBallClicked(ActionEvent event) throws IOException {
    System.out.println("yoga ball clicked");
    if (GameState.isRiddleResolved) {
      Button button = (Button) event.getSource();
      Scene sceneButtonIsIn = button.getScene();
      sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FOLDER));
      btnYogaBall
          .hoverProperty()
          .addListener(
              l -> {
                System.out.println("Button is being hovered");
              });
    }
  }

  @FXML
  private void clickWeight(MouseEvent event) {
    System.out.println("weight clicked");
  }

  @FXML
  public void clickWeight1(MouseEvent event) {
    System.out.println("weight clicked");
  }

  @FXML
  private void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  @FXML
  private void onReturnToFirstRiddle(ActionEvent event) throws IOException {
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FIRST_RIDDLE));
  }

  @FXML
  private void onCalendarClicked(ActionEvent event) throws IOException {
    System.out.println("Calendar clicked");
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.CALENDAR));
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

  public void clickWindow(MouseEvent event) throws IOException {}

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  private void onStartGame(ActionEvent event) throws IOException {
    /* */
    // the time the instant game is started is measured
    long startTime = System.currentTimeMillis();
    // the progress indicator is set to 0
    time.setProgress(0);

    time.setVisible(true);
    // Platform.runLater(
    // () -> {
    Task<Void> ds =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech = new TextToSpeech();
            return null;
          }
        };
    Thread ds1 = new Thread(ds, "Search Thread");
    ds1.start();

    // text to speech warnings for 2 minutes,1minute and 30 seconds are created in threads
    // });
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
    Task<Void> youLost =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {

            textToSpeech.speak("You have lost");
            return null;
          }
        };

    // a timer is created that runs a task every second in a background thread

    Timer myTimer = new Timer();

    myTimer.scheduleAtFixedRate(
        new TimerTask() {
          private double seconds = 0;

          @Override
          public void run() {
            // if the game is active and the timer reaches 120 seconds the game ends
            if (GameState.isGameActive == true) {
              timerMethod(seconds, startTime, myTimer, oneMinute, thirtySeconds, youLost);
              seconds++;
            }
          }
        },
        0,
        1000);
    enableAllButtons();
    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.FIRST_RIDDLE));
    // the scene is changed to the first riddle when the start button is pressed

  }

  @FXML
  private void onBoxingBagClicked(ActionEvent event) {
    if (GameState.isRiddleResolved) {
      boxingBagCounter++;
      System.out.println("boxing bag clicked");
      if (boxingBagCounter == 3) {
        boxingBagCounter = 0;

        Button button = (Button) event.getSource();
        Scene sceneButtonIsIn = button.getScene();
        sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.PASSCODEHINT));
      }
    }
  }

  @FXML
  private void onSafeClicked(ActionEvent event) throws IOException {
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

  public void timerMethod(
      double seconds,
      long startTime,
      Timer myTimer,
      Task<Void> oneMinute,
      Task<Void> thirtySeconds,
      Task<Void> youLost) {
    if (seconds == 120) {
      if (GameState.isGameWon == false) {
        Thread lost = new Thread(youLost, "lost Thread");
        lost.start();
      }
      Platform.exit();
      long time = System.currentTimeMillis() - startTime;
      myTimer.cancel();

      System.out.println(" took " + time + "ms");
    }
    // if the game is active and the timer reaches 60 seconds a 1 minute text to speech
    // warning is spoken
    if (seconds == 60) {
      Thread reminder2 = new Thread(oneMinute, "minute Thread");

      reminder2.start();
    }
    // if the game is active and the timer reaches 90 seconds a 30 seconds text to speech
    // warning is spoken
    if (seconds == 90) {
      Thread reminder3 = new Thread(thirtySeconds, "30 seconds Thread");
      reminder3.start();
    }

    time.setProgress(seconds / 120);
  }

  public void enableAllButtons() {
    btnGoToSafe.setDisable(false);
    boxingBag.setDisable(false);
    // boxingBag.setVisible(true);
    btnStart.setDisable(true);
    btnStart.setVisible(false);
    bottle.setDisable(false);
    window.setDisable(false);
    btnCalendar.setDisable(false);
    btnYogaBall.setDisable(false);

    btnReturnToFirstRiddle.setDisable(false);
    btnReturnToFirstRiddle.setVisible(true);
  }
}
