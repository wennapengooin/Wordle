package main.java;

import java.util.*;

public class WordleGame {
    public static final int WORD_LENGTH = 5; // Length of the secret word
    public static final int MAX_ATTEMPTS = 6; // Maximum number of attempts

    private static String secretWord; // Core entity of the program
    private static List<Character> guessedLetters;
    private static List<String> feedbackHistory;
    private static List<Integer> correctPositions;
    private static final WordLoader wordLoader = new WordLoader("C:/Users/HP/IdeaProjects/Wordle/src/main/resources/5_letter_words.txt");
    private static final List<String> wordList = wordLoader.getWordList();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Wordle!");

        boolean playAgain = true;
        while (playAgain) {
            initializeGame();

            System.out.println("Guess the " + WORD_LENGTH + "-letter word.");

            int attempts = 0;
            while (attempts < MAX_ATTEMPTS) {
                System.out.println("Attempt #" + (attempts + 1));
                System.out.print("Enter your guess: ");
                String guess = scanner.next().trim().toLowerCase();

                if (guess.length() != WORD_LENGTH) {
                    System.out.println("Invalid input. Please enter a " + WORD_LENGTH + "-letter word.");
                    continue;
                }

                // Validate the user input against the word list
                if (!isValidWord(guess)) {
                    System.out.println("Not a valid word. Please enter a valid word.");
                    continue;
                }

                if (isCorrectGuess(guess)) {
                    System.out.println("Congratulations! You guessed the word: " + secretWord);
                    break;
                } else {
                    attempts++;
                    displayFeedback(guess);
                }
            }

            if (attempts == MAX_ATTEMPTS) {
                System.out.println("Sorry, you ran out of attempts. The secret word was: " + secretWord);
            }

            System.out.print("Do you want to play again? (y/n): ");
            String playAgainResponse = scanner.next().trim().toLowerCase();
            playAgain = playAgainResponse.equals("y");
        }

        System.out.println("Thanks for playing Wordle!");
        scanner.close();
    }

    public static void initializeGame() {
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        secretWord = wordList.get(randomIndex);
        guessedLetters = new ArrayList<>();
        feedbackHistory = new ArrayList<>();
        correctPositions = new ArrayList<>();
    }

    public static boolean isCorrectGuess(String guess) {
        return guess.equals(secretWord);
    }

    static void displayFeedback(String guess) {
        guessedLetters.clear();
        correctPositions.clear();
        Set<Character> correctInWrongSpot = new HashSet<>(); // Keep track of letters guessed correctly in the wrong spot
        for (char letter : guess.toCharArray()) {
            guessedLetters.add(letter);
        }

        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < WORD_LENGTH; i++) {
            char letter = secretWord.charAt(i);
            char guessedLetter = guess.charAt(i);

            if (isCorrectGuessInRightSpot(letter, guessedLetter)) {
                feedback.append(letter); // Guessed correctly in the right spot
                correctPositions.add(i); // Record the position of correctly guessed letter
                correctInWrongSpot.add(letter); // Mark as already displayed in the wrong spot
            } else if (isCorrectGuessInWrongSpot(guessedLetter) && !correctPositions.contains(i) && !correctInWrongSpot.contains(guessedLetter)) {
                feedback.append(Character.toUpperCase(guessedLetter)); // Guessed correctly but in the wrong spot
            } else {
                feedback.append("*"); // Not guessed
            }
        }

        feedbackHistory.add(feedback.toString());
        System.out.println("Feedback:");
        for (int i = 0; i <= feedbackHistory.size() - 1; i++) {
            System.out.println("  " + feedbackHistory.get(i));
        }

        showKeyboard();
    }

    static boolean isCorrectGuessInRightSpot(char letter, char guessedLetter) {
        return guessedLetter == letter;
    }

    static boolean isCorrectGuessInWrongSpot(char guessedLetter) {
        return secretWord.contains(String.valueOf(guessedLetter));
    }

    // Validate if a given word is in the word list
    private static boolean isValidWord(String word) {
        return wordList.contains(word);
    }


    private static void showKeyboard() {
        System.out.println("Keyboard:");
        char[] keyboardLayout = {
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
                'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
                'z', 'x', 'c', 'v', 'b', 'n', 'm'
        };

        for (char letter : keyboardLayout) {
            if (guessedLetters.contains(letter)) {
                System.out.print("# ");
            } else {
                System.out.print(letter + " ");
            }

            // Add a newline after each row for QWERTY layout
            if (letter == 'p' || letter == 'l' || letter == 'm') {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static String getSecretWord() {
        return secretWord;
    }

    public static void setSecretWord(String newSecretWord) {
        secretWord = newSecretWord;
    }

    public static List<String> getFeedbackHistory() {
        return feedbackHistory;
    }
}