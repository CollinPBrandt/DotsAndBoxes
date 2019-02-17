import Board.*;
import MinMax.MiniMax;

import java.util.Scanner;

public class Game {

    Board board;
    Boolean gameOver;
    Boolean player1IsAI;
    Boolean player2IsAI;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Game(int boardDimensions){
        this.board = new Board(boardDimensions);
        //track who is human/AI
        this.player1IsAI = false;
        this.player2IsAI = false;
        //track game state
        this.gameOver = false;
    }

    /////////////////////////////////////////////////////////////
    //Public Methods
    /////////////////////////////////////////////////////////////

    public void play(){

        playerSelect();

        while(!gameOver){

            board.printBoard();

            if(board.isPlayer1Turn)
                System.out.println("\nPlayer 1's Turn:");
            else
                System.out.println("\nPlayer 2's Turn:");

            makeMove();

            //board.checkForAndUpdateScore();

            //Switch whose turn it is
            board.isPlayer1Turn = !board.isPlayer1Turn;

            isGameOver();
            printScore();
        }
    }

    /////////////////////////////////////////////////////////////
    //Private Methods
    /////////////////////////////////////////////////////////////

    private void playerSelect() {

        System.out.println("Press 1 for AI player 1, press anything else for human player 1");

        Scanner scanner1 = new Scanner(System.in);
        if (scanner1.hasNextInt()) {
            if (scanner1.nextInt() == 1) {
                player1IsAI = true;
            }
        }

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Press 2 for AI player 2, press anything else for human player 2");
        if (scanner2.hasNextInt()) {
            if (scanner2.nextInt() == 2) {
                player2IsAI = true;
            }
        }
    }


    private void makeMove(){
        //if the current player is AI, do their makeMove
        if((board.isPlayer1Turn && player1IsAI) || (!board.isPlayer1Turn && player2IsAI)){
            makeAIMove();
        }
        else{
            makeHumanMove();
        }
    }

    private void makeAIMove(){
        MiniMax miniMax = new MiniMax(board, 4);
        miniMax.makeAIMove();
    }

    private void makeHumanMove(){
        Move move = new Move(-1, -1);
        Boolean validMove = false;

        while(!validMove) {

            System.out.println("Input Format: row column");

            //get makeMove, checking that it is two ints
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
                //if makeMove is legal, draw it. Else try again (continue);
                board.makeMove(move);
            }
            else{
                continue;
            }
        }
    }

    private void isGameOver(){
        if(board.isBoardComplete()){
            gameOver = true;

            String winningPlayer;
            if(board.player1Score > board.player2Score)
                winningPlayer = "Player 1!";
            else if (board.player1Score < board.player2Score)
                winningPlayer = "Player 2!";
            else
                winningPlayer = "It's a Tie!";

            System.out.println("Game Over!");
            board.printBoard();
            System.out.printf("The Winner is: %S\n", winningPlayer);
        }
    }

    private void printScore(){
        System.out.printf("\nPlayer 1 Score: %d\n", board.player1Score);
        System.out.printf("Player 2 Score: %d\n", board.player2Score);

    }

}
