package MiniMax;

import Board.*;
import Game.*;

public class MiniMax {
    Board rootBoard;
    int maxPry;
    AIPlayer currentPlayer;

    public MiniMax(Board rootBoard, int maxPry, AIPlayer currentPlayer) {
        this.rootBoard = rootBoard;
        this.maxPry = maxPry;
        this.currentPlayer = currentPlayer;
    }

    /////////////////////////////////////////////////////////////
    //MiniMax.MiniMax no Pruning
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
            return evaluateBoardFunction(board);
        }

        currentPry++;

        if(board.activeTurnPlayer == currentPlayer){
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

    public int evaluateBoardFunction(Board board){
        int evaluation = 0;

        AbstractPlayer opponent;
        if(board.player1 == currentPlayer){
            opponent = board.player2;
        } else{
            opponent = board.player1;
        }

        //Better to have your score higher
        evaluation += (board.getPlayerScore(currentPlayer) - board.getPlayerScore(opponent));


        return evaluation;
    }

    /////////////////////////////////////////////////////////////
    //MiniMax.MiniMax with Pruning
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
            return evaluateBoardFunction(board);
        }

        currentPry++;

        if(board.activeTurnPlayer == currentPlayer){
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
