import java.util.ArrayList;

public class rook {
    private int rank;
    private int file;
    private int color;
    public rook(int f, int r, int c){
        rank = r;
        file = f;
        color = c;
    }

    public ArrayList<Move> getMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();
        boolean u = true;
        boolean d = true;
        boolean l = true;
        boolean r = true;
        for(int i = 1; i < 8; i++){
            Move R = new Move(new Coord(file, rank), new Coord(file + i, rank));
            if(R.isLegal(chessboard, color) && r){
                moves.add(R);
                if(R.areNotSameColor(chessboard)){
                    r = false;
                }
            }else{
                if(R.outOrBlocked(chessboard)){
                    r = false;
                }
            }
            Move L = new Move(new Coord(file, rank), new Coord(file - i, rank));
            if(L.isLegal(chessboard, color) && l){
                moves.add(L);
                if(L.areNotSameColor(chessboard)){
                    l = false;
                }
            }else{
                if(L.outOrBlocked(chessboard)){
                    l = false;
                }
            }
            Move U = new Move(new Coord(file, rank), new Coord(file, rank + i));
            if(U.isLegal(chessboard, color) && u){
                moves.add(U);
                if(U.areNotSameColor(chessboard)){
                    u = false;
                }
            }else{
                if(R.outOrBlocked(chessboard)){
                    u = false;
                }
            }
            Move D = new Move(new Coord(file, rank), new Coord(file, rank - i));
            if(D.isLegal(chessboard, color) && d){
                moves.add(D);
                if(D.areNotSameColor(chessboard)){
                    d = false;
                }
            }else{
                if(D.outOrBlocked(chessboard)){
                    d = false;
                }
            }
        }
        return moves;
    }
    public ArrayList<Move> getPseudoMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();
        boolean u = true;
        boolean d = true;
        boolean l = true;
        boolean r = true;
        for(int i = 1; i < 8; i++){
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
