import java.util.ArrayList;

public class Evaluation implements Runnable{
    private int[][] chessboard = new int[8][8];
    private Move last;
    public int col; // -1 represents black, 1 equals white
    public Evaluation(int[][] board, int c, Move l){
        copyArrays(chessboard, board);
        col = c;
        last = l;
    }
    @Override
    public void run() {
        double time = System.nanoTime();
        ArrayList<Move> total = getAllMoves(col, last, true, true);
        System.out.println(total.size() + " moves");
        for(Move mov: total){
            //chessboard[mov.getNewSquare().Y][mov.getNewSquare().X] = 1;
            System.out.println(mov);
        }
        for (int[] ints : chessboard) {
            for (int in: ints) {
                System.out.print(in + "  ");
            }
            System.out.println();
        }
        System.out.println("time: " + (System.nanoTime()-time)/1000000000);
    }
    public static void copyArrays(int[][] one, int[][] two){
        for(int i = 0; i < two.length; i++){
            for(int j = 0; j < two[i].length; j++) {
                one[i][j] = two[i][j];
            }
        }
    }
    public ArrayList<Move> getAllMoves(int c, Move lastMove, boolean canCastleL, boolean canCastleS){
        ArrayList<Move> totalMoves = new ArrayList<>();
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (c == 1) {
                    switch (chessboard[i][j]) {
                        case 100://white pawn
                            totalMoves.addAll(new pawn(j, i, c).getMoves(chessboard, lastMove));
                            break;
                        case 300://white knight
                            totalMoves.addAll(new knight(j, i, c).getMoves(chessboard));
                            break;
                        case 350://white bishop
                            totalMoves.addAll(new bishop(j, i, c).getMoves(chessboard));
                            break;
                        case 500://white rook
                            totalMoves.addAll(new rook(j, i, c).getMoves(chessboard));
                            break;
                        case 900://white queen
                            totalMoves.addAll(new queen(j, i, c).getMoves(chessboard));
                            break;
                        case 10000://white king
                            totalMoves.addAll(new king(j, i, c).getMoves(chessboard));
                            if(canCastleL){
                                totalMoves.addAll(new king(j, i, c).castleMoves(chessboard,'l'));
                            }
                            if(canCastleS){
                                totalMoves.addAll(new king(j, i, c).castleMoves(chessboard,'s'));
                            }
                            break;
                        default:
                            break;
                    }
                    //System.out.println(i + " " + j);
                }
                if(c == -1){
                    switch (chessboard[i][j]) {
                        case -100://white pawn
                            totalMoves.addAll(new pawn(j, i, c).getMoves(chessboard, lastMove));
                            break;
                        case -300://white knight
                            totalMoves.addAll(new knight(j, i, c).getMoves(chessboard));
                            break;
                        case -350://white bishop
                            totalMoves.addAll(new bishop(j, i, c).getMoves(chessboard));
                            break;
                        case -500://white rook
                            totalMoves.addAll(new rook(j, i, c).getMoves(chessboard));
                            break;
                        case -900://white queen
                            totalMoves.addAll(new queen(j, i, c).getMoves(chessboard));
                            break;
                        case -10000://white king
                            totalMoves.addAll(new king(j, i, c).getMoves(chessboard));
                            break;
                        default:
                            break;
                    }
                    //System.out.println(i + " " + j);
                }
            }
        }
        return totalMoves;
    }
    public int getSquare(Coord c){
        return chessboard[c.X][c.Y];
    }
}