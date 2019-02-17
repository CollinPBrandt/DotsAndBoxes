package Board;

import java.util.ArrayList;

public class Board {

    /////////////////////////////////////////////////////////////
    //Fields
    /////////////////////////////////////////////////////////////

    public int boardDimension;
    public AbstractBoardElement[][] boardElements;
    public boolean isPlayer1Turn;
    public int player1Score;
    public int player2Score;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        this.boardElements = new AbstractBoardElement[boardDimension][boardDimension];
        this.isPlayer1Turn = true;
        this.player1Score = 0;
        this.player2Score = 0;

        fillBoardElement();
    }

    /////////////////////////////////////////////////////////////
    //Public methods
    /////////////////////////////////////////////////////////////

    public int checkForScore(){
        int moveScore = 0;
        //iterating over each box in boardElements(odd row and columns)
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box box = (Box)this.boardElements[row][column];
                //if box has not already been completed...
                 if(!box.isComplete){
                     //if box is now isComplete, set as complete, return value
                     if(isBoxComplete(box)) {
                         box.isComplete = true;
                         moveScore += box.value;
                     }
                 }
            }
        }
        return moveScore;
    }

    public void updateScore(int moveScore){
        if(isPlayer1Turn)
            player1Score += moveScore;
        else
            player2Score += moveScore;
    }

    public Board deepCopyBoard(){
        //copying fields
        Board newBoard = new Board(boardDimension);
        newBoard.isPlayer1Turn = this.isPlayer1Turn;

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
                        newBox.isComplete = thisBox.isComplete;
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
    //Helper methods
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

    /////////////////////////////////////////////////////////////
    //Moves
    /////////////////////////////////////////////////////////////

    public void makeMove(Move move){
        //check if move is legal, make move, and update score
        checkIfMoveIsLegal(move);
        Line line = (Line)findElement(move.row, move.column);
        line.drawLine();
        updateScore(checkForScore());
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
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box box = (Box)this.boardElements[row][column];
                //if box has not already been completed return false
                if(!box.isComplete){
                    return false;
                }
            }
        }
        //all boxes are completed
        return true;
    }

    /////////////////////////////////////////////////////////////
    //Heuristic methods
    /////////////////////////////////////////////////////////////

    public int evaluateBoardFunction(){
        int evaluation = 0;

        //if game complete, if win then very high eval, else low
        if(isPlayer1Turn){
            if(isBoardComplete()){
                if(player1Score > player2Score){
                    evaluation += 1000;
                }
                else{
                    evaluation -= 1000;
                }
            }
        } else{
            if(isBoardComplete()){
                if(player1Score < player2Score){
                    evaluation += 1000;
                }
                else{
                    evaluation -= 1000;
                }
            }
        }

        //increase eval by score lead
        if(isPlayer1Turn){
            if(this.player1Score > this.player2Score){
                evaluation += this.player1Score - this.player2Score;
            }
            //Maximizing score for player 2
        } else {
            if (this.player2Score > this.player1Score) {
                evaluation += this.player2Score - this.player1Score;
            }
        }

        //if leave a box with 3 sides complete, not good
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box currentBox = (Box)this.boardElements[row][column];
                //Maximizing score for player 1
                if(isPlayer1Turn){
                    if(this.checkBoxForSidesComplete(currentBox) == 3){
                        evaluation -= currentBox.value;
                    }
                //Maximizing score for player 2
                } else{
                    if(this.checkBoxForSidesComplete(currentBox) == 3){
                        evaluation += currentBox.value;
                    }
                }

            }
        }

        //if leave with 2 sides complete, good
        for(int row = 1; row < boardDimension; row+= 2){
            for(int column = 1; column < boardDimension; column+= 2){
                Box currentBox = (Box)this.boardElements[row][column];
                //Maximizing score for player 1
                if(isPlayer1Turn){
                    if(this.checkBoxForSidesComplete(currentBox) == 2){
                        evaluation += currentBox.value;
                    }
                    //Maximizing score for player 2
                } else{
                    if(this.checkBoxForSidesComplete(currentBox) == 2){
                        evaluation -= currentBox.value;
                    }
                }

            }
        }
        return evaluation;
    }

}
