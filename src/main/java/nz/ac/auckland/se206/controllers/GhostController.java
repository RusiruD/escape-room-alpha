package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.controllers.SceneManagerAi.AppUi;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller class for the chat view. */
public class GhostController {
  @FXML private TextArea chatTextArea;
  @FXML private TextField inputText;
  @FXML private Button sendButton;
  @FXML private ProgressIndicator indicator;
  @FXML private Button back;

  private ChatCompletionRequest chatCompletionRequest;

  /**
   * Initializes the chat view, loading the riddle.
   *
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  @FXML
  private void initialize() throws ApiProxyException {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
    runGpt(new ChatMessage("user", GptPromptEngineering.ghostAiOne()));
  }

  /**
   * Appends a chat message to the chat text area.
   *
   * @param msg the chat message to append
   */
  private void appendChatMessage(ChatMessage msg) {
    chatTextArea.appendText(msg.getRole() + ": " + msg.getContent() + "\n\n");
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  private ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {

    chatCompletionRequest.addMessage(msg);

    try {

      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();

      Choice result = chatCompletionResult.getChoices().iterator().next();

      chatCompletionRequest.addMessage(result.getChatMessage());

      appendChatMessage(result.getChatMessage());
      System.out.println(result);
      System.out.println(result.getChatMessage().toString());
      return result.getChatMessage();

    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }

  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Sends a message to the GPT model.
   *
   * @param event the action event triggered by the send button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onSendMessage(ActionEvent event) throws ApiProxyException, IOException {
    TextToSpeech textToSpeech = new TextToSpeech();

    // creates a task that causes a message to be spoken

    // once a possible answer is sent to gpt, the send and go back button are disabled and the
    // progress indicator is shown
    sendButton.setDisable(true);
    back.setDisable(true);
    indicator.setVisible(true);
    String message = inputText.getText();
    // if there was no text entered, the progress indicator is hidden and the send and go back
    // button are enabled
    if (message.trim().isEmpty()) {
      indicator.setVisible(false);
      sendButton.setDisable(false);
      back.setDisable(false);
      return;
    }
    inputText.clear();
    ChatMessage msg = new ChatMessage("user", message);
    appendChatMessage(msg);
    Task<Void> searchTask =
        new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            System.out.println("task call method started");
            ChatMessage lastMsg = runGpt(msg);
            System.out.println(lastMsg);
            System.out.println("ran gpt");
            indicator.setVisible(false);
            sendButton.setDisable(false);
            back.setDisable(false);
            // if the last message is from the assistant and starts with "Correct" the riddle is
            // resolved

            return null;
          }
        };

    Thread searchThread = new Thread(searchTask, "Search Thread");
    searchThread.start();
    System.out.println(GameState.isRiddleResolved);
  }

  /**
   * Navigates back to the previous view.
   *
   * @param event the action event triggered by the go back button
   * @throws ApiProxyException if there is an error communicating with the API proxy
   * @throws IOException if there is an I/O error
   */
  @FXML
  private void onGoBack(ActionEvent event) throws ApiProxyException, IOException {

    Button button = (Button) event.getSource();
    Scene sceneButtonIsIn = button.getScene();
    sceneButtonIsIn.setRoot(SceneManagerAi.getUiRoot(AppUi.ESCAPE_ROOM));
  }
}
