package Board;

public class Board {

    /////////////////////////////////////////////////////////////
    //Fields
    /////////////////////////////////////////////////////////////

    public int boardDimension;
    public AbstractBoardElement[][] boardElements;
    public int totalBoardValue;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        this.boardElements = new AbstractBoardElement[boardDimension][boardDimension];

        fillBoardElement();

        this.totalBoardValue = calcTotalBoardValue();

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
        //Grab reference to each line around box
        Line lineAbove = (Line)this.boardElements[box.row + 1][box.column];
        Line lineBelow = (Line)this.boardElements[box.row - 1][box.column];
        Line lineLeft = (Line)this.boardElements[box.row][box.column - 1];
        Line lineRight = (Line)this.boardElements[box.row][box.column + 1];

        //if all those lines are drawn return true as it scored
        if(lineAbove.drawn && lineBelow.drawn && lineLeft.drawn && lineRight.drawn) {
            box.isComplete = true;
            return true;
        }

        return false;

    }
}
