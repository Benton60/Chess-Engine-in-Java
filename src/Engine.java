public class Engine implements Runnable {

    public boolean canCastleL;
    public boolean canCastleS;
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
        System.out.println(generatePositions(masterPosition, 2));
        System.out.println("Time: " + (System.nanoTime()-time)/1000000);
    }
    public int generatePositions(Position pos, int depth){
        if(depth == 0){
            return 1;
        }
        int numPositions = 0;
        Position current = pos.clone();
        current.changeColor();
        for(Move move: current.getAllMoves()){
            Position evenMoreCurrent = current.clone();
            evenMoreCurrent.makeMove(move);
            evenMoreCurrent.changeColor();
            numPositions += generatePositions(evenMoreCurrent, depth-1);
            if(depth == 2){
                System.out.println(move.toText() + "   " + numPositions);
            }
        }
        return numPositions;
    }
}
