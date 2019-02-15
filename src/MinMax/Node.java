package MinMax;

import java.util.ArrayList;
import Board.*;

public class Node {
    Node parent;
    ArrayList<Node> children;

    Board board;
    Boolean isMax;

    public Node(Node parent, Board board) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.board = board;
        this.isMax = true;
    }

    public void makeChildren(){
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        //iterate over all possible moves
        for(Move move : possibleMoves){
            //make copy of board and add move
            Board childBoard = board.copyBoard();
            childBoard.addLine(move);
            //make child node using new board
            Node childNode = new Node(this, childBoard);
            //add to children list
            children.add(childNode);
        }
    }
}
