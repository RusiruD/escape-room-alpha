package nz.ac.auckland.se206.controllers;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManagerAi {
  public enum AppUi {
    ESCAPE_ROOM,
    FIRST_RIDDLE,
    SECOND_RIDDLE,
    SAFE,
    FOLDER,
    PASSCODEHINT,
    CALENDAR,
    GHOST,
    LOSS
  }

  private static HashMap<AppUi, Parent> sceneMap = new HashMap<AppUi, Parent>();

  public static void addUi(AppUi appUi, Parent uiRoot) {
    sceneMap.put(appUi, uiRoot);
  }

  public static Parent getUiRoot(AppUi ui) {
    return sceneMap.get(ui);
  }
}
