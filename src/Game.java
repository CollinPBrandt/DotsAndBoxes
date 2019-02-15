import Board.*;
import MinMax.Algorithm;
import MinMax.Node;

import java.util.Scanner;

public class Game {

    Board board;
    int player1Score;
    int player2Score;
    Boolean gameOver;
    Boolean player1Turn;
    Boolean player1IsAI;
    Boolean player2IsAI;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Game(int boardDimensions){
        this.board = new Board(boardDimensions);
        //track score
        this.player1Score = 0;
        this.player2Score = 0;
        //track who's turn
        this.player1Turn = true;
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

            if(player1Turn)
                System.out.println("\nPlayer 1's Turn:");
            else
                System.out.println("\nPlayer 2's Turn:");

            makeMove();

            if(player1Turn)
                player1Score += board.checkBoardForScore();
            else
                player2Score += board.checkBoardForScore();

            //Switch whose turn it is
            player1Turn = !player1Turn;

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
        //if the current player is AI, do their move
        if((player1Turn && player1IsAI) || (!player1Turn && player2IsAI)){
            makeAIMove();
        }
        else{
            makeHumanMove();
        }
    }

    private void makeAIMove(){
        Algorithm algorithm = new Algorithm(new Node(null, board), 2);
        algorithm.expand();
    }

    private void makeHumanMove(){
        Move move = new Move(-1, -1);
        Boolean validMove = false;

        while(!validMove) {

            System.out.println("Input Format: row column");

            //get move, checking that it is two ints
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
                //if move is legal, draw it. Else try again (continue);
                Line line = (Line) this.board.boardElements[move.row][move.column];
                line.drawLine();
                validMove = true;
            }
            else{
                continue;
            }
        }
    }

    private void isGameOver(){
        if(player1Score + player2Score == board.totalBoardValue){
            gameOver = true;

            String winningPlayer;
            if(player1Score > player2Score)
                winningPlayer = "Player 1!";
            else if (player1Score < player2Score)
                winningPlayer = "Player 2!";
            else
                winningPlayer = "It's a Tie!";

            System.out.println("Game Over!");
            board.printBoard();
            System.out.printf("The Winner is: %S\n", winningPlayer);
        }
    }

    private void printScore(){
        System.out.printf("\nPlayer 1 Score: %d\n", player1Score);
        System.out.printf("Player 2 Score: %d\n", player2Score);

    }

}
