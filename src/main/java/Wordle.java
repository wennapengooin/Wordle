package main.java;

import java.util.*;
//CREATE README
// CLEAN UP CODE
//BREAK UP CERTAIN METHODS
//JAVADOCS

public class Wordle {
    public static final String PLACE_HOLDER = "*"; // Placeholder for unguessed letters

    private static final WordLoader wordLoader = new WordLoader("C:/Users/HP/IdeaProjects/Wordle/src/main/resources/5_letter_words.txt");
    private static final List<String> wordList = wordLoader.getWordList();

    int letterCount;
    int attempts;
    int currentAttempt;
    boolean isWordFound;
    List<String> words;

    String word;
    String currentWord;
    String[][] gameBoard;
    String[][] keyboard;
    int currentRow;

    Wordle() {
        this.letterCount = 5;
        this.attempts = 6;
        this.currentAttempt = 0;
        this.isWordFound = false;
        this.words = wordList;
        this.word = getRandomWord();
        this.currentWord = "";
        this.gameBoard = initializeGameBoard();
        this.keyboard = initializeKeyboard();
    }

    private String[][] initializeKeyboard() {
        String[][] keyboard;
        keyboard = new String[3][];
        keyboard[0] = new String[]{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
        keyboard[1] = new String[]{"a", "s", "d", "f", "g", "h", "j", "k", "l"};
        keyboard[2] = new String[]{"z", "x", "c", "v", "b", "n", "m"};
        return keyboard;
    }

    private String[][] initializeGameBoard() {
        gameBoard = new String[attempts][letterCount];
        for (int i = 0; i < attempts; i++) {
            for (int j = 0; j < letterCount; j++) {
                gameBoard[i][j] = PLACE_HOLDER;
            }
        }
        return gameBoard;
    }

    void updateGameState() {
        for (int i = 0; i < word.length(); i++) {
            String c = String.valueOf(currentWord.charAt(i));

            String letter;
            if (word.contains(c)) {
                letter = c;

                if (!c.equals(Character.toString(word.charAt(i)))) {
                    letter = letter.toUpperCase();
                }
            } else {
                letter = "*";
            }
            this.updateGameBoard(i, letter);
            this.updateKeyboard(letter, c);
        }
    }

    void updateGameBoard(int index, String letter) {
        for (int i = 0; i < word.length(); i++) {
            this.gameBoard[this.currentRow][index] = letter;
        }
    }

    void updateKeyboard(String letter, String origLetter) {
        if (!word.contains(origLetter)) {
            letter = PLACE_HOLDER;
        }

        for (String[] row : this.keyboard) {
            if (Arrays.asList(row).contains(origLetter)) {
                int index = Arrays.asList(row).indexOf(origLetter);
                row[index] = letter;
            }
        }
    }


    String getRandomWord() {
        String randomWord;
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        randomWord = wordList.get(randomIndex);
        return randomWord;
    }

    private void printGameBoard() {
        System.out.println("\nGame Board:");
        for (String[] row : gameBoard) {
            for (String c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private void printKeyboard() {
        System.out.println("\nKeyboard:");
        for (String[] row : keyboard) {
            for (String key : row) {
                System.out.print(key);
                System.out.print(" "); // Add white space between keyboard letters for readability
            }
            System.out.println(); // Print a newline at the end
        }
    }

    void endGame() {
        if (isWordFound) {
            System.out.println("Congratulations! You guessed the word: " + word + " with " + (attempts - currentAttempt) + " attempts left!");
        } else {
            System.out.println("Sorry, you ran out of attempts. The word was: " + this.word);
        }
    }

    boolean isValidWord() {
        if (!Objects.equals(currentWord.length(), letterCount)) {
            return false;
        }
        return this.words.contains(currentWord);
    }

    boolean isWordFound() {
        return currentWord.equals(word);
    }

    static boolean isValidYesOrNo(String input) {
        return input.equals("yes") || input.equals("no");
    }

    private void playGame() {

        while (attempts > 0 && !isWordFound) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nYou have " + (attempts - currentAttempt) + " attempts left. Enter your guess: ");
            currentWord = scanner.next().trim().toLowerCase();

            if (isValidWord()) {
                updateGameState();
                printGameBoard();
                printKeyboard();

                attempts -= 1;
                currentRow += 1;
            } else {
                System.out.println("Invalid input. Please enter a " + letterCount + "-letter word.");
            }
            if (isWordFound()) {
                isWordFound = true;
            }
        }
        if (isWordFound() || attempts == 0) {
            endGame();
        }
    }

    String getWord() {
        return word;
    }
    void setCurrentWord(String newWord) {
        this.currentWord = newWord;
    }
    String getCurrentWord() {
        return currentWord;
    }
    int getCurrentRow() {
        return currentRow;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        System.out.println("Welcome to Wordle!");

        while (playAgain) {
            Wordle wordle = new Wordle();
            wordle.playGame();
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainInput = scanner.next().toLowerCase();
            while (!isValidYesOrNo(playAgainInput)) {

                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                playAgainInput = scanner.next().toLowerCase();
            }
            if (!playAgainInput.equals("yes")) {
                playAgain = false;
            }
        }
        System.out.println("Thank you for playing Wordle!");
    }

    public String[][] getKeyboard() {
        return keyboard;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }

    public void setCurrentRow(int i) {
        currentRow = i;
    }


    public void setWordFound(boolean b) {
        isWordFound = b;
    }

    public void setCurrentAttempt(int i) {
        currentAttempt = i;
    }

    public void setAttempts(int i) {
        attempts = i;
    }

    public List<String> getWordList() {
        return wordList;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
