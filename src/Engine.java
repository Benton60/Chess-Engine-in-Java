import java.util.ArrayList;

public class Engine implements Runnable{
    private static final int[][] chessboard = new int[8][8];
    public Engine(int[][] board){
        copyArrays(chessboard, board);
    }
    @Override
    public void run() {
        for(Move curr: getAllMoves()){
            System.out.println(curr);
        }
        for(Move mov: getAllMoves()){
            chessboard[mov.getNewSquare().Y][mov.getNewSquare().X] = 1;
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
    public ArrayList<Move> getAllMoves(){
        ArrayList<Move> totalMoves = new ArrayList<>();
        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard[i].length; j++){
                switch(chessboard[i][j]){
                    case 100://white pawn

                        break;
                    case 300://white knight
                        totalMoves.addAll(new knight(j, i).getMoves(chessboard));
                        break;
                    case 350://white bishop
                        totalMoves.addAll(new bishop(j, i).getMoves(chessboard));
                        break;
                    case 500://white rook
                        break;
                    case 900://white queen
                        break;
                    case 10000://white king
                        break;
                    default:
                        break;
                }
            }
        }
        return totalMoves;
    }
    public int getSquare(Coord c){
        return chessboard[c.X][c.Y];
    }
}