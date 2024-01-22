import java.util.ArrayList;

public class knight {
    private int rank;
    private int file;
    public int color;
    private final Coord[] possibleNewMoves = new Coord[8];
    public knight(int f, int r, int c){
        rank = r;
        file = f;
        color = c;
        possibleNewMoves[0] = new Coord(file+1, rank+2);
        possibleNewMoves[1] = new Coord(file-1, rank+2);
        possibleNewMoves[2] = new Coord(file+2, rank+1);
        possibleNewMoves[3] = new Coord(file-2, rank+1);
        possibleNewMoves[4] = new Coord(file-2, rank-1);
        possibleNewMoves[5] = new Coord(file+2, rank-1);
        possibleNewMoves[6] = new Coord(file+1, rank-2);
        possibleNewMoves[7] = new Coord(file-1, rank-2);
    }
    public ArrayList<Move> getMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();

        for(Coord currentCoord: possibleNewMoves){
            Move moveToCheck = new Move(new Coord(file, rank), currentCoord);
            if(moveToCheck.isLegal(chessboard, color)){
                moves.add(moveToCheck);
            }
        }
        return moves;
    }
    public ArrayList<Move> getPseudoMoves(int[][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();

        for(Coord currentCoord: possibleNewMoves){
            Move moveToCheck = new Move(new Coord(file, rank), currentCoord);
            if(moveToCheck.isPseudoLegal(chessboard)){
                moves.add(moveToCheck);
            }
        }
        return moves;
    }

}
