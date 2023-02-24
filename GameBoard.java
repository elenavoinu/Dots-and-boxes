public class GameBoard {
    /**
     Variables that represent the state of the board,
     the current turn of the AI or the player,
     the scores of the AI and the player,
     the maximum number of lines that can be drawn,
     the number of rows and columns of the board,
     the totalLineNum number of lines that have been drawn,
     and the scoreDifference between the AI and player scores.
     */
    int[][] boardState;
    int rows;
    int cols;
    int maxLineNum;
    int playerScore;
    int aisScore;
    int totalLineNum;
    boolean isAIMove;
    int scoreDifference;

    //Constructor
    GameBoard (int[][] boardState, int rows, int cols, int playerScore, int aisScore, boolean isAIMove, int totalLineNum, int maxLineNum) {
        this.rows = rows;
        this.cols = cols;
        this.playerScore = playerScore;
        this.aisScore = aisScore;
        this.isAIMove = isAIMove;
        this.boardState = boardState;
        this.totalLineNum = totalLineNum;
        this.maxLineNum = maxLineNum;
    }

    public void updateAIScore (int boxValue) {
        this.aisScore += boxValue;
    }
    public boolean isAIMove () {
        return this.isAIMove;
    }
    public int[][] getBoardState() {
        return this.boardState;
    }
    public void updatePlayerScore (int boxValue) {
        this.playerScore += boxValue;
    }

    /** This method calculates the score difference between the AI's score and the player's score and is used in
        Minimax algorithm to determine the best move in the game by AI. */
    public void calculateScoreDifference() {
        this.scoreDifference = this.aisScore - this.playerScore;
    }

    /**This method prints the gameBoard board as a 2d array to the console.
     * Each element in the board has a value of 0, 7, 9, or 11 representing:
     *  0 = ".", 7 = blank, 9 = horizontal line, 11 = vertical line */
    public void displayBoard() {
        for (int i = 0; i < rows; i++ ) {
            for (int j = 0; j < cols; j ++) {
                switch (boardState[i][j]) {
                    case 0 -> System.out.print("." + " ");
                    case Constants.BLANK_CELL -> System.out.print(" " + " ");
                    case Constants.HORIZONTAL_LINE -> System.out.print("-" + " ");
                    case Constants.VERTICAL_LINE -> System.out.print("|" + " ");
                    default -> System.out.print(boardState[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /** This method checks the direction of the player's move and based on that calls either
     *  the "horizontalTop" method or the "horizontalBottom" method.
     *  If the player's move is "vertical", it calls either the "verticalLeft"
     *  method or the "verticalRight".*/
    public void updateScore(int row, int column, String direction) {
        if (direction.equals("horizontal")) {
            if (row == 0) horizontalTop(row, column);
            else if (row == rows - 1) horizontalBottom(row, column);
            else {
                horizontalTop(row, column);
                horizontalBottom(row, column);
            }
        }
        else if (direction.equals("vertical")) {
            if (column == 0) verticalLeft(row, column);
            else if (column == cols - 1) verticalRight(row, column);
            else {
                verticalLeft(row, column);
                verticalRight(row, column);
            }
        }
    }

    /** This method checks if there is a horizontal line above and below the current
     *  position and a vertical line two positions to the left of the current position.
     *  If these conditions are true, the score is updated for either the AI or the player
     *  The score is updated based on the value in the boardState array at the current position.*/
    private void verticalRight(int row, int col) {
        if (boardState[row-1][col-1] == 9 && boardState[row+1][col-1] == 9 && boardState[row][col-2] == 11) {
            if (isAIMove) {
                updateAIScore(boardState[row][col-1]);
            }
            else {
                updatePlayerScore(boardState[row][col-1]);
            }
        }
    }

   /** The verticalLeft method checks for a possible score increase for the player or AI by checking for
    * a vertical line and two horizontal lines adjacent to the blank cell in the left direction of the selected row
    * and column. If the condition is met, the score is updated for either the player or the AI based on whose move it is,
    * using the updateAIScore or updatePlayerScore method.*/
    private void verticalLeft(int row, int col) {
        if (boardState[row-1][col+1] == 9 && boardState[row+1][col+1] == 9 && boardState[row][col+2] == 11) {
            if (isAIMove) {
                updateAIScore(boardState[row][col+1]);
            }
            else {
                updatePlayerScore(boardState[row][col+1]);
            }
        }
    }

    /** horizontalBottom method, the code checks if there are two vertical lines and a horizontal line in the specified
       locations and updates the score based on the state of the cell in the middle.*/
    private void horizontalBottom(int row, int col) {
        if (boardState[row-1][col-1] == 11 && boardState[row-1][col+1] == 11 && boardState[row-2][col] == 9) {
            if (isAIMove) {
                updateAIScore(boardState[row-1][col]);
            }
            else {
                updatePlayerScore(boardState[row-1][col]);
            }
        }
    }

    /** Checks if a horizontal line is formed at the top of a given cell by checking the cells on either
     * side of the cell in the top row and the cell below it. If a horizontal line is formed,
     * it updates the score of either the AI or the player depending on whose turn it is.*/
    private void horizontalTop(int row, int col) {
        if (boardState[row+1][col-1] == 11 && boardState[row+1][col+1] == 11 && boardState[row+2][col] == 9) {
            if (isAIMove) {
                updateAIScore(boardState[row+1][col]);
            }
            else {
                updatePlayerScore(boardState[row+1][col]);
            }
        }
    }
}
