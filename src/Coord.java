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
    public boolean isInBounds(){
        if(X <= 7 && X >= 0 && Y <= 7 && Y >= 0){
            return true;
        }
        return false;
    }
}
