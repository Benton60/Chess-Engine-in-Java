public class Engine implements Runnable {

    private int time;
    private int depth = 0;
    Position masterPosition;
    public Engine(Position pos, int t){
        masterPosition = pos;
        time = t;
    }
    @Override
    public void run() {
        double time = System.nanoTime();
        //System.out.println(generatePositions(masterPosition, 3));
        Move move = getBestMove(masterPosition, 1);
        System.out.println(move.toText());
        mover mov = new mover(move);
        new Thread(mov).start();
        System.out.println("Time: " + (System.nanoTime()-time)/1000000);
    }
    public Move getBestMove(Position pos, int depth){
        Move best = null;
        int eval = 0;
        for(Move current:pos.getAllMoves()){
            Position evenMoreCurrent = pos.clone();
            evenMoreCurrent.makeMove(current);
            evenMoreCurrent.changeColor();
            if(evenMoreCurrent.getEval() > eval || best == null){
                best = current;
            }
        }
        return best;
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
            if(depth ==4){
                System.out.println(move.toText() + "   " + numPositions);
            }
        }
        return numPositions;
    }
}
