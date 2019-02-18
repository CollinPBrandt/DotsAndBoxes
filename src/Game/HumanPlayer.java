package Game;

import Board.*;

import java.util.Scanner;

public class HumanPlayer extends AbstractPlayer {


    public HumanPlayer(){}

    @Override
    public void makeMove(Board board) {
        Move move = new Move(-1, -1);
        Boolean validMove = false;

        while(!validMove) {

            System.out.println("Input Format: row column");

            //get makeMoveOnBoard, checking that it is two ints
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                move.row = scanner.nextInt();
            } else {
                System.out.println("Incorrect input format");
                continue;
            }

            if (scanner.hasNextInt()) {
                move.column = scanner.nextInt();
            } else {
                System.out.println("Incorrect input format");
                continue;
            }
            if(board.checkIfMoveIsLegal(move)){
                validMove = true;
                //if makeMoveOnBoard is legal, draw it and add score. Else try again (continue);
                board.makeMoveOnBoard(move);
                //update boxes
                board.checkForNewScore();
                //update score
                board.updateScore();
            }
            else{
                continue;
            }
        }
    }
}
