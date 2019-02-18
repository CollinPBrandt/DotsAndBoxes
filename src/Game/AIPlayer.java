package Game;

import Board.*;
import MiniMax.*;

public class AIPlayer extends AbstractPlayer {

    private int prys;

    public AIPlayer(int prys) {
        this.prys = prys;
    }

    @Override
    public void makeMove(Board board) {
        MiniMax miniMax = new MiniMax(board, prys, this);
        miniMax.makeAIMovePruning();
    }
}
