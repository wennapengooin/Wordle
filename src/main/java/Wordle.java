package main.java;

import java.util.*;

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

    /**
     * Constructor to initialize the Wordle game.
     */
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

    /**
     * Initializes the keyboard layout.
     *
     * @return The 2D array representing the keyboard layout.
     */
    private String[][] initializeKeyboard() {
        String[][] keyboard;
        keyboard = new String[3][];
        keyboard[0] = new String[]{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
        keyboard[1] = new String[]{"a", "s", "d", "f", "g", "h", "j", "k", "l"};
        keyboard[2] = new String[]{"z", "x", "c", "v", "b", "n", "m"};
        return keyboard;
    }

    /**
     * Initializes the game board.
     *
     * @return The 2D array representing the game board.
     */
    private String[][] initializeGameBoard() {
        gameBoard = new String[attempts][letterCount];
        for (int i = 0; i < attempts; i++) {
            for (int j = 0; j < letterCount; j++) {
                gameBoard[i][j] = PLACE_HOLDER;
            }
        }
        return gameBoard;
    }

    /**
     * Updates the game state based on the current user guess.
     */
    // The updateGameState() method exhibits the Strategy pattern. It dynamically changes behavior by selecting
    // different strategies based on the user's guess. It encapsulates varying strategies for updating the game state,
    // allowing easy addition or modification of new strategies.

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

    /**
     * Updates the game board with a letter at a specific index.
     *
     * @param index The index where the letter should be placed.
     * @param letter The letter to be placed on the game board.
     */
    void updateGameBoard(int index, String letter) {
        for (int i = 0; i < word.length(); i++) {
            this.gameBoard[this.currentRow][index] = letter;
        }
    }

    /**
     * Updates the keyboard based on the user's guess.
     *
     * @param letter The letter to be placed on the keyboard.
     * @param origLetter The original letter to be replaced on the keyboard.
     */
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

    /**
     * Retrieves a random word from the word list.
     *
     * @return A random word.
     */
    // This method implements a Factory Method pattern by generating and returning a random word from a predefined
    // word list. It encapsulates the creation process of an object, providing flexibility to change the way random
    // words are generated without affecting the calling code.
    String getRandomWord() {
        String randomWord;
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        randomWord = wordList.get(randomIndex);
        return randomWord;
    }

    /**
     * Prints the current game board.
     */
    private void printGameBoard() {
        System.out.println("\nGame Board:");
        for (String[] row : gameBoard) {
            for (String c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints the keyboard layout.
     */
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

    /**
     * Displays the end game message based on whether the word was found or not.
     */
    // The endGame() method resembles the Observer pattern by notifying observers (players or users) about the game
    // outcome. It provides different behavior based on whether the word was found or not, enabling various reactions
    // or actions to be taken by observers.
    void endGame() { //
        if (isWordFound) {
            System.out.println("Congratulations! You guessed the word: " + word + " with " + (attempts - currentAttempt) + " attempts left!");
        } else {
            System.out.println("Sorry, you ran out of attempts. The word was: " + this.word);
        }
    }

    /**
     * Checks if the current user guess is a valid word.
     *
     * @return True if the guess is a valid word, false otherwise.
     */
    boolean isValidWord() {
        if (!Objects.equals(currentWord.length(), letterCount)) {
            return false;
        }
        return this.words.contains(currentWord);
    }

    /**
     * Checks if the hidden word has been guessed by the player.
     *
     * @return True if the hidden word is guessed, false otherwise.
     */
    boolean isWordFound() {
        return currentWord.equals(word);
    }

    /**
     * Checks if the input is "yes" or "no".
     *
     * @param input The input to be checked.
     * @return True if the input is "yes" or "no", false otherwise.
     */
    static boolean isValidYesOrNo(String input) {
        return input.equals("yes") || input.equals("no");
    }

    private void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (attempts > 0 && !isWordFound) {
            displayAttemptsLeft();
            currentWord = getUserGuess(scanner);

            if (isValidWord()) {
                updateGameState();
                printGameBoard();
                printKeyboard();
                attempts--;
                currentRow++;
                checkForWordFound();
            } else {
                System.out.println("Invalid input. Please enter a " + letterCount + "-letter word.");
            }
        }

        endGame();
    }

    private void displayAttemptsLeft() {
        System.out.print("\nYou have " + (attempts - currentAttempt) + " attempts left. Enter your guess: ");
    }

    /**
     * Main method to start the Wordle game.
     *
     * @param args Command-line arguments.
     */
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

    private String getUserGuess(Scanner scanner) {
        return scanner.next().trim().toLowerCase();
    }

    private void checkForWordFound() {
        if (isWordFound()) {
            isWordFound = true;
        }
    }

    String getWord() {
        return word;
    }
    void setCurrentWord(String newWord) {
        this.currentWord = newWord;
    }
    int getCurrentRow() {
        return currentRow;
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
