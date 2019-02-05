public class Board {

    public int boardDimension; // side length in boxes
    public Dot[][] dots;
    public Box[][] boxes;

    public Board(int boardDimension) {
        this.boardDimension = boardDimension;
        this.dots = new Dot[boardDimension + 1][boardDimension + 1];
        this.boxes = new Box[boardDimension][boardDimension];

        makeDots();
    }

    public void makeDots(){
        for(int row = 0; row < boardDimension + 1; row++){
            for(int column = 0; column < boardDimension + 1; column++){
                //number dots 0 to boardDimensions + 1 squared
                dots[row][column] = new Dot((row * (boardDimension + 1)) + column);
            }
        }
    }

    public void makeBoxes(){
        for(int row = 0; row < boardDimension; row++){
            for(int column = 0; column < boardDimension; column++){
                //number dots 0 to boardDimensions squared
                boxes[row][column] = new Box((row * boardDimension) + column);
            }
        }
    }
}
