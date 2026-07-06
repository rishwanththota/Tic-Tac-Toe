import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private int gridSize;
    private char[][] board;
    private boolean isPlayerXTurn;
    private boolean isSinglePlayer;
    private boolean gameOver;
    private String gameResult;
    private String player1Name = "Player 1";
    private String player2Name = "Player 2";
    private String difficulty = "MEDIUM"; // EASY, MEDIUM, HARD

    public GameLogic() {
        initGame(3, false);
    }

    public void initGame(int size, boolean singlePlayer) {
        this.gridSize = size;
        this.isSinglePlayer = singlePlayer;
        this.board = new char[size][size];
        this.isPlayerXTurn = true;
        this.gameOver = false;
        this.gameResult = "";
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void setPlayer1Name(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.player1Name = name;
        } else {
            this.player1Name = "Player 1";
        }
    }

    public String getPlayer1Name() {
        return this.player1Name;
    }

    public void setPlayer2Name(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.player2Name = name;
        } else {
            this.player2Name = "Player 2";
        }
    }

    public String getPlayer2Name() {
        return this.player2Name;
    }

    public void setDifficulty(String difficulty) {
        if (difficulty != null &&
            (difficulty.equalsIgnoreCase("EASY") ||
             difficulty.equalsIgnoreCase("MEDIUM") ||
             difficulty.equalsIgnoreCase("HARD"))) {
            this.difficulty = difficulty.toUpperCase();
        } else {
            this.difficulty = "MEDIUM";
        }
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize || board[row][col] != ' ' || gameOver) {
            return false;
        }

        // 1. Current Player ka move lagana
        board[row][col] = isPlayerXTurn ? 'X' : 'O';
        checkGameState();

        // 2. Agar Single Player mode hai aur match jari hai to AI turn chalega
        if (!gameOver) {
            isPlayerXTurn = !isPlayerXTurn;
            
            if (isSinglePlayer && !isPlayerXTurn) {
                makeAIMove();
            }
        }
        return true;
    }

    private void makeAIMove() {
        if (gameOver) return;

        // DIFFICULTY LOGIC:
        // EASY   -> AI hamesha random khelay ga (no smart moves)
        // MEDIUM -> COMPETITIVE MIX: 50% chances hain ke AI akalmandi se khelega, aur 50% random taake mix match ho
        // HARD   -> AI hamesha akalmandi se khelay ga (win/block priority)
        boolean playSmart;
        if ("EASY".equalsIgnoreCase(difficulty)) {
            playSmart = false;
        } else if ("HARD".equalsIgnoreCase(difficulty)) {
            playSmart = true;
        } else {
            playSmart = Math.random() < 0.50;
        }

        if (playSmart) {
            // Smart Decision 1: Agar AI khud kahin se jeet raha hai to furan wahan 'O' rakh kar match khatam kare
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        if (checkWin('O')) {
                            checkGameState();
                            isPlayerXTurn = true;
                            return;
                        }
                        board[i][j] = ' '; // Undo tracking
                    }
                }
            }

            // Smart Decision 2: Agar user ('X') jeetne wala hai to uski line block kare
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        if (checkWin('X')) {
                            board[i][j] = 'O'; // User ko block kar diya
                            checkGameState();
                            isPlayerXTurn = true;
                            return;
                        }
                        board[i][j] = ' '; // Undo tracking
                    }
                }
            }
        }

        // Default Balanced Play: Agar playSmart false ho ya koi winning/blocking space na mile
        makeRandomAIMove();
    }

    private void makeRandomAIMove() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board[i][j] == ' ') {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        // Sirf single khali box par bari chal kar exit karna
        if (!emptyCells.isEmpty()) {
            int randomIndex = (int) (Math.random() * emptyCells.size());
            int[] cell = emptyCells.get(randomIndex);
            board[cell[0]][cell[1]] = 'O';
            checkGameState();
            isPlayerXTurn = true;
        }
    }

    private void checkGameState() {
        if (checkWin('X')) {
            gameOver = true;
            // Input dialog mein user ka jo bhi custom naam hoga wo display hoga
            gameResult = player1Name.toUpperCase() + " WINS!!!";
        } else if (checkWin('O')) {
            gameOver = true;
            // Player 2 (ya AI, jiska default naam "Player 2" hai) ka custom naam yahan show hoga
            gameResult = player2Name.toUpperCase() + " WINS!!!";
        } else if (isBoardFull()) {
            gameOver = true;
            gameResult = "IT'S A DRAW!!!";
        }
    }

    private boolean checkWin(char player) {
        int target = (gridSize == 3) ? 3 : 4;
        
        // Horizontal Rows Check
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j <= gridSize - target; j++) {
                boolean win = true;
                for (int k = 0; k < target; k++) {
                    if (board[i][j + k] != player) { win = false; break; }
                }
                if (win) return true;
            }
        }
        
        // Vertical Columns Check
        for (int j = 0; j < gridSize; j++) {
            for (int i = 0; i <= gridSize - target; i++) {
                boolean win = true;
                for (int k = 0; k < target; k++) {
                    if (board[i + k][j] != player) { win = false; break; }
                }
                if (win) return true;
            }
        }
        
        // Main Diagonal Check
        for (int i = 0; i <= gridSize - target; i++) {
            for (int j = 0; j <= gridSize - target; j++) {
                boolean win = true;
                for (int k = 0; k < target; k++) {
                    if (board[i + k][j + k] != player) { win = false; break; }
                }
                if (win) return true;
            }
        }
        
        // Inverse Diagonal Check
        for (int i = target - 1; i < gridSize; i++) {
            for (int j = 0; j <= gridSize - target; j++) {
                boolean win = true;
                for (int k = 0; k < target; k++) {
                    if (board[i - k][j + k] != player) { win = false; break; }
                }
                if (win) return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    public char[][] getBoard() { return board; }
    public int getGridSize() { return gridSize; }
    public boolean isPlayerXTurn() { return isPlayerXTurn; }
    public boolean isGameOver() { return gameOver; }
    public String getGameResult() { return gameResult; }
    public boolean isSinglePlayer() { return isSinglePlayer; }
}
