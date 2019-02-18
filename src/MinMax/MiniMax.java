package MinMax;

import Board.*;

public class MiniMax {
    public Board rootBoard;
    int maxPry;

    public MiniMax(Board rootBoard, int maxPry) {
        this.rootBoard = rootBoard;
        this.maxPry = maxPry;
    }

    /////////////////////////////////////////////////////////////
    //MiniMax no Pruning
    /////////////////////////////////////////////////////////////

    public void makeAIMove(AIPlayer player){
        if(maxPry < 0){
            throw new IllegalArgumentException("maxPry must be >0");
        }
        miniMax(this.rootBoard, 0, player);
    }

    private int miniMax(Board board, int currentPry, AIPlayer currentPlayer) {
        if(currentPry == maxPry || board.isBoardComplete()){
            //get evaluation function maxed for correct player
            return evaluateBoardFunction(board, currentPlayer);
        }

        currentPry++;

        if(board.activeTurnPlayer == currentPlayer){
            return getMax(board, currentPry, currentPlayer);
        } else{
            return getMin(board, currentPry, currentPlayer);
        }
    }

    private  int getMax(Board board, int currentPry, AIPlayer currentPlayer){
        int maxEvaluation = Integer.MIN_VALUE;
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //make move
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMax(newBoard, currentPry, currentPlayer);

            if(evaluation > maxEvaluation){
                maxEvaluation = evaluation;
                bestMove = move;
            }
        }
        board.makeMoveOnBoard(bestMove);
        return maxEvaluation;
    }

    private  int getMin(Board board, int currentPry, AIPlayer currentPlayer){
        int minEvaluation = Integer.MAX_VALUE;
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //make move
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMax(newBoard, currentPry, currentPlayer);

            if(evaluation < minEvaluation){
                minEvaluation = evaluation;
                bestMove = move;
            }
        }
        board.makeMoveOnBoard(bestMove);
        return minEvaluation;
    }

    public int evaluateBoardFunction(Board board, AIPlayer currentPlayer){
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
    //MiniMax with Pruning
    /////////////////////////////////////////////////////////////

    public void makeAIMovePruning(AIPlayer currentPlayer){
        if(maxPry < 0){
            throw new IllegalArgumentException("maxPry must be >0");
        }
        miniMaxPruning(this.rootBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
    }

    private int miniMaxPruning(Board board, int currentPry, int alpha, int beta, AIPlayer currentPlayer) {
        if(currentPry == maxPry || board.isBoardComplete()){
            //get evaluation function maxed for correct player
            return evaluateBoardFunction(board, currentPlayer);
        }

        currentPry++;

        if(board.activeTurnPlayer == currentPlayer){
            return getMaxPruning(board, currentPry, alpha, beta, currentPlayer);
        } else{
            return getMinPruning(board, currentPry, alpha, beta, currentPlayer);
        }
    }

    private  int getMaxPruning(Board board, int currentPry, int alpha, int beta, AIPlayer currentPlayer){
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //make move, update boxes
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMaxPruning(newBoard, currentPry, alpha, beta, currentPlayer);

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

    private  int getMinPruning(Board board, int currentPry, int alpha, int beta, AIPlayer currentPlayer){
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //make move update boxes
            newBoard.makeMoveOnBoard(move);

            int evaluation = miniMaxPruning(newBoard, currentPry, alpha, beta, currentPlayer);

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
