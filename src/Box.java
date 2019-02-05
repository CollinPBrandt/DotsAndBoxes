import java.util.ArrayList;

public class Box {


    int value;
    int index;
    ArrayList<Dot> cornerDots;

    public Box(int index) {
        this.index = index;
        this.value = (int) (Math.random() * 10);
        this.cornerDots = new ArrayList<>();
    }

    public void findDotCorners(Board board){
        for(int row = 0; row < board.boardBoxDimension; row++){
            for(int column = 0; column < board.boardBoxDimension; column++){
                //upper left dot
                this.cornerDots.add(board.getDot(this.index + row));
                //upper right dot
                this.cornerDots.add(board.getDot(this.index + row + 1));
                //lower left dot
                this.cornerDots.add(board.getDot(this.index + row + board.boardDotDimension));
                //lower right dot
                this.cornerDots.add(board.getDot(this.index + row + board.boardDotDimension + 1));
            }
        }
    }

}


