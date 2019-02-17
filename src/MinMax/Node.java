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
        //starts at true for root, gets set to opposite of it's parent for all children
        this.isMax = true;
    }

    public void makeChildren(){
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        //iterate over all possible moves
        for(Move move : possibleMoves){
            //make copy of rootBoard and add move
            Board childBoard = board.deepCopyBoard();
            childBoard.makeMove(move);
            //make child node using new rootBoard
            Node childNode = new Node(this, childBoard);
            //swap between max and min
            childNode.isMax = !this.isMax;
            //add to children list
            children.add(childNode);
        }
    }
}
