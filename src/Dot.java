public class Dot {

    int index;
    Dot[] neighborDots;

    public Dot(int index) {
        this.index = index;
        this.neighborDots = new Dot[4];
    }
}
