import java.util.ArrayList;
import java.util.Arrays;

public class Engine implements Runnable {

    private int time;
    private int totalDepth = 2;
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
        Move move = getBestMove(masterPosition, totalDepth);
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
            temp.changeColor();
            double eval = Search(temp, depth, -10000000, 1000000, 0, 0); // checks the eval for that line
            System.out.println(move.toText() + "   " + eval);
            if(checkLastPositions(temp)){// checks whether the position is contained in the recent positions array from the api class.
                eval -= 250;
            }
            if(eval > bestEval){
                bestMove = move; // if the eval is better than the current line choose this one
                bestEval = eval;
            }
        }
        System.out.println(bestEval);
        return bestMove;
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
    public static boolean checkLastPositions(Position pos){
        for(Position current: API.lastPositions){
            if(areEqual(current.getBoard(), pos.getBoard())){
                return true;
            }
        }
        return false;
    }
    public double Search(Position pos, int depth, double alphaW, double alphaB, double betaW, double betaB) {
        if (depth == 0) {
            double eval = pos.getEval();
            if(pos.getNumPieces() < 12) {
                return SearchAllCaptures(pos, 5); //SearchAllCaptures(pos, bestPositionSoFarForWhite, bestPositionSoFarForBlack, 0) ; // if we are done evaluating just check the positional eval
            }
            return eval;
        }

        ArrayList<Move> moves = pos.getAllMoves(); //get all the moves from the position

        if (moves.isEmpty()) {                        //if there are no  moves and the king is in check then it is really bad aka checkmate
            if (pos.kingIsInCheck()) {
                int multiplier = 9-(totalDepth-depth);
                return -1000000*pos.col*multiplier;
            }
            return pos.getEval();                              //if the king isn't in check then it is a stalemate or draw
        }
        double bestEval = -1000000 * pos.col;
        for (Move move : moves) {
            Position current = pos.clone();
            current.makeMove(move);
            current.changeColor();
            double evaluation = Search(current, depth - 1, betaW, betaB, alphaW, alphaB);
            if (pos.col == -1 && evaluation < bestEval) {
                bestEval = evaluation;
            }
            if (pos.col == 1 && evaluation > bestEval) {
                bestEval = evaluation;
            }
            if(pos.col == 1 && bestEval >= alphaB){
                return bestEval;
            }
            if(pos.col == 1 && bestEval <= alphaW){
                return bestEval;
            }
        }
        return bestEval;
    }
    public static boolean areEqual(int[][] b, int[][] a){
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public double SearchAllCaptures(Position pos, int depth) {
        if (depth == 0) {

            return pos.getEval(); // if we are done evaluating just check the positional eval
        }
        ArrayList<Move> moves = pos.getAllCaptureMoves(); //get all the moves from the position
        if (moves.isEmpty()) {                        //if there are no  moves and the king is in check then it is really bad aka checkmate
            if (pos.kingIsInCheck()) {
                int multiplier = 9-(totalDepth-depth);
                return -1000000*pos.col*multiplier;
            }
            return pos.getEval();
        }
        double bestEval = -1000000 * pos.col;
        for (Move move : moves) {
            Position current = pos.clone();
            current.makeMove(move);
            current.changeColor();
            double evaluation = SearchAllCaptures(current, depth - 1);
            if (pos.col == -1 && evaluation < bestEval) {
                bestEval = evaluation;
            }
            if (pos.col == 1 && evaluation > bestEval) {
                bestEval = evaluation;
            }

        }
        return bestEval;
    }
}
