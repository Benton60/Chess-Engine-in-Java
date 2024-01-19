public class Coord {
    public int X;
    public int Y;
    public Coord(int xl, int yl){
        X = xl;
        Y = yl;
    }
    public Coord(){
        X = 0;
        Y = 0;
    }
    public String toString(){
        return "(" + X + "," + Y + ")";
    }
}
