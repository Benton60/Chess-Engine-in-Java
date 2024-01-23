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
    public String toText(){
        String ret = "";
        switch(X){
            case 0:
                ret += "a";
                break;
            case 1:
                ret += "b";
                break;
            case 2:
                ret += "c";
                break;
            case 3:
                ret += "d";
                break;
            case 4:
                ret += "e";
                break;
            case 5:
                ret += "f";
                break;
            case 6:
                ret += "g";
                break;
            case 7:
                ret += "h";
                break;
            default:
                break;
        }
        ret+=(8-Y);
        return ret;
    }
    public boolean isInBounds(){
        if(X <= 7 && X >= 0 && Y <= 7 && Y >= 0){
            return true;
        }
        return false;
    }
}
