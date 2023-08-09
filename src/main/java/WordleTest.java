package main.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordleTest {

    private Wordle wordle;

    @BeforeEach
    public void setUp() {
        wordle = new Wordle();
    }
    @AfterEach
    public void tearDown() {
        wordle = null;
    }

    @Test
    public void testInitializeKeyboard() {
        String[][] keyboard = wordle.getKeyboard();
        assertNotNull(keyboard);
        assertEquals("q", keyboard[0][0]);
        assertEquals("a", keyboard[1][0]);
        assertEquals("z", keyboard[2][0]);
    }

    @Test
    public void testInitializeGameBoard() {
        String[][] gameBoard = wordle.getGameBoard();
        assertNotNull(gameBoard);
        assertEquals(6, gameBoard.length);
        assertEquals(5, gameBoard[0].length);
        assertEquals(Wordle.PLACE_HOLDER, gameBoard[0][0]);
    }

    @Test
    public void testUpdateGameBoard() {
        wordle.setCurrentRow(0);
        wordle.updateGameBoard(1, "a");
        assertEquals("a", wordle.getGameBoard()[0][1]);
    }

    @Test
    public void testUpdateKeyboard() {
        wordle.setWord("happy");
        wordle.updateKeyboard("a", "a");
        assertEquals("a", wordle.getKeyboard()[1][0]);

        wordle.setWord("bully");
        wordle.updateKeyboard("*", "a");
        assertEquals(Wordle.PLACE_HOLDER, wordle.getKeyboard()[1][0]);
    }

    @Test
    public void testIsValidWord() {
        wordle.setCurrentWord(wordle.getWord());
        assertTrue(wordle.isValidWord());
        wordle.setCurrentWord("thing");
        assertTrue(wordle.isValidWord());
        wordle.setCurrentWord("hat");
        assertFalse(wordle.isValidWord());
    }

    @Test
    public void testGetRandomWord() {
        String randomWord = wordle.getRandomWord();
        assertNotNull(randomWord);
        assertTrue(wordle.getWordList().contains(randomWord));
    }

    @Test
    public void testEndGameWordFound() {
        wordle.setWordFound(true);
        wordle.setCurrentAttempt(2);
        wordle.setAttempts(6);
        wordle.endGame();
        // You can create more specific assertions based on the expected output.
    }

    @Test
    public void testEndGameWordNotFound() {
        wordle.setWordFound(false);
        wordle.setCurrentAttempt(6);
        wordle.setAttempts(6);
        wordle.endGame();
        // You can create more specific assertions based on the expected output.
    }


    @Test
    public void testCorrectGuessOnFirstAttempt() {
        String correctWord = wordle.getWord();

        wordle.setCurrentWord(correctWord);
        wordle.updateGameState();

        assertTrue(wordle.isWordFound());
    }

    @Test
    public void testOutOfAttempts() {
        String correctWord = wordle.getWord();

        for (int i = 0; i < wordle.letterCount; i++) {
            if (!correctWord.contains(String.valueOf(wordle.word.charAt(i)))) {
                wordle.setCurrentWord(wordle.word.replace(wordle.word.charAt(i), 'x'));
            } else {
                wordle.setCurrentWord(correctWord);
            }
            wordle.updateGameState();
        }

        assertFalse(wordle.isWordFound);
        assertEquals(0, wordle.getCurrentRow());
    }

}

