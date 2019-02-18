package Game;

import Board.*;

import java.util.Scanner;

public class Game {

    Board board;
    int prys;
    Boolean gameOver;

    AbstractPlayer player1;
    AbstractPlayer player2;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Game(int boardDimensions, int prys){
        this.board = new Board(boardDimensions);
        this.prys = prys;
        //track game state
        this.gameOver = false;

    }

    /////////////////////////////////////////////////////////////
    //Public Methods
    /////////////////////////////////////////////////////////////

    public void play(){
        //makes new players depending on AI or Human
        playerSelect();
        board.setPlayers(player1, player2);
        //start with player 1
        board.activeTurnPlayer = player1;

        while(!gameOver){

            board.printBoard();

            //if it's player 1 turn...
            if(board.activeTurnPlayer == player1) {
                System.out.println("\nPlayer 1's Turn:");
                player1.makeMove(board);
            }
            //if its player 2 turn...
            else {
                System.out.println("\nPlayer 2's Turn:");
                player2.makeMove(board);
            }

            isGameOver();
            printScore();
        }
    }

    /////////////////////////////////////////////////////////////
    //Private Methods
    /////////////////////////////////////////////////////////////

    private void playerSelect() {

        Scanner scanner1 = new Scanner(System.in);
        boolean legalInput1 = false;
        boolean legalInput2 = false;

        while(legalInput1 == false) {
            System.out.println("Type 'Human' for human player1, or 'AI' for AI player1");
            String p1 = scanner1.nextLine();
            if (p1.equals("AI")) {
                System.out.println("Player1 is an AI\n");
                player1 = new AIPlayer(prys);
                legalInput1 = true;
            } else if (p1.equals("Human")) {
                System.out.println("Player1 is a Human\n");
                player1 = new HumanPlayer();
                legalInput1 = true;
            } else {
                System.out.println("Unknown Input");
            }
        }
        while(legalInput2 == false){
            System.out.println("Type 'Human' for human player2, or 'AI' for AI player2");
            String p2 = scanner1.nextLine();

            if(p2.equals("AI")) {
                System.out.println("Player2 is an AI\n");
                player2 = new AIPlayer(prys);
                legalInput2 = true;
            } else if(p2.equals("Human")){
                System.out.println("Player2 is an Human\n");
                player2 = new HumanPlayer();
                legalInput2 = true;
            } else{
                System.out.println("Unknown Input");
            }
        }
    }

    private void isGameOver(){
        if(board.isBoardComplete()){
            gameOver = true;

            String winningPlayer;
            if(board.getPlayer1Score() > board.getPlayer2Score())
                winningPlayer = "Player 1!";
            else if (board.getPlayer1Score() < board.getPlayer2Score())
                winningPlayer = "Player 2!";
            else
                winningPlayer = "It's a Tie!";

            System.out.println("Game.Game Over!");
            board.printBoard();
            System.out.printf("The Winner is: %S\n", winningPlayer);
        }
    }

    private void printScore(){
        System.out.printf("\nPlayer 1 Score: %d\n", board.getPlayer1Score());
        System.out.printf("Player 2 Score: %d\n", board.getPlayer2Score());

    }

}
