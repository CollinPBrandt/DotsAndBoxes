package Board;

import java.util.ArrayList;

public class Board {

    /////////////////////////////////////////////////////////////
    //Fields
    /////////////////////////////////////////////////////////////

    public int boardDimension;
    public AbstractBoardElement[][] boardElements;
    public int totalBoardValue;
    public int boardFunction;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        this.boardElements = new AbstractBoardElement[boardDimension][boardDimension];

        fillBoardElement();

        this.totalBoardValue = calcTotalBoardValue();
        this.boardFunction = calculateBoardFunction();

    }

    /////////////////////////////////////////////////////////////
    //Public methods
    /////////////////////////////////////////////////////////////

    public AbstractBoardElement findElement(int findRow, int findColumn) {
        //Given a row and column, returns the elements at that point
        for (int row = 0; row < boardDimension; row++) {
            for (int column = 0; column < boardDimension; column++) {
                if (findRow == row && findColumn == column)
                    return this.boardElements[row][column];
            }
        }
        return null;
    }

    public int checkBoardForScore(){
        //iterating over each box in boardElements(odd row and columns)
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box box = (Box)this.boardElements[row][column];
                //if box has not already been completed...
                 if(!box.isComplete){
                     //if box is now isComplete return value
                     if(checkBoxForScore(box)) {
                         return box.value;
                     }
                 }
            }
        }
        return 0;
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

    public void addLine(Move move){
        checkIfMoveIsLegal(move);
        Line line = (Line)findElement(move.row, move.column);
        line.drawLine();
    }

    public boolean checkIfMoveIsLegal(Move move){
        //check move in bounds
        if(move.row > boardDimension - 1 || move.column > boardDimension - 1) {
            System.out.println("That move is outside of the board, try again");
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

    public Board copyBoard(){
        //copying fields
        Board newBoard = new Board(boardDimension);
        newBoard.boardFunction = this.boardFunction;
        newBoard.totalBoardValue = this.totalBoardValue;

        //need to copy all elements over
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
                    else {
                        //must maintain the same value in each box
                        Box newBox = new Box(row, column);
                        Box thisBox = (Box)this.boardElements[row][column];
                        newBox.value = thisBox.value;
                        boardElements[row][column] = new Box(row, column);
                    }
                }
            }
        }
        return newBoard;
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

        //row numbers, board elements, and left/right outline
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
    //Heuristic methods
    /////////////////////////////////////////////////////////////

    public int calculateBoardFunction(){
        int function = 0;

        //iterate over every box
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box currentBox = (Box)this.boardElements[row][column];
                //increases function if there are 3 sides of a box complete as you can score
                if(checkBoxForSidesComplete(currentBox) == 3){
                    function++;
                }
                //decreases function if there are 2 sides complete as if you fill the third the other player can score
                if(checkBoxForSidesComplete(currentBox) == 2){
                    function--;
                }
            }
        }
        return function;
    }



    /////////////////////////////////////////////////////////////
    //Private methods
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

    private int calcTotalBoardValue(){
        int totalBoardValue = 0;

        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box box = (Box)this.boardElements[row][column];
                totalBoardValue += box.value;
            }
        }
        return totalBoardValue;
    }

    private boolean checkBoxForScore(Box box){
        //if four sides of box are complete, it is a score
        if(checkBoxForSidesComplete(box) == 4)
            return true;
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


}
