public class Box {


    int value;
    int index;
    Dot[] cornerDots;

    public Box(int index) {
        this.index = index;
        this.value = (int)(Math.random() * 10);
        this.cornerDots = new Dot[4];
    }
}
