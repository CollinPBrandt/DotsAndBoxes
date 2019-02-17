package MinMax;

import Board.*;

public class MiniMax {
    public Board rootBoard;
    int maxPry;
    AIPlayer player;

    public MiniMax(Board rootBoard, int maxPry, AIPlayer player) {
        this.rootBoard = rootBoard;
        this.maxPry = maxPry;
        this.player = player;
    }

    /////////////////////////////////////////////////////////////
    //MiniMax no Pruning
    /////////////////////////////////////////////////////////////

    public void makeAIMove(){
        if(maxPry < 0){
            throw new IllegalArgumentException("maxPry must be >0");
        }
        miniMax(this.rootBoard, 0);
    }

    private int miniMax(Board board, int currentPry) {
        if(currentPry == maxPry || board.isBoardComplete()){
            //get evaluation function maxed for correct player
            return board.evaluateBoardFunction(player);
        }

        currentPry++;

        if(board.isPlayer1Turn){
            return getMax(board, currentPry);
        } else{
            return getMin(board, currentPry);
        }
    }

    private  int getMax(Board board, int currentPry){
        int maxEvaluation = Integer.MIN_VALUE;
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //swap who's turn it is on new rootBoard
            newBoard.isPlayer1Turn = !board.isPlayer1Turn;
            //make move
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMax(newBoard, currentPry);

            if(evaluation > maxEvaluation){
                maxEvaluation = evaluation;
                bestMove = move;
            }
        }
        board.makeMoveOnBoard(bestMove);
        return maxEvaluation;
    }

    private  int getMin(Board board, int currentPry){
        int minEvaluation = Integer.MAX_VALUE;
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //swap who's turn it is on new rootBoard
            newBoard.isPlayer1Turn = !board.isPlayer1Turn;
            //make move
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMax(newBoard, currentPry);

            if(evaluation < minEvaluation){
                minEvaluation = evaluation;
                bestMove = move;
            }
        }
        board.makeMoveOnBoard(bestMove);
        return minEvaluation;
    }

    /////////////////////////////////////////////////////////////
    //MiniMax with Pruning
    /////////////////////////////////////////////////////////////

    public void makeAIMovePruning(){
        if(maxPry < 0){
            throw new IllegalArgumentException("maxPry must be >0");
        }
        miniMaxPruning(this.rootBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private int miniMaxPruning(Board board, int currentPry, int alpha, int beta) {
        if(currentPry == maxPry || board.isBoardComplete()){
            //get evaluation function maxed for correct player
            return board.evaluateBoardFunction(player);
        }

        currentPry++;

        if(board.isPlayer1Turn){
            return getMaxPruning(board, currentPry, alpha, beta);
        } else{
            return getMinPruning(board, currentPry, alpha, beta);
        }
    }

    private  int getMaxPruning(Board board, int currentPry, int alpha, int beta){
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //swap who's turn it is on new rootBoard
            newBoard.isPlayer1Turn = !board.isPlayer1Turn;
            //make move, update boxes
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMaxPruning(newBoard, currentPry, alpha, beta);

            if(evaluation > alpha){
                alpha = evaluation;
                bestMove = move;
            }

            if(alpha >= beta) {
                break;
            }

        }
        if(bestMove != null) {
            board.makeMoveOnBoard(bestMove);
        }
        return alpha;
    }

    private  int getMinPruning(Board board, int currentPry, int alpha, int beta){
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //swap who's turn it is on new rootBoard
            newBoard.isPlayer1Turn = !board.isPlayer1Turn;
            //make move update boxes
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMaxPruning(newBoard, currentPry, alpha, beta);

            if(evaluation < beta){
                beta = evaluation;
                bestMove = move;
            }

            if(alpha >= beta){
                break;
            }
        }
        if(bestMove!= null){
            board.makeMoveOnBoard(bestMove);
        }
        return beta;
    }

}
