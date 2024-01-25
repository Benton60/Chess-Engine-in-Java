import java.util.ArrayList;

public class Engine implements Runnable {

    private int time;
    private int depth = 4;
    Position masterPosition;
    public Engine(Position pos, int t){
        masterPosition = pos;
        time = t;
    }
    @Override
    public void run() {
        double time = System.nanoTime();
        //System.out.println(generatePositions(masterPosition, 3));
        //System.out.println(Search(masterPosition, depth));
        Move move = getBestMove(masterPosition, depth);
        System.out.println(move.toText());
        move.makeMove(masterPosition.getBoard());
        printChessBoard(masterPosition.getBoard());

        mover mov = new mover(move);
        new Thread(mov).start();
        System.out.println("Time: " + (System.nanoTime()-time)/1000000);


    }
    public Move getBestMove(Position pos, int depth){
        Move bestMove = new Move(new Coord(0,0), new Coord(0,0));
        double bestEval = -1000000000;
        for(Move move: pos.getAllMoves()){ // looks at all whites possible moves
            Position temp = pos.clone();   // sets up a testing board to see if they are good moves
            move.makeMove(temp.getBoard()); // makes the moves on the testing board
            pos.changeColor();
            double eval = Search(temp, depth, 1000000000, -100000000); // checks the eval for that line
            System.out.println(move.toText() + "   " + eval);
            if(eval > bestEval){
                bestMove = move; // if the eval is better than the current line choose this one
                bestEval = eval;
            }
        }
        System.out.println(bestEval);
        return bestMove;
    }

    public double Search(Position pos, int depth, double bestPositionSoFarForWhite, double bestPositionSoFarForBlack) {
        if (depth == 0) {
            return pos.getEval() ; // if we are done evaluating just check the positional eval
        }
        ArrayList<Move> moves = pos.getAllMoves(); //get all the moves from the position
        if (moves.isEmpty()) {                        //if there are no  moves and the king is in check then it is really bad aka checkmate
            if (pos.kingIsInCheck()) {
                return -100000000;
            }
            return 0;                              //if the king isn't in check then it is a stalemate or draw
        }
        double bestEval = -100000000* pos.col;
        for(Move move: moves){
            Position current = pos.clone();
            current.makeMove(move);
            current.changeColor();
            double evaluation = Search(current, depth-1, bestPositionSoFarForWhite, bestPositionSoFarForBlack);
            if(current.col == 1 && bestPositionSoFarForWhite < evaluation){
                return evaluation;
            }else{
                bestPositionSoFarForWhite = evaluation;
            }
            if(current.col == -1 && bestPositionSoFarForBlack > evaluation){
                return evaluation;
            }else{
                bestPositionSoFarForBlack = evaluation;
            }
            if(current.col == -1 && evaluation > bestEval){
                bestEval = evaluation;
            }
            if(current.col == 1 && evaluation < bestEval){
                bestEval = evaluation;
            }
        }
        return bestEval;
    }
    public int generatePositions(Position pos, int depth){
        if(depth == 0){
            return 1;
        }
        int numPositions = 0;
        Position current = pos.clone();
        //current.changeColor();
        for(Move move: current.getAllMoves()){
            Position evenMoreCurrent = current.clone();
            evenMoreCurrent.makeMove(move);
            evenMoreCurrent.changeColor();
            numPositions += generatePositions(evenMoreCurrent, depth-1);
            if(depth == 4){
                System.out.println(move.toText() + "   " + numPositions);
            }
        }
        return numPositions;
    }
    public static void printChessBoard(int[][] board){
        for (int[] strings : board) {
            for (int string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }
    public static double SearchAllCaptures(Position pos, double bestPositionSoFarForWhite, double bestPositionSoFarForBlack, int count){
        ArrayList<Move> moves = pos.getAllCaptureMoves(); //get all the moves from the position
        if(moves.isEmpty() || count == 5){
            return pos.getEval();
        }
        double bestEval = -100000000* pos.col;
        for(Move move: moves){
            Position current = pos.clone();
            current.makeMove(move);
            current.changeColor();
            double evaluation = SearchAllCaptures(current, bestPositionSoFarForWhite, bestPositionSoFarForBlack, count+1);
            if(current.col == 1 && bestPositionSoFarForWhite < evaluation){
                return evaluation;
            }else{
                bestPositionSoFarForWhite = evaluation;
            }
            if(current.col == -1 && bestPositionSoFarForBlack > evaluation){
                return evaluation;
            }else{
                bestPositionSoFarForBlack = evaluation;
            }
            if(current.col == -1 && evaluation > bestEval){
                bestEval = evaluation;
            }
            if(current.col == 1 && evaluation < bestEval){
                bestEval = evaluation;
            }
        }
        return bestEval;
    }
}
