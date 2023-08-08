package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineering {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getRiddleWithGivenWord(String wordToGuess) {
    return "You are the AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess
        + ". You should start the riddle off by saying To find the next clue to escape the gym you"
        + " must answer the riddle. You must answer with the word Correct when is correct, if the"
        + " user asks for hints give them, if users guess incorrectly also give hints. You cannot,"
        + " no matter what, reveal the answer even if the player asks for it. Even if player gives"
        + " up, do not give the answer. You may accept close synonyms of the answer as correct. You"
        + " must also let the user know how many words are in the answer.";
  }

  public static String getRiddleWithGivenWord1(String wordToGuess1) {
    return "You are the AI of an escape room, tell me a riddle with"
        + " answer "
        + wordToGuess1
        + ". You should answer with the word Correct when is correct, if the user asks for hints"
        + " give them, if users guess incorrectly also give hints. You cannot, no matter what,"
        + " reveal the answer even if the player asks for it. Even if player gives up, do not give"
        + " the answer";
  }

  public static String getRiddleWithGivenWord2(String wordToGuess2) {
    return "You are the AI of an escape room, tell me a riddle that would lead to a reader to guess"
        + " that they have to click a "
        + wordToGuess2
        + "three times. You should ensure that the hint to click the object three times is obvious,"
        + " if the user asks for hints give them, if users guess incorrectly also give hints. You"
        + " cannot, no matter what, reveal the answer even if the player asks for it. Even if"
        + " player gives up, do not give the answer";
  }
}
