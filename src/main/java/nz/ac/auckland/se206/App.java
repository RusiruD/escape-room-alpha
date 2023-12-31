package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nz.ac.auckland.se206.controllers.SceneManagerAi;
import nz.ac.auckland.se206.controllers.SceneManagerAi.AppUi;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;

  public static void main(final String[] args) {
    launch();
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFxml(fxml));
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {
    SceneManagerAi.addUi(AppUi.ESCAPE_ROOM, loadFxml("room"));
    SceneManagerAi.addUi(AppUi.FIRST_RIDDLE, loadFxml("firstRiddle"));
    SceneManagerAi.addUi(AppUi.CALENDAR, loadFxml("calendar"));
    SceneManagerAi.addUi(AppUi.SAFE, loadFxml("safe"));
    SceneManagerAi.addUi(AppUi.FOLDER, loadFxml("secondRiddle"));
    SceneManagerAi.addUi(AppUi.PASSCODEHINT, loadFxml("thirdRiddle"));
    SceneManagerAi.addUi(AppUi.GHOST, loadFxml("ghostAi"));

    scene = new Scene(SceneManagerAi.getUiRoot(AppUi.ESCAPE_ROOM), 750, 500);

    stage.setScene(scene);

    stage.setOnCloseRequest(
        event -> {
          // Intercept the close request and perform cleanup
          System.out.println("Closing application");
          GameState.isGameActive = false;
          Platform.exit(); // Close the JavaFX application
        });
    stage.show();
  }
}
