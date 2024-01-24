public class Test{
    public static int[][] startingChessboard = {
            {-500, -300, -350, -900, -10000, -350, -300, -500},
            {-100, -100, -100, 0, -100, -100, -100, -100},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, -100, 0, 0, 0, 0},
            {100, 100, 100, 100, 100, 100, 100, 100},
            {500, 300, 350, 900, 10000, 350, 300, 500}
    };
    public static void main(String[] args){
        Engine eng = new Engine(new Position(startingChessboard, 1, new Move(new Coord(0,0), new Coord(0,0)), true, true, true, true), 3);
        new Thread(eng).start();
    }
}