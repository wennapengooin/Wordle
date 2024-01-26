# Wordle

**Table of Contents**

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Game Instructions](#game-instructions)
- [Design Patterns](#design-patterns)
- [Contributing](#contributing)
- [License](#license)

## Introduction

The Wordle Game Application is a simple text-based game where players try to guess a hidden word within a limited number of attempts. The game provides an interactive and enjoyable experience for players to test their word-guessing skills.

## Features

- Randomly selects a hidden word from a predefined word list.
- Interactive gameplay that allows players to make guesses.
- Displays the current state of the game board and the keyboard.
- Informs players of their remaining attempts and whether they guessed the word.
- Offers a "Play Again" option to restart the game.

## Getting Started

1. Clone this repository to your local machine.
2. Ensure you have Java installed.
3. Open a terminal and navigate to the project directory.
4. Copy the file path of '5_letter_words.txt' and replace the current file path in the wordLoader attribute.
4. Compile the code using `javac Wordle.java`.
5. Run the game using `java Wordle`.

## How to Play

1. The game randomly selects a hidden word of a certain length.
2. You have a limited number of attempts to guess the word.
3. Enter a guess by typing a word of the same length as the hidden word.
4. The game will provide feedback by revealing correct letters on the game board.
5. Continue making guesses until you either guess the word or run out of attempts.

## Game Instructions

- Type your guesses using lowercase letters. The game is case-insensitive.
- The game board shows your progress in guessing the hidden word.
- The keyboard displays the available letters for making guesses.
- If you guess a letter that is in the hidden word but in the wrong position, it will be shown in uppercase.
- If you guess a letter that is not in the hidden word, it will be replaced with an asterisk (*).
- After winning or losing, you can choose to play again or exit the game.

*This program does not explicitly follow the principles of the Clean Architecture. Please refer to CA prompt.
we can discuss how certain elements might align with its principles:

Separation of Concerns: The class seems to encapsulate various components of the Wordle game logic, such as initializing the game, updating the game state, and interacting with the player. While these components are not organized into distinct architectural layers, they do exhibit a form of separation of concerns within the class.

Dependency Rule: The class's dependencies, like WordLoader, are abstracted away and encapsulated within the class. This provides a level of encapsulation and allows for potential future changes to be isolated within the class, adhering to the Dependency Rule from the Clean Architecture.

Modularity: The class defines different methods that handle specific tasks such as initializing the game, updating the game state, and displaying game information. This approach can be seen as a form of modularity, as each method focuses on a particular responsibility.

Single Responsibility Principle (SRP): While the class takes on multiple responsibilities, individual methods within the class seem to adhere to the SRP by focusing on specific tasks. For instance, methods like initializeGameBoard() and updateGameState() have distinct roles.

User Interaction: The class handles user interaction through the playGame() method and prompts the user for input using the Scanner class. While the class doesn't include a dedicated "User Interface" layer as Clean Architecture suggests, it does handle user interactions within its context.
