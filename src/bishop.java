import java.util.ArrayList;

public class bishop {
    private int rank;
    private int file;
    ArrayList<Move> inBoundMoves = new ArrayList<>();
    public bishop(int f, int r){
        rank = r;
        file = f;
    }

    public ArrayList<Move> getMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();
        boolean ur = true;
        boolean dr = true;
        boolean ul = true;
        boolean dl = true;
        for(int i = 1; i < 8; i++){
            Move UR = new Move(new Coord(file, rank), new Coord(file + i, rank + i));
            if(UR.isLegal(chessboard) && ur){
                moves.add(UR);
                if(UR.areNotSameColor(chessboard)){
                    ur = false;
                }
            }else{
                ur = false;
            }
            Move DR = new Move(new Coord(file, rank), new Coord(file + i, rank - i));
            if(DR.isLegal(chessboard) && dr){
                moves.add(DR);
                if(DR.areNotSameColor(chessboard)){
                    dr = false;
                }
            }else{
                dr = false;
            }
            Move UL = new Move(new Coord(file, rank), new Coord(file - i, rank + i));
            if(UL.isLegal(chessboard) && ul){
                moves.add(UL);
                if(UL.areNotSameColor(chessboard)){
                    ul = false;
                }
            }else{
                ul = false;
            }
            Move DL = new Move(new Coord(file, rank), new Coord(file - i, rank - i));
            if(DL.isLegal(chessboard) && dl){
                moves.add(UR);
                if(DL.areNotSameColor(chessboard)){
                    dl = false;
                }
            }else{
                dl = false;
            }
        }
        return moves;
    }
}
