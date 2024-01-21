import java.util.ArrayList;

public class queen {
    private int rank;
    private int file;
    private int color;
    ArrayList<Move> inBoundMoves = new ArrayList<>();
    public queen(int f, int r, int c){
        rank = r;
        file = f;
        color = c;
    }

    public ArrayList<Move> getMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();
        boolean ur = true;
        boolean dr = true;
        boolean ul = true;
        boolean dl = true;
        boolean r = true;
        boolean l = true;
        boolean u = true;
        boolean d = true;
        for(int i = 1; i < 8; i++){
            Move UR = new Move(new Coord(file, rank), new Coord(file + i, rank + i));
            if(UR.isLegal(chessboard, color) && ur){
                moves.add(UR);
                if(UR.areNotSameColor(chessboard)){
                    ur = false;
                }
            }else{
                ur = false;
            }
            Move DR = new Move(new Coord(file, rank), new Coord(file + i, rank - i));
            if(DR.isLegal(chessboard, color) && dr){
                moves.add(DR);
                if(DR.areNotSameColor(chessboard)){
                    dr = false;
                }
            }else{
                dr = false;
            }
            Move UL = new Move(new Coord(file, rank), new Coord(file - i, rank + i));
            if(UL.isLegal(chessboard, color) && ul){
                moves.add(UL);
                if(UL.areNotSameColor(chessboard)){
                    ul = false;
                }
            }else{
                ul = false;
            }
            Move DL = new Move(new Coord(file, rank), new Coord(file - i, rank - i));
            if(DL.isLegal(chessboard, color) && dl){
                moves.add(DL);
                if(DL.areNotSameColor(chessboard)){
                    dl = false;
                }
            }else{
                dl = false;
            }




            Move R = new Move(new Coord(file, rank), new Coord(file + i, rank));
            if(R.isLegal(chessboard, color) && r){
                moves.add(R);
                if(R.areNotSameColor(chessboard)){
                    r = false;
                }
            }else{
                r = false;
            }
            Move L = new Move(new Coord(file, rank), new Coord(file - i, rank));
            if(L.isLegal(chessboard, color) && l){
                moves.add(L);
                if(L.areNotSameColor(chessboard)){
                    l = false;
                }
            }else{
                l = false;
            }
            Move U = new Move(new Coord(file, rank), new Coord(file, rank + i));
            if(U.isLegal(chessboard, color) && u){
                moves.add(U);
                if(U.areNotSameColor(chessboard)){
                    u = false;
                }
            }else{
                u = false;
            }
            Move D = new Move(new Coord(file, rank), new Coord(file, rank - i));
            if(D.isLegal(chessboard, color) && d){
                moves.add(D);
                if(D.areNotSameColor(chessboard)){
                    d = false;
                }
            }else{
                d = false;
            }
        }
        return moves;
    }
    public ArrayList<Move> getPseudoMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();
        boolean ur = true;
        boolean dr = true;
        boolean ul = true;
        boolean dl = true;
        boolean r = true;
        boolean l = true;
        boolean u = true;
        boolean d = true;
        for(int i = 1; i < 8; i++){
            Move UR = new Move(new Coord(file, rank), new Coord(file + i, rank + i));
            if(UR.isPseudoLegal(chessboard) && ur){
                moves.add(UR);
                if(UR.areNotSameColor(chessboard)){
                    ur = false;
                }
            }else{
                ur = false;
            }
            Move DR = new Move(new Coord(file, rank), new Coord(file + i, rank - i));
            if(DR.isPseudoLegal(chessboard) && dr){
                moves.add(DR);
                if(DR.areNotSameColor(chessboard)){
                    dr = false;
                }
            }else{
                dr = false;
            }
            Move UL = new Move(new Coord(file, rank), new Coord(file - i, rank + i));
            if(UL.isPseudoLegal(chessboard) && ul){
                moves.add(UL);
                if(UL.areNotSameColor(chessboard)){
                    ul = false;
                }
            }else{
                ul = false;
            }
            Move DL = new Move(new Coord(file, rank), new Coord(file - i, rank - i));
            if(DL.isPseudoLegal(chessboard) && dl){
                moves.add(DL);
                if(DL.areNotSameColor(chessboard)){
                    dl = false;
                }
            }else{
                dl = false;
            }




            Move R = new Move(new Coord(file, rank), new Coord(file + i, rank));
            if(R.isPseudoLegal(chessboard) && r){
                moves.add(R);
                if(R.areNotSameColor(chessboard)){
                    r = false;
                }
            }else{
                r = false;
            }
            Move L = new Move(new Coord(file, rank), new Coord(file - i, rank));
            if(L.isPseudoLegal(chessboard) && l){
                moves.add(L);
                if(L.areNotSameColor(chessboard)){
                    l = false;
                }
            }else{
                l = false;
            }
            Move U = new Move(new Coord(file, rank), new Coord(file, rank + i));
            if(U.isPseudoLegal(chessboard) && u){
                moves.add(U);
                if(U.areNotSameColor(chessboard)){
                    u = false;
                }
            }else{
                u = false;
            }
            Move D = new Move(new Coord(file, rank), new Coord(file, rank - i));
            if(D.isPseudoLegal(chessboard) && d){
                moves.add(D);
                if(D.areNotSameColor(chessboard)){
                    d = false;
                }
            }else{
                d = false;
            }
        }
        return moves;
    }
}
