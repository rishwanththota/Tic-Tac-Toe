# 🎮 Tic-Tac-Toe — Premium Wood Edition

A professional-grade, fully object-oriented Tic-Tac-Toe game built with **Java Swing**, featuring a custom premium "Wood-Finish" UI theme, persistent score tracking, and scalable matrix grids.

## ✨ Features

| Feature | Details |
| :--- | :--- |
| 🪵 **Wood-Finish UI** | Custom "Warm Oak" palette — rich wood-tone backgrounds, ivory X, and mahogany O markers. |
| 📊 **Score Tracker** | Persistent win counters for both players across multiple rounds. |
| 🔄 **Reset Button** | Start a new game at any time without closing the app. |
| 📐 **Scalable Grids** | Dynamic board generation supporting 3x3, 5x5, and 7x7 matrix layouts. |
| 🏆 **Win Detection** | Checks all winning lines across variable board dimensions. |
| ✋ **Draw Detection** | Announces a stalemate when the board fills with no winner. |
| 🖱️ **Hover Effects** | Cells react on mouse hover for tactile feedback. |

## 🏗️ Architecture

The project follows clean OOP separation of concerns across three classes:

```text
src/
├── Main.java       # Entry point — launches the EDT and creates GameUI
├── GameLogic.java  # Pure game engine: board state, matrix rules, win/draw logic
└── GameUI.java     # All Swing components, event handling, visual feedback

### Class Responsibilities

GameLogic — knows nothing about Swing.
Manages the multidimensional board array, validates moves, checks win conditions for variable grid sizes, detects draws, and tracks cumulative scores. Can be unit-tested independently.

GameUI — knows nothing about game rules.
Builds the JFrame hierarchy (title bar → score panel + grid → status bar), handles button clicks, delegates to GameLogic, and reflects results back through custom labels and repaints.

Main — five lines.
Schedules GameUI creation on the Swing Event Dispatch Thread using SwingUtilities.invokeLater()

🔍 Win Detection Logic (explained)
The board is stored as a dynamic char[][] array. After every move, checkWin() iterates over all possible winning lines and returns true the moment it finds a completed sequence where all cells are equal and non-empty

```
0 | 1 | 2
---------
3 | 4 | 5
---------
6 | 7 | 8
```

All 8 winning lines are pre-declared as index triplets:

```java
private static final int[][] WIN_COMBINATIONS = {
    {0,1,2}, {3,4,5}, {6,7,8},   // rows
    {0,3,6}, {1,4,7}, {2,5,8},   // columns
    {0,4,8}, {2,4,6}             // diagonals
};
```

After every move, `checkWin()` iterates over all 8 and returns `true` the moment it finds a triplet where all three cells are equal **and** non-empty.

---

.

🚀 How to Run in VS Code
Prerequisites
Java Development Kit (JDK) 11 or higher

VS Code with the Extension Pack for Java installed

Steps
Clone the repository
git clone (https://github.com/rishwanththota/Tic-Tac-Toe)
cd Tic-Tac-Toe-Project

Open in VS Code
code .

Run from the terminal

# Compile all source files
   javac src/*.java -d out
   # Run the Main class
   java -cp out Main

## 📸 UI Overview

```
┌─────────────────────────────────────┐
│         TIC-TAC-TOE                 │
├──────────────┬──────────────────────┤
│  ● PLAYER X  │  ┌───┬───┬───┐      │
│    X  3      │  │ X │   │ O │      │
│              │  ├───┼───┼───┤      │
│  ○ PLAYER O  │  │   │ X │   │      │
│    O  2      │  ├───┼───┼───┤      │
│              │  │ O │   │ X │      │
│              │  └───┴───┴───┘      │
├──────────────┴──────────────────────┤
│        Player X wins! 🎉            │
│            [ NEW GAME ]             │
└─────────────────────────────────────┘
```

## 📄 License

MIT — free to use, modify, and showcase in your own portfolio.
