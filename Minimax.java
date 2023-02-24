import java.util.ArrayList;
import java.util.List;
public class Minimax {
    // state of the game
    GameBoard gameBoard;
    //minimax depth for the Minimax algorithm
    int plies;
    Minimax(GameBoard gameBoard, int plies) {
        this.plies = plies;
        this.gameBoard = gameBoard;
        this.gameBoard.isAIMove = true;
    }

    /**
     * The move method calculates the best move by calling the minimax method
     * with the current game state and the plies value.
     */
    public GameBoard move() {
        // Initialize a new node with the current state of the game board
        Node current = new Node(gameBoard);
        // Search the game tree starting from the current node and with a specified depth (number of plies)
        Node minimax = minimax(current, 0, plies);
        // Get the best move from the resulting node
        Node move = getMove(minimax);
        // Return the game board after making the best move
        return move.gameBoard;
    }

    /** implementation of Minimax algorithm for 2-player games. It tries to determine the best move for a player based on
     the moves made by the other player.*/
    public Node minimax(Node currentNode, int treeDepth, int ply) {
        // Base case: If we have reached the maximum depth or the total number of lines on the board
        // is equal to the maximum number of lines, return the node
        if (treeDepth >= ply || currentNode.gameBoard.totalLineNum == currentNode.gameBoard.maxLineNum) {
            return currentNode;
        }
        // Determine whose turn it is: AI or not AI
        boolean isAITurn = treeDepth % 2 == 0;
        // Generate all the possible children nodes of the current node
        List<Node> children = possibleMoves(currentNode, isAITurn);
        Node bestNode = null;
        int bestValue = isAITurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Node child : children) {
            child.gameBoard.calculateScoreDifference();
            Node result = minimax(child, treeDepth + 1, ply);
            if (isAITurn && result.gameBoard.scoreDifference > bestValue ||
                    !isAITurn && result.gameBoard.scoreDifference < bestValue) {
                bestNode = result;
                bestValue = result.gameBoard.scoreDifference;
            }
        }
        return bestNode;
    }

    /** Method is used to get the move made by the AI.*/
    public Node getMove (Node currentNode) {
        // Initialize temp as the current node
        Node temp = currentNode;
        // moving up the parent nodes until the root node is reached
        while (temp.parent.parent != null) {
            temp = temp.parent;
        }
        return temp;
    }

    /** The possibleMoves method generates all possible moves from the current state and returns a list of Node objects,
     *  where each node represents a new state after a move has been made.
     *  The method copies the current board state and makes the move, updating the scores and line numbers.*/
    public List<Node> possibleMoves(Node currentState, boolean value) {
        //save all possible moves in the children list
        List<Node> children = new ArrayList<>();
        GameBoard grid = currentState.gameBoard;
        int rows = grid.rows;
        int cols = grid.cols;
        int[][] board = grid.getBoardState();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int[][] tempBoard;
                GameBoard temp;
                Node child;
                //checking if the current cell is a valid position to place a line
                if (board[i][j] == Constants.BLANK_CELL && (i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0)) {
                    // Copy the current game board state
                    tempBoard = copyArray(board, rows, cols);
                    // Create a new node for a horizontal or vertical move
                    tempBoard[i][j] = (i % 2 == 0) ? Constants.HORIZONTAL_LINE : Constants.VERTICAL_LINE;
                    temp = new GameBoard(tempBoard, grid.rows, grid.cols, grid.playerScore, grid.aisScore, value, grid.totalLineNum, grid.maxLineNum);
                    temp.updateScore(i, j, (i % 2 == 0) ? "horizontal" : "vertical");
                    temp.totalLineNum++;
                    child = new Node(temp);
                    // Set the parent node for the child node
                    child.setParent(currentState);
                    children.add(child);
                }
            }
        }
        return children;
    }

    //create new array that is a copy of the original state of the game board
    public int[][] copyArray (int[][] state, int rows, int cols) {
        int[][] temp = new int[rows][cols];
        for (int i = 0; i < rows; i ++) {
            System.arraycopy(state[i], 0, temp[i], 0, cols);
        }
        return temp;
    }
}