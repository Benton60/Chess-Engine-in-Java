import java.util.ArrayList;

public class pawn {
    private int rank;
    private int file;
    private int color;

    public pawn(int f, int r, int c){
        rank = r;
        file = f;
        color = c;
    }

    public ArrayList<Move> getMoves(int[][] chessboard, Move lastMove){
        ArrayList<Move> moves = new ArrayList<>();
        Coord pawnCoord = new Coord(file, rank);
        Move two = new Move(pawnCoord, new Coord(file, rank - 2 * color));
        if(((rank == 6 && color == 1) || (rank == 1 && color == -1)) && chessboard[rank-2*color][file] == 0 && chessboard[rank - color][file] == 0 && two.isLegal(chessboard, color)){
            moves.add(two);
        }
        Move one = new Move(pawnCoord, new Coord(file, rank - color));
        if(chessboard[rank - color][file] == 0 && one.isLegal(chessboard, color)){
            moves.add(one);
        }
        Move L = new Move(pawnCoord, new Coord((file - 1), rank - color));
        if(L.isLegal(chessboard, color) && chessboard[rank-color][file-1] != 0){
            moves.add(L);
        }
        if(L.isLegal(chessboard, color) && chessboard[rank][file-1] == -100 * color && lastMove.getNewSquare().Y - lastMove.getOriginalSquare().Y == 2 * color && chessboard[lastMove.getNewSquare().Y][lastMove.getNewSquare().X] == -100 * color && lastMove.getNewSquare().X == file - 1){
            moves.add(L);
        }
        Move R = new Move(pawnCoord, new Coord((file + 1), rank - color));
        if(R.isLegal(chessboard, color) && chessboard[rank-color][file+1] != 0){
            moves.add(R);
        }
        if(R.isLegal(chessboard, color) && chessboard[rank][file+1] == -100 * color && lastMove.getNewSquare().Y - lastMove.getOriginalSquare().Y == 2 * color && chessboard[lastMove.getNewSquare().Y][lastMove.getNewSquare().X] == -100 * color && lastMove.getNewSquare().X == file + 1){
            moves.add(R);
        }
        return moves;
    }
}
