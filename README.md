# G&W Turtle Bridge

**G&W Turtle Bridge** is a computer implementation of the classic handheld game from the Game & Watch series. The player controls a character carrying packages across a bridge made of turtles. The turtles can dive underwater, requiring precision and good timing.

## Gameplay Description

The player moves across turtles forming a temporary bridge over water. The goal is to transport packages from one side to the other and return. Fish appear in the game and can be caught by turtles. The maximum score is 999 points — reaching this ends the game.

## Features

**Player Controls:**

- A – move left  
- D – move right  
- S – start/pause the game  

**Game Mechanics:**

- Moving across turtles that can dive  
- Carrying and delivering packages  
- Fish appearing that turtles can catch  

**Score Display:**

- Score shown in seven-segment style (using the `SevenSegmentDigit` class)  

**Event Handling:**

- Listener mechanism to handle game state and score updates  

## Project Structure

src/
├── Main.java // Main application entry point
└── p02/
├── game/
│ └── Board.java // Game logic: board, player, events
└── pres/
├── GameFrame.java // Application window with GUI
├── JBoard.java // Panel responsible for rendering the game
└── SevenSegmentDigit.java // Class for drawing seven-segment style digits


## Requirements

- Java 11 or newer  
- Built-in Java libraries (`javax.swing`, `java.awt`)  

## How to Run

1. Clone the repository:  
   ```bash
   git clone https://github.com/knuuricchi/G-W_TurtleBridge_P02.git

    Open the project in IntelliJ IDEA or another Java-supporting IDE.

    Run the Main.java file.

Game Rules

    The player starts on the left side of the board.

    Upon reaching the right side, the player picks up a package.

    Returning to the left side delivers the package.

    Watch out for turtles diving underwater — falling into water causes a loss.

    The game ends after reaching 999 points.
