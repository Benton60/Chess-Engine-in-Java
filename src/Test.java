public class Test{
    public static int[][] startingChessboard = {
            {-500, -300, -350, -900, -10000, -350, -300, -500},
            {-100, -100, -100, -100, -100, -100, -100, -100},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {100, 100, 100, 100, 100, 100, 100, 100},
            {500, 300, 350, 900, 10000, 350, 300, 500}
    };
    /*
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, -10000, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {500, 0, 0, 0, 100000, 0, 0, 0},
            };
            */
    public static void main(String[] args){
        startAPI();
        Position pos = new Position(startingChessboard, 1, new Move(new Coord(0,0), new Coord(0,0)), true, true, true, true);
        System.out.println(pos.getAllCaptureMoves());
        Engine eng = new Engine(pos, 3);
        new Thread(eng).start();
    }
    public static void startAPI(){
        API.lastPositions[0] = new Position();
        API.lastPositions[1] = new Position();
        API.lastPositions[2] = new Position();
        API.lastPositions[3] = new Position();
        API.lastPositions[4] = new Position();
    }
}