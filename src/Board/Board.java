package Board;

import Game.*;

import java.util.ArrayList;

public class Board {

    /////////////////////////////////////////////////////////////
    //Fields
    /////////////////////////////////////////////////////////////

    public int boardDimension;
    public AbstractBoardElement[][] boardElements;
    public Box[] boxes;
    public Line[] lines;
    //public boolean isPlayer1Turn;

    public AbstractPlayer player1;
    public AbstractPlayer player2;

    public AbstractPlayer activeTurnPlayer;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        this.boardElements = new AbstractBoardElement[boardDimension][boardDimension];

        fillBoardElement();
        this.boxes = getBoxes();
        this.lines = getLines();
    }

    /////////////////////////////////////////////////////////////
    //SetUp Methods
    /////////////////////////////////////////////////////////////

    private void fillBoardElement(){
        for(int row = 0; row < boardDimension; row++){
            for(int column = 0; column < boardDimension; column++){
                if(row % 2 == 0){
                    if(column % 2 == 0)
                        boardElements[row][column] = new Dot(row, column);
                    else
                        boardElements[row][column] = new Line(row, column);
                }
                else{
                    if(column % 2 == 0)
                        boardElements[row][column] = new Line(row, column);
                    else
                        boardElements[row][column] = new Box(row, column);
                }
            }
        }
    }

    private Box[] getBoxes(){
        Box[] boxes = new Box[(boardDimension/2)*(boardDimension/2)];
        int index = 0;
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2) {
                boxes[index] = (Box)boardElements[row][column];
                index++;
            }
        }
        return boxes;
    }

    private Line[] getLines(){
        Line[] lines = new Line[(boardDimension/2)*(boardDimension + 1)];
        int index = 0;
        for(int row = 0; row < boardDimension; row++){
            for(int column = 0; column < boardDimension; column++){
                if(row % 2 == 0){
                    if(column % 2 != 0) {
                        lines[index] = (Line)boardElements[row][column];
                        index++;
                    }
                }
                else{
                    if(column % 2 == 0) {
                        lines[index] = (Line)boardElements[row][column];
                        index++;
                    }
                }
            }
        }
        return lines;
    }

    public void setPlayers(AbstractPlayer player1, AbstractPlayer player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    private AbstractBoardElement findElement(int findRow, int findColumn) {
        //Given a row and column, returns the elements at that point
        for (int row = 0; row < boardDimension; row++) {
            for (int column = 0; column < boardDimension; column++) {
                if (findRow == row && findColumn == column)
                    return this.boardElements[row][column];
            }
        }
        return null;
    }

    public void printBoard(){
        System.out.println();

        //column numbers
        System.out.print("  ");
        for(int i = 0; i < boardDimension; i++){
            System.out.printf(" %d",i);
        }

        System.out.println();
        System.out.print("  ");

        //top outline
        for(int i = 0; i < boardDimension * 2 + 1; i++){
            System.out.printf("-",i);
        }

        //row numbers, rootBoard elements, and left/right outline
        for(int row = 0; row < boardDimension; row++){
            System.out.println();
            System.out.print(row);
            System.out.print("|");
            for(int column = 0; column < boardDimension; column++) {
                boardElements[row][column].print();
            }
            System.out.print(" |");
        }

        //bottom outline
        System.out.print("\n  ");
        for(int i = 0; i < boardDimension * 2 + 1; i++){
            System.out.printf("-",i);
        }

        System.out.println();
    }


    /////////////////////////////////////////////////////////////
    //Scoring Methods
    /////////////////////////////////////////////////////////////

    public void checkForNewScore(){
        //iterating over each box in boxes
        for(Box box : boxes) {
            //if box hasn't been marked complete...
            if (box.scoringPlayer == null) {
                //...but box is now Complete, set who completed it
                if (isBoxComplete(box)) {
                    if (activeTurnPlayer == player1) {
                        box.scoringPlayer = player1;
                    } else{
                        box.scoringPlayer = player2;
                    }
                }
            }
        }
    }

    public void updateScore(){
        getPlayer1Score();
        getPlayer2Score();
    }

    public int getPlayerScore(AbstractPlayer player){
        if(player1 == player)
            return getPlayer1Score();
        else
            return getPlayer2Score();
    }

    public int getPlayer1Score(){
        int player1Score = 0;
        for(Box box : boxes) {
            if(box.scoringPlayer == player1){
                player1Score += box.value;
            }
        }
        return player1Score;
    }

    public int getPlayer2Score(){
        int player2Score = 0;
        for(Box box : boxes) {
            if(box.scoringPlayer == player2){
                player2Score += box.value;
            }
        }
        return player2Score;
    }

    /////////////////////////////////////////////////////////////
    //Move Methods
    /////////////////////////////////////////////////////////////

    public void makeMoveOnBoard(Move move){
        //check if move is legal, make move, update boxes to reflect score
        checkIfMoveIsLegal(move);
        Line line = (Line)findElement(move.row, move.column);
        line.drawLine();
        checkForNewScore();
        //swap active player after move
        if(this.activeTurnPlayer == player1)
            this.activeTurnPlayer = player2;
        else
            this.activeTurnPlayer = player1;
    }

    public boolean checkIfMoveIsLegal(Move move){
        //check move in bounds
        if(move.row > boardDimension - 1 || move.column > boardDimension - 1) {
            System.out.println("That move is outside of the rootBoard, try again");
            return false;
        }
        if(!checkIfMoveIsOnLineSpace(move)){
            System.out.println("Can only move on a line space, try again");
            return false;
        }
        //check if move is on a line space
        if(checkIfMoveHasBeenDrawn(move)){
            System.out.println("That line has already been drawn, try again");
            return false;
        }

        return true;
    }

    private boolean checkIfMoveIsOnLineSpace(Move move){
        if(move.row % 2 == 0 && move.column % 2 != 0)
            return true;

        else if(move.row % 2 != 0 && move.column % 2 == 0)
            return true;

        else
            return false;
    }

    private boolean checkIfMoveHasBeenDrawn(Move move) {
        if (checkIfMoveIsOnLineSpace(move)) {
            Line line = (Line) boardElements[move.row][move.column];
            //check if that line has been drawn
            if (line.drawn)
                return true;
        }
        //Board.Move is on an empty line, good to draw
        return false;
    }

    public ArrayList<Move> getAllPossibleMoves(){
        ArrayList<Move> possibleMoves = new ArrayList();

        //for every possible move, check if legal and if it is add to possible moves
        for(int row = 0; row < boardDimension; row++){
            for(int column = 0; column < boardDimension; column++) {
                Move move = new Move(row, column);
                if(checkIfMoveIsOnLineSpace(move) && !checkIfMoveHasBeenDrawn(move)){
                    possibleMoves.add(move);
                }
            }
        }

        return possibleMoves;
    }

    /////////////////////////////////////////////////////////////
    //Checking Boxes
    /////////////////////////////////////////////////////////////

    private boolean isBoxComplete(Box box){
        //if four sides of box are complete, it is a score
        if(checkBoxForSidesComplete(box) == 4) {
            return true;
        }
        else
            return false;
    }

    private int checkBoxForSidesComplete(Box box){
        int sidesComplete = 0;

        //check if each line is drawn, if it is add 1 to sidesComplete
        Line lineAbove = (Line)this.boardElements[box.row + 1][box.column];
        if(lineAbove.drawn)
            sidesComplete++;

        Line lineBelow = (Line)this.boardElements[box.row - 1][box.column];
        if(lineBelow.drawn)
            sidesComplete++;

        Line lineLeft = (Line)this.boardElements[box.row][box.column - 1];
        if(lineLeft.drawn)
            sidesComplete++;

        Line lineRight = (Line)this.boardElements[box.row][box.column + 1];
        if(lineRight.drawn)
            sidesComplete++;

        return sidesComplete;
    }

    /////////////////////////////////////////////////////////////
    //Winning
    /////////////////////////////////////////////////////////////

    public boolean isBoardComplete(){
        //iterate over all boxes
        for(Box box : boxes){
            //if a box has no scoring player, board not complete
            if(box.scoringPlayer == null){
                return false;
            }
        }
        return true;
    }

    /////////////////////////////////////////////////////////////
    //Minimax methods
    /////////////////////////////////////////////////////////////

    public Board deepCopyBoard(){
        //copying fields
        Board newBoard = new Board(boardDimension);
        //not deep copy of players
        newBoard.setPlayers(this.player1, this.player2);
        newBoard.activeTurnPlayer = this.activeTurnPlayer;

        //need to copy all elements over
        for(int row = 0; row < boardDimension; row++){
            for(int column = 0; column < boardDimension; column++){
                if(row % 2 == 0){
                    if(column % 2 != 0){
                        //must maintain drawn lines
                        Line thisLine = (Line)this.boardElements[row][column];
                        Line newLine = (Line)newBoard.boardElements[row][column];
                        newLine.drawn = thisLine.drawn;
                    }
                }
                else{
                    if(column % 2 == 0){
                        //must maintain drawn lines
                        Line thisLine = (Line)this.boardElements[row][column];
                        Line newLine = (Line)newBoard.boardElements[row][column];
                        newLine.drawn = thisLine.drawn;
                    }
                    else {
                        //must maintain the same value in each box
                        Box thisBox = (Box)this.boardElements[row][column];
                        Box newBox = (Box)newBoard.boardElements[row][column];
                        newBox.value = thisBox.value;
                        newBox.scoringPlayer = thisBox.scoringPlayer;
                    }
                }
            }
        }
        newBoard.getBoxes();
        newBoard.getLines();
        return newBoard;
    }


}
