package MinMax;

import Board.*;

public class MiniMax {
    public Board rootBoard;
    int maxPry;

    public MiniMax(Board rootBoard, int maxPry) {
        this.rootBoard = rootBoard;
        this.maxPry = maxPry;
    }

    public void makeAIMove(){
        if(maxPry < 0){
            throw new IllegalArgumentException("maxPry must be >0");
        }
        miniMax(this.rootBoard, 0);
    }

    public int miniMax(Board board, int currentPry) {
        if(currentPry == maxPry || board.isBoardComplete()){
            return board.evaluateBoardFunction();
        }

        currentPry++;

        if(board.isPlayer1Turn){
            return getMax(board, currentPry);
        } else{
            return getMin(board, currentPry);
        }
    }

    public int getMax(Board board, int currentPry){
        int maxEvaluation = Integer.MIN_VALUE;
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //swap who's turn it is on new rootBoard
            newBoard.isPlayer1Turn = !board.isPlayer1Turn;
            //make move
            newBoard.makeMove(move);

            int evaluation = miniMax(newBoard, currentPry);

            if(evaluation > maxEvaluation){
                maxEvaluation = evaluation;
                bestMove = move;
            }
        }
        board.makeMove(bestMove);
        return maxEvaluation;
    }

    public int getMin(Board board, int currentPry){
        int minEvaluation = Integer.MAX_VALUE;
        Move bestMove = null;

        for(Move move : board.getAllPossibleMoves()){
            //make deep copy of rootBoard
            Board newBoard = board.deepCopyBoard();
            //swap who's turn it is on new rootBoard
            newBoard.isPlayer1Turn = !board.isPlayer1Turn;
            //make move
            newBoard.makeMove(move);

            int evaluation = miniMax(newBoard, currentPry);

            if(evaluation < minEvaluation){
                minEvaluation = evaluation;
                bestMove = move;
            }
        }
        board.makeMove(bestMove);
        return minEvaluation;
    }


}
