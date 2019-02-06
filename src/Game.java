import java.util.Scanner;

public class Game {

    Board board;
    int player1Score;
    int player2Score;
    Boolean gameOver;
    Boolean player1Turn;

    public Game(int boardDimensions){
        this.board = new Board(boardDimensions);
        this.player1Score = 0;
        this.player2Score = 0;
        this.gameOver = false;
        this.player1Turn = true;
    }

    public void play(){

        while(!gameOver){

            board.printBoard();

            if(player1Turn)
                System.out.println("\nPlayer 1's Turn:");
            else
                System.out.println("\nPlayer 2's Turn:");

            makeMove();

            if(player1Turn)
                player1Score += board.checkBoxesForScore();
            else
                player2Score += board.checkBoxesForScore();

            printScore();
            player1Turn = !player1Turn;
        }
    }

    public void makeMove(){
        int row;
        int column;
        Boolean validMove = false;

        while(!validMove) {

            System.out.println("Input Format: row column");

            //get move, checking that it is two ints
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                row = scanner.nextInt();
            }
            else {
                System.out.println("Incorrect input format");
                continue;
            }

            if (scanner.hasNextInt()){
                column = scanner.nextInt();
            }
            else{
                System.out.println("Incorrect input format");
                continue;
            }

            //check move in bounds
            if(row > board.boardDimension || column > board.boardDimension) {
                System.out.println("That move is outside of the board, try again");
                continue;
            }

            //check if move is on a line space
            if (board.findElement(row, column) instanceof Line) {
                Line line = (Line) this.board.boardElements[row][column];

                //check if that line has been drawn
                if(line.drawn == true){
                    System.out.println("That line has already been drawn, try again");
                    continue;
                } else {
                    //Move is on an empty line, good to draw
                    line.drawLine();
                    validMove = true;
                }

            //else print illegal move and try again
            } else {
                System.out.println("Can only move on a line space, try again");
                continue;
            }
        }
    }

    public void printScore(){
        System.out.printf("\nPlayer 1 Score: %d\n", player1Score);
        System.out.printf("Player 2 Score: %d\n", player2Score);

    }

}
