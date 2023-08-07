package main.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {
    private final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;

    @BeforeEach
    public void setUp() {
        // Set up the test input stream with a specific answer for user input
        // Replace the default System.in with the test input stream
        System.setIn(systemIn);
    }

    @Test
    public void testInitializeGame() {
        WordleGame.initializeGame();
        String secretWord = WordleGame.getSecretWord();
        assertNotNull(secretWord);
        assertEquals(WordleGame.WORD_LENGTH, secretWord.length());
    }

    @Test
    public void testIsCorrectGuess() {
        WordleGame.initializeGame();
        String secretWord = WordleGame.getSecretWord();
        assertTrue(WordleGame.isCorrectGuess(secretWord));
        assertFalse(WordleGame.isCorrectGuess("incorrect_guess"));
    }

    @Test
    public void testCorrectGuessInRightSpot() {
        char letter = 'a';
        char guessedLetter = 'a';
        assertTrue(WordleGame.isCorrectGuessInRightSpot(letter, guessedLetter));
        guessedLetter = 'b';
        assertFalse(WordleGame.isCorrectGuessInRightSpot(letter, guessedLetter));
    }

    @Test
    public void testCorrectGuessInWrongSpot() {
        WordleGame.initializeGame();
        String secretWord = WordleGame.getSecretWord();
        char correctLetter = secretWord.charAt(0);
        assertTrue(WordleGame.isCorrectGuessInWrongSpot(correctLetter));
        char incorrectLetter = 'z';
        assertFalse(WordleGame.isCorrectGuessInWrongSpot(incorrectLetter));
    }

    @Test
    public void testFeedbackForCorrectGuess() {
        String secretWord = "apple";
        WordleGame.initializeGame();
        WordleGame.setSecretWord(secretWord);

        String guess = secretWord;
        WordleGame.displayFeedback(guess);
        List<String> feedbackHistory = WordleGame.getFeedbackHistory();
        assertEquals(1, feedbackHistory.size());
        assertEquals(secretWord, feedbackHistory.get(0));

        // Guessed correctly in the right spot
        String expectedFeedback = secretWord;
        assertEquals(expectedFeedback, feedbackHistory.get(0));
    }

    @Test
    public void testFeedbackForIncorrectGuess() {
        String secretWord = "apple";
        WordleGame.initializeGame();
        WordleGame.setSecretWord(secretWord);

        String guess = "fruit";
        WordleGame.displayFeedback(guess);
        List<String> feedbackHistory = WordleGame.getFeedbackHistory();
        assertEquals(1, feedbackHistory.size());

        // Guessed incorrectly, feedback should have '*'
        String expectedFeedback = "*****";
        assertEquals(expectedFeedback, feedbackHistory.get(0));
    }

    @Test
    public void testGameWin() {
        String secretWord = "apple";
        WordleGame.initializeGame();
        WordleGame.setSecretWord(secretWord);

        // Guess the word correctly
        String guess = secretWord;
        WordleGame.displayFeedback(guess);
        assertTrue(WordleGame.isCorrectGuess(guess));
    }

    @Test
    public void testGameLose() {
        String secretWord = "apple";
        WordleGame.initializeGame();
        WordleGame.setSecretWord(secretWord);

        // Make MAX_ATTEMPTS incorrect guesses
        for (int i = 0; i < WordleGame.MAX_ATTEMPTS; i++) {
            String incorrectGuess = "orange";
            WordleGame.displayFeedback(incorrectGuess);
            assertFalse(WordleGame.isCorrectGuess(incorrectGuess));
        }
    }
}
