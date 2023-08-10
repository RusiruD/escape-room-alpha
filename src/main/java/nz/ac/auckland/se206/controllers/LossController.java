package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for the chat view. */
public class LossController {
  @FXML private Button btnExit;

  @FXML
  private void exit(ActionEvent event) throws ApiProxyException, IOException {
    Platform.exit();
  }
}
