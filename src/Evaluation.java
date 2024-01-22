import java.util.ArrayList;

public class Evaluation implements Runnable{
    private static final int[][] chessboard = new int[8][8];
    public int col;
    public Evaluation(int[][] board, int c){
        copyArrays(chessboard, board);
        col = c;
    }
    @Override
    public void run() {
        for(Move mov: getAllMoves(col)){
            chessboard[mov.getNewSquare().Y][mov.getNewSquare().X] = 1;
            System.out.println(mov);
        }
        for (int[] ints : chessboard) {
            for (int in: ints) {
                System.out.print(in + "  ");
            }
            System.out.println();
        }
    }
    public static void copyArrays(int[][] one, int[][] two){
        for(int i = 0; i < two.length; i++){
            for(int j = 0; j < two[i].length; j++) {
                one[i][j] = two[i][j];
            }
        }
    }
    public ArrayList<Move> getAllMoves(int c){
        ArrayList<Move> totalMoves = new ArrayList<>();
        if(c == 1) {
            for (int i = 0; i < chessboard.length; i++) {
                for (int j = 0; j < chessboard[i].length; j++) {
                    switch (chessboard[i][j]) {
                        case 100://white pawn

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
                            totalMoves.addAll(new king(j,i,c).getMoves(chessboard));
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