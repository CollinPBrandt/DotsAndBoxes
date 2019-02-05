import java.util.ArrayList;

public class Dot {

    int index;
    ArrayList<Dot> neighborDots;

    public Dot(int index) {
        this.index = index;
        this.neighborDots = new ArrayList<>();
    }


    public void findDotNeighbors(Board board) {
        //index is in top row, add dot below
        if(this.index < board.boardDotDimension){
            this.neighborDots.add(board.getDot(this.index + board.boardDotDimension));
        }
        //index is in bottom row, add dot above
        else if(this.index >= board.boardDotDimension * board.boardBoxDimension){
            this.neighborDots.add(board.getDot(this.index - board.boardDotDimension));
        }
        //index is not bottom or top, add dot above and below
        else{
            this.neighborDots.add(board.getDot(this.index - board.boardDotDimension));
            this.neighborDots.add(board.getDot(this.index + board.boardDotDimension));
        }

        //index is in leftmost column, add dot to right
        if(this.index % board.boardDotDimension == 0){
            this.neighborDots.add(board.getDot(this.index + 1));
        }
        //index is in rightmost column, add dot to left
        else if(this.index % board.boardDotDimension == board.boardBoxDimension){
            this.neighborDots.add(board.getDot(this.index - 1));
        }
        //index is not leftmost or rightmost, add dot left and right
        else{
            this.neighborDots.add(board.getDot(this.index - 1));
            this.neighborDots.add(board.getDot(this.index + 1));
        }
    }


}

