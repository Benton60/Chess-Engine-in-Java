public class Test{

    public static void main(String[] args){
        mover move = new mover(new Move(new Coord(0,0), new Coord(7,7)));
        new Thread(move).start();
    }
}