public class Line extends AbstractBoardElement {

    /////////////////////////////////////////////////////////////
    //Fields
    /////////////////////////////////////////////////////////////

    boolean drawn;

    /////////////////////////////////////////////////////////////
    //Constructor
    /////////////////////////////////////////////////////////////

    public Line(int row, int column) {
        super(row, column);
        drawn = false;
    }

    /////////////////////////////////////////////////////////////
    //Methods
    /////////////////////////////////////////////////////////////

    public void drawLine(){
        this.drawn = true;
    }

    @Override
    public void print() {
        if(drawn) {
            if (row % 2 == 0)
                System.out.print(" -");
            else
                System.out.print("|");
        }
        else{
            System.out.print("  ");
        }
    }
}
