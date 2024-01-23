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
        for(Coord checkMove: possibleMoves){
            Move move = new Move(new Coord(file, rank), checkMove);
            if(move.isLegal(chessboard, color)){
                moves.add(move);
            }
        }
        for(Move move: moves){
            System.out.println(move.toText());
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
    public ArrayList<Move> castleMoves(int[][] chessboard, char dir){
        ArrayList<Move> moves = new ArrayList<>();
        if(color == 1) {
            if (dir == 'l' && chessboard[7][3] == 0 && chessboard[7][2] == 0 && chessboard[7][1] == 0){
                if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,7), new Coord(3,7)),chessboard, color)){
                    if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,7), new Coord(2,7)),chessboard, color)){

                        moves.add(new Move('l', color));
                    }
                }
            }
            if (dir == 's' && chessboard[7][6] == 0 && chessboard[7][5] == 0){
                if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,7), new Coord(5,7)),chessboard, color)){
                    if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,7), new Coord(6,7)),chessboard, color)){
                        moves.add(new Move('s', color));
                    }
                }
            }
        }
        if(color == -1) {
            if (dir == 'l' && chessboard[0][3] == 0 && chessboard[0][2] == 0 && chessboard[0][1] == 0){
                if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,0), new Coord(3,0)),chessboard, color)){
                    if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,0), new Coord(2,0)),chessboard, color)){
                        moves.add(new Move('l', color));
                    }
                }
            }
            if (dir == 's' && chessboard[0][6] == 0 && chessboard[0][5] == 0){
                if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,0), new Coord(5,0)),chessboard, color)){
                    if(!new Move().canKingBeCapturedAfterThisMove(new Move(new Coord(4,0), new Coord(6,0)),chessboard, color)){
                        moves.add(new Move('s', color));
                    }
                }
            }
        }
        return moves;
    }
    public static void printChessBoard(int[][] board){
        for (int[] strings : board) {
            for (int string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }
}
