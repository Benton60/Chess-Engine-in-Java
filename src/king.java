import java.util.ArrayList;

public class king {
    private int file;
    private int rank;
    private int color;
    private Coord[] possibleMoves = new Coord[8];
    public king(int f, int r, int c){
        rank = r;
        file = f;
        color = c;
        possibleMoves[0] = new Coord(file - 1, rank + 1);
        possibleMoves[1] = new Coord(file, rank + 1);
        possibleMoves[2] = new Coord(file + 1, rank + 1);
        possibleMoves[3] = new Coord(file + 1, rank);
        possibleMoves[4] = new Coord(file + 1, rank - 1);
        possibleMoves[5] = new Coord(file, rank - 1);
        possibleMoves[6] = new Coord(file - 1, rank - 1);
        possibleMoves[7] = new Coord(file - 1, rank);
    }
    public ArrayList<Move> getMoves(int [][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();
        //System.out.println("help");
        for(Coord checkMove: possibleMoves){
            Move move = new Move(new Coord(file, rank), checkMove);
            if(move.isLegal(chessboard, color)){
                //System.out.println(move);
                moves.add(move);
            }
        }
        return moves;
    }
    public ArrayList<Move> getPseudoMoves(int [][] chessboard){
        ArrayList<Move> moves = new ArrayList<>();

        for(Coord checkMove: possibleMoves){
            Move move = new Move(new Coord(file, rank), checkMove);
            if(move.isPseudoLegal(chessboard)){
                moves.add(move);
            }
        }
        return moves;
    }
}
