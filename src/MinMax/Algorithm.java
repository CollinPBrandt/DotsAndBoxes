package MinMax;

public class Algorithm {
    public Node root;
    int pry;

    public Algorithm(Node root, int pry) {
        this.root = root;
        this.pry = pry;
    }

    public void expand(){
        expand(root, pry);
    }

    public void expand(Node currentNode, int pry){
        if(pry < 1 )
            return;
        currentNode.makeChildren();
        for(Node child : currentNode.children){
            expand(child, pry - 1);
        }
    }
}
