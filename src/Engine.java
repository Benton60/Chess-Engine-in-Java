import java.util.ArrayList;
import java.util.Arrays;

public class Engine implements Runnable {

    private int time;
    private int totalDepth = 5;
    Position masterPosition;
    public static int snipped = 0;
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
            double eval = Search(temp, depth, bestEval, bestEval, -1000000, 1000000); // checks the eval for that line
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
        System.out.println("Snipped: " + snipped);
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

    // the betas are used to hold the current positions best move so that it can be passed on to further search calls
    // the alphas are the last positions best values and if the current positions values are worse than last positions values then we need to stop evaluating because we know that making this move is worse
    public double Search(Position pos, int depth, double alphaW, double alphaB, double betaW, double betaB) {
        ArrayList<Move> moves = pos.getAllMoves(); //get all the moves from the position
        if (depth == 0) {
            if(pos.getNumPieces() < 12 && moves.size() <= 20 && pos.col == 1) {
                return SearchAllCaptures(pos, 3, alphaW, alphaB, betaW, betaB); // if we are done evaluating just check the positional eval
            }
            return SearchAllCaptures(pos, 0, alphaW, alphaB, betaW, betaB);
        }

        if (moves.isEmpty()) {                        //if there are no  moves and the king is in check then it is really bad aka checkmate
            if (pos.kingIsInCheck()) {
                int multiplier = 9-(totalDepth-depth);
                return -1000000*pos.col*multiplier;
            }
            return 0;                              //if the king isn't in check then it is a stalemate or draw
        }
        sortMoves(moves, pos.getBoard());
        for (Move move : moves) {
            Position current = pos.clone();
            current.makeMove(move);
            current.changeColor();
            double evaluation = Search(current, depth - 1, betaW, betaB, alphaW, alphaB);
            //evaluation = evaluation + applyPostBiases(move, pos.col); //this applies the biases more the closer the position is to being played
            if(current.col == -1 && evaluation >= alphaB){ // if we are evaluating as black two positions back is black, so we are trying to find the lowest evaluation
                snipped++;
                return evaluation;
            }
            if(current.col == 1 && evaluation <= alphaW){ // if we are evaluating as white then two positions back is also white, so we are trying to find the lowest evaluation
                snipped++;
                return evaluation;
            }
            if(pos.col == -1 && evaluation < betaB){  // this keeps track of the best position for black
                betaB = evaluation;
            }
            if(pos.col == 1 && evaluation > betaW){  // this keeps track of the best position so far for white
                betaW = evaluation;
            }

        }
        if(pos.col == -1){
            return betaB;
        }else{
            return betaW;
        }
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
    public double SearchAllCaptures(Position pos, int depth, double alphaW, double alphaB, double betaW, double betaB) {
        if (depth < 1) {
            return pos.getEval();
        }

        ArrayList<Move> moves = pos.getAllCaptureMoves(); //get all the moves from the position
        if (moves.isEmpty()) {                        //if there are no  moves and the king is in check then it is really bad aka checkmate
            if (pos.kingIsInCheck()) {
                int multiplier = 9-(totalDepth-depth);
                return -1000000*pos.col*multiplier;
            }
            return pos.getEval();                              //if the king isn't in check then it is a stalemate or draw
        }
        for (Move move : moves) {
            Position current = pos.clone();
            current.makeMove(move);
            current.changeColor();
            double evaluation = SearchAllCaptures(current, depth - 1, betaW, betaB, alphaW, alphaB);
            if(current.col == -1 && evaluation >= alphaB){ // if we are evaluating as black two positions back is black, so we are trying to find the lowest evaluation
                return evaluation;
            }
            if(current.col == 1 && evaluation <= alphaW){ // if we are evaluating as white then two positions back is also white, so we are trying to find the lowest evaluation
                return evaluation;
            }
            if(pos.col == -1 && evaluation < betaB){  // this keeps track of the best position for black
                betaB = evaluation;
            }
            if(pos.col == 1 && evaluation > betaW){  // this keeps track of the best position so far for white
                betaW = evaluation;
            }

        }
        if(pos.col == -1){
            return betaB;
        }else{
            return betaW;
        }
    }
    public static double applyPostBiases(Move move, int color){
        double eval = 0;
        if(move.promotion){
            eval = eval + 900 * color;
        }
        if(move.castleSide != 'n'){
            //System.out.println("Castling");
            eval = eval + 500 * color;
        }
        return eval;
    }
    public static void sortMoves(ArrayList<Move> moves, int[][] board){
        for(int i = 0; i < moves.size(); i++){
            if(board[moves.get(i).getNewSquare().Y][moves.get(i).getNewSquare().X] != 0){
                moves.add(0, moves.get(i));
                moves.remove(i+1);
            }
            if(Math.abs(board[moves.get(i).getOriginalSquare().Y][moves.get(i).getOriginalSquare().X]) < Math.abs(board[moves.get(i).getNewSquare().Y][moves.get(i).getNewSquare().X])){
                moves.add(0, moves.get(i));
                moves.remove(i+1);
            }
        }
    }
}
