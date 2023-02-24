import java.util.Scanner;
import java.util.Random;

public class WeightedDotsAndBoxes {
    static Scanner userInput = new Scanner(System.in);
    static int searchPlys;
    static int rowsOnBoard;
    static int colsOnBoard;
    public static void main (String[] args) {
        gameSetup();
        //Increase the size of the gameBoard board to create spaces for the "lines" to be drawn.
        rowsOnBoard = rowsOnBoard * 2 + 1;
        colsOnBoard = colsOnBoard * 2 + 1;
        //calculate the maximum number of lines that can be drawn on a gameBoard board
        int maxLines = (rowsOnBoard * colsOnBoard) / 2;
        //rack the number of lines that have been drawn on the gameBoard board.
        int totalLines = 0;
        //create the game board
        int[][] grid = new int[rowsOnBoard][colsOnBoard];
        for (int row = 0; row < rowsOnBoard; row++) {
            for (int column = 0; column < colsOnBoard; column++) {
                //If the row index is an odd number and the column index is an odd number, then the element is assigned a random number between 1 and 5.
                if (row % 2 != 0 && column % 2 != 0) {
                    // generate values from 1-5 as per assignment
                    grid[row][column] = new Random().nextInt(5 - 1 + 1) + 1;}
                // If the row index or the column index (or both) is an odd number, then the element is assigned the value 7 (for blank).
                else if (row % 2 != 0 || column % 2 != 0) {
                    grid[row][column] = Constants.BLANK_CELL;
                }
            }
        }
        int player1Score = 0;
        int aiScore = 0;
        GameBoard gameBoard = new GameBoard(grid, rowsOnBoard, colsOnBoard, player1Score, aiScore, false, totalLines, maxLines);
        String direction = "";

        if (gameBoard.totalLineNum < gameBoard.maxLineNum) {
            do {
                gameBoard.isAIMove = false;
                System.out.println();
                System.out.println("Human Player's Turn");
                gameBoard.displayBoard();
                System.out.println("Select a row to draw a line");
                int row = userInput.nextInt();
                System.out.println("Select a column to draw a line");
                int col = userInput.nextInt();
                while (gameBoard.getBoardState()[row][col] != Constants.BLANK_CELL) {
                    System.out.println("Cannot draw line here");
                    System.out.println("Select a row to draw a line");
                    row = userInput.nextInt();
                    System.out.println("Select a column to draw a line");
                    col = userInput.nextInt();
                }
                //draw a horizontal line
                if (row % 2 == 0) {
                    gameBoard.getBoardState()[row][col] = Constants.HORIZONTAL_LINE;
                    direction = "horizontal";
                }
                //draw a vertical line
                else {
                    gameBoard.getBoardState()[row][col] = Constants.VERTICAL_LINE;
                    direction = "vertical";
                }
                gameBoard.totalLineNum++;

                gameBoard.updateScore(row, col, direction);

                if (gameBoard.totalLineNum < gameBoard.maxLineNum) {
                    Minimax ai = new Minimax(gameBoard, searchPlys);
                    gameBoard = ai.move();
                }
            } while (gameBoard.totalLineNum < gameBoard.maxLineNum);
        }
        gameBoard.displayBoard();
        gameResult(gameBoard);
    }

    //display the game result
    private static void gameResult(GameBoard gameBoard) {
        System.out.println("Game Over");
        System.out.println("Human Player Score: " + gameBoard.playerScore);
        System.out.println("AI Score: " + gameBoard.aisScore);
        if (gameBoard.playerScore > gameBoard.aisScore) System.out.println("Human Player Won.");
        else if (gameBoard.playerScore < gameBoard.aisScore) System.out.println("AI Won.");
        else System.out.println("Draw.");
    }

    //get user inout for ply number, columns and rows
    private static void gameSetup(){
        //decide on number of plies and size of the box
        System.out.println("How many plies (= 1 move for each) the AI will minimax");
        searchPlys = userInput.nextInt();
        System.out.println("Enter a number for size of the board: Rows");
        rowsOnBoard = userInput.nextInt();
        System.out.println("Enter a number for size of the board: Columns");
        colsOnBoard = userInput.nextInt();
    }
}