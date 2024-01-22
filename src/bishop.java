import java.util.ArrayList;

public class bishop {
    private int rank;
    private int file;
    private int color;
    public bishop(int f, int r, int c){
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
        for(int i = 1; i < 8; i++){
            Move UR = new Move(new Coord(file, rank), new Coord(file + i, rank + i));
            if(UR.isLegal(chessboard, color) && ur){
                moves.add(UR);
                System.out.println(UR.outOrBlocked(chessboard));
                if(UR.areNotSameColor(chessboard)){
                    ur = false;
                }
            }else{
                if(UR.outOrBlocked(chessboard)){
                    ur = false;
                }
            }
            Move DR = new Move(new Coord(file, rank), new Coord(file + i, rank - i));
            if(DR.isLegal(chessboard, color) && dr){
                moves.add(DR);
                if(DR.areNotSameColor(chessboard)){
                    dr = false;
                }
            }else{
                if(DR.outOrBlocked(chessboard)){
                    dr = false;
                }
            }
            Move UL = new Move(new Coord(file, rank), new Coord(file - i, rank + i));
            if(UL.isLegal(chessboard, color) && ul){
                moves.add(UL);
                if(UL.areNotSameColor(chessboard)){
                    ul = false;
                }
            }else{
                if(UL.outOrBlocked(chessboard)){
                    ul = false;
                }

            }
            Move DL = new Move(new Coord(file, rank), new Coord(file - i, rank - i));
            if(DL.isLegal(chessboard, color) && dl){
                moves.add(DL);
                if(DL.areNotSameColor(chessboard)){
                    dl = false;
                }
            }else{
                if(DL.outOrBlocked(chessboard)){
                    dl = false;
                }
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
        }
        return moves;
    }

}
