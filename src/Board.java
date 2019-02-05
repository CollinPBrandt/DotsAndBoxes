public class Board {

    public int boardBoxDimension; // side length in boxes
    public int boardDotDimension;
    public Dot[][] dots;
    public Box[][] boxes;

    public Board(int boardDimension) {
        this.boardBoxDimension = boardDimension;
        this.boardDotDimension = boardBoxDimension + 1;
        this.dots = new Dot[boardDimension + 1][boardDimension + 1];
        this.boxes = new Box[boardDimension][boardDimension];

        makeDots();
        makeBoxes();

        makeAllDotNeighbors();
        findAllCornerDots();
    }

    public void makeDots(){
        for(int row = 0; row < boardDotDimension; row++){
            for(int column = 0; column < boardDotDimension; column++){
                //number dots 0 to boardDotDimensions
                dots[row][column] = new Dot((row * boardDotDimension) + column);
            }
        }
    }

    public void makeBoxes(){
        for(int row = 0; row < boardBoxDimension; row++){
            for(int column = 0; column < boardBoxDimension; column++){
                //number dots 0 to boardDimensions squared
                boxes[row][column] = new Box((row * boardBoxDimension) + column);
            }
        }
    }

    public Dot getDot(int index){
        for (int row = 0; row < this.boardDotDimension; row++) {
            for (int column = 0; column < this.boardDotDimension; column++) {
                if(this.dots[row][column].index == index)
                    return this.dots[row][column];
            }
        }
        return null;
    }

    public void makeAllDotNeighbors(){
        for (int row = 0; row < this.boardDotDimension; row++) {
            for (int column = 0; column < this.boardDotDimension; column++) {
                this.dots[row][column].findDotNeighbors(this);
            }
        }
    }

    public void findAllCornerDots(){
        for(int row = 0; row < boardBoxDimension; row++){
            for(int column = 0; column < boardBoxDimension; column++){
                this.boxes[row][column].findDotCorners(this);
            }
        }
    }

    public void drawBoard(){

    }

}
