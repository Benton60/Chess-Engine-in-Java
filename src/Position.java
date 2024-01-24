import java.util.ArrayList;

public class Position{
    private int[][] chessboard = new int[8][8];
    private Move lastMove;
    public int col; // -1 represents black, 1 equals white
    private boolean canCastleL;
    private boolean canCastleS;
    private boolean bcanCastleS;
    private boolean bcanCastleL;

    public Position(int[][] board, int c, Move l, boolean cl, boolean cs, boolean bcl, boolean bcs){
        copyArrays(chessboard, board);
        col = c;
        lastMove = l;
        canCastleL = cl;
        canCastleS = cs;
        bcanCastleS = bcs;
        bcanCastleL = bcl;
    }

    public void runThisPosition() {
        double time = System.nanoTime();
        ArrayList<Move> total = getAllMoves();
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
    public ArrayList<Move> getAllMoves(){
        ArrayList<Move> totalMoves = new ArrayList<>();
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if (col == 1) {
                    switch (chessboard[i][j]) {
                        case 100://white pawn
                            ArrayList<Move> moves = new pawn(j, i, col).getMoves(chessboard, lastMove);
                            for(int t = 0; t < moves.size(); t++){
                                if(moves.get(t).getNewSquare().Y == 0){
                                    moves.set(t, new Move(moves.get(t).getOriginalSquare(), moves.get(t).getNewSquare(), true));
                                }
                            }
                            totalMoves.addAll(moves);
                            break;
                        case 300://white knight
                            totalMoves.addAll(new knight(j, i, col).getMoves(chessboard));
                            break;
                        case 350://white bishop
                            totalMoves.addAll(new bishop(j, i, col).getMoves(chessboard));
                            break;
                        case 500://white rook
                            totalMoves.addAll(new rook(j, i, col).getMoves(chessboard));
                            break;
                        case 900://white queen
                            totalMoves.addAll(new queen(j, i, col).getMoves(chessboard));
                            break;
                        case 10000://white king
                            totalMoves.addAll(new king(j, i, col).getMoves(chessboard));
                            if(canCastleL){
                                totalMoves.addAll(new king(j, i, col).castleMoves(chessboard,'l'));
                            }
                            if(canCastleS){
                                totalMoves.addAll(new king(j, i, col).castleMoves(chessboard,'s'));
                            }
                            break;
                        default:
                            break;
                    }
                    //System.out.println(i + " " + j);
                }
                if(col == -1){
                    switch (chessboard[i][j]) {
                        case -100://white pawn
                            ArrayList<Move> moves = new pawn(j, i, col).getMoves(chessboard, lastMove);
                            for(int t = 0; t < moves.size(); t++){
                                if(moves.get(t).getNewSquare().Y == 7){
                                    moves.set(t, new Move(moves.get(t).getOriginalSquare(), moves.get(t).getNewSquare(), true));
                                }
                            }
                            totalMoves.addAll(moves);
                            break;
                        case -300://white knight
                            totalMoves.addAll(new knight(j, i, col).getMoves(chessboard));
                            break;
                        case -350://white bishop
                            totalMoves.addAll(new bishop(j, i, col).getMoves(chessboard));
                            break;
                        case -500://white rook
                            totalMoves.addAll(new rook(j, i, col).getMoves(chessboard));
                            break;
                        case -900://white queen
                            totalMoves.addAll(new queen(j, i, col).getMoves(chessboard));
                            break;
                        case -10000://white king
                            totalMoves.addAll(new king(j, i, col).getMoves(chessboard));
                            if(bcanCastleL){
                                totalMoves.addAll(new king(j, i, col).castleMoves(chessboard,'l'));
                            }
                            if(bcanCastleS){
                                totalMoves.addAll(new king(j, i, col).castleMoves(chessboard,'s'));
                            }
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
    public double getEval(){
        double eval = 0;
        for(int i = 0; i < chessboard.length; i++){
            for(int j = 0; j < chessboard.length; j++){
                eval += chessboard[i][j]; // adds up all piece values
            }
        }
        return eval; //if the color is black it reverses the eval
    }
    public int[][] getBoard(){
        return chessboard;
    }
    public void makeMove(Move move){
        String ret = move.makeMove(chessboard);
        switch (ret){
            case "l":
                canCastleL = false;
                break;
            case "s":
                canCastleS = false;
            case "b":
                canCastleL = false;
                canCastleS = false;
                break;
            default:
                break;
        }
        lastMove = move;
    }
    public Position clone(){
        int[][] newBoard = new int[8][8];
        copyArrays(newBoard, chessboard);
        Move newMove = new Move(new Coord(lastMove.getOriginalSquare().X, lastMove.getOriginalSquare().Y), new Coord(lastMove.getNewSquare().X, lastMove.getNewSquare().Y));
        return new Position(newBoard, col, newMove, canCastleL, canCastleS, bcanCastleL, bcanCastleS);
    }
    public void changeColor(){
        if(col == -1){
            col = 1;
        }else{
            col = -1;
        }
    }
    public boolean kingIsInCheck(){
        Move move = new Move(new Coord(0,0), new Coord(0,0));
        return move.canKingBeCapturedAfterThisMove(move,chessboard, col);
    }
}