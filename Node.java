public class Node {
    GameBoard gameBoard;
    Node parent;

    //Constructor
    Node(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    //Keep track of the previous node in the path being considered.
    public void setParent (Node parent) {
        this.parent = parent;
    }
}