public class Move {
    private Coord oldCoord;
    private Coord newCoord;
    private char castleSide = 'n';
    private int color;
    public Move(Coord beginning, Coord ending){
        oldCoord = beginning;
        newCoord = ending;
    }
    public Move(){}
    public Move(char side, int col){
        castleSide = side;
        color = col;
    }
    public boolean isLegal(int[][] chessboard, int color){
        if(newCoord.X > 7 || newCoord.X < 0 || newCoord.Y > 7 || newCoord.Y < 0){  // checks if the move falls outside the array aka board
            //System.out.println("outside");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] > 0 && chessboard[oldCoord.Y][oldCoord.X] > 0){ // checks whether both pieces are white
            //System.out.println("both are white");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] < 0 && chessboard[oldCoord.Y][oldCoord.X] < 0){ // checks whether both pieces are black
            //System.out.println("both are black");
            return false;
        }
        if(canKingBeCapturedAfterThisMove(this, chessboard, color)){
            return false;
        }

        return true;
    }
    public boolean isPseudoLegal(int[][] chessboard){
        if(newCoord.X > 7 || newCoord.X < 0 || newCoord.Y > 7 || newCoord.Y < 0){  // checks if the move falls outside the array aka board
            //System.out.println("outside");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] > 0 && chessboard[oldCoord.Y][oldCoord.X] > 0){ // checks whether both pieces are white
            //System.out.println("both are white");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] < 0 && chessboard[oldCoord.Y][oldCoord.X] < 0){ // checks whether both pieces are black
            //System.out.println("both are black");
            return false;
        }

        return true;
    }
    public Coord getOriginalSquare(){
        return oldCoord;
    }
    public Coord getNewSquare(){
        return newCoord;
    }
    public String toString(){
        if(castleSide != 'n' && color == -1){
            return "Black to the " + castleSide;
        }
        if(castleSide != 'n' && color == 1){
            return "White to the " + castleSide;
        }
        return oldCoord.toString() + " : " + newCoord.toString();
    }
    public boolean areNotSameColor(int[][] chessboard){
        if(chessboard[newCoord.Y][newCoord.X] > 0 && chessboard[oldCoord.Y][oldCoord.X] > 0){ // checks whether both pieces are white
            //System.out.println("both are white");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] < 0 && chessboard[oldCoord.Y][oldCoord.X] < 0){ // checks whether both pieces are black
            //System.out.println("both are black");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] == 0){
            return false;
        }
        return true;
    }
    public boolean canKingBeCapturedAfterThisMove(Move move, int[][] ChessBoard, int color){
        int[][] newChessBoard = new int[8][8];
        copyArrays(newChessBoard, ChessBoard);
        newChessBoard[move.getNewSquare().Y][move.getNewSquare().X] = newChessBoard[move.getOriginalSquare().Y][move.getOriginalSquare().X];
        newChessBoard[move.getOriginalSquare().Y][move.getOriginalSquare().X] = 0;
        Coord kingPos = getKingPos(newChessBoard, color);
        try {
            if (newChessBoard[kingPos.Y + color][kingPos.X + color] == 100 * color) {
                return true;
            }
        }catch(Exception e){}
        try {
            if (newChessBoard[kingPos.Y + color][kingPos.X - color] == 100 * color) {
                return true;
            }
        }catch(Exception e){}
        for(Move current: new knight(kingPos.X, kingPos.Y, color).getPseudoMoves(newChessBoard)){
            if(newChessBoard[current.getNewSquare().Y][current.getNewSquare().X] == -300*color){
                return true;
            }
        }
        for(Move current: new bishop(kingPos.X, kingPos.Y, color).getPseudoMoves(newChessBoard)){
            if(newChessBoard[current.getNewSquare().Y][current.getNewSquare().X] == -350*color){
                return true;
            }
        }
        for(Move current: new rook(kingPos.X, kingPos.Y, color).getPseudoMoves(newChessBoard)){
            if(newChessBoard[current.getNewSquare().Y][current.getNewSquare().X] == -500*color){
                return true;
            }
        }
        for(Move current: new queen(kingPos.X, kingPos.Y, color).getPseudoMoves(newChessBoard)){
            if(newChessBoard[current.getNewSquare().Y][current.getNewSquare().X] == -900*color){
                return true;
            }
        }
        for(Move current: new king(kingPos.X, kingPos.Y, color).getPseudoMoves(newChessBoard)){
            if(newChessBoard[current.getNewSquare().Y][current.getNewSquare().X] == -10000*color){
                return true;
            }
        }
        return false;
    }
    public Coord getKingPos(int[][] chessboard, int color){
        for(int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                if(chessboard[i][j] == 10000*color){
                    return new Coord(j,i);
                }
            }
        }
        return new Coord(0,0);
    }
    public static void copyArrays(int[][] one, int[][] two){
        for(int i = 0; i < two.length; i++){
            for(int j = 0; j < two[i].length; j++) {
                one[i][j] = two[i][j];
            }
        }
    }
    public boolean outOrBlocked(int[][] chessboard){
        if(newCoord.X > 7 || newCoord.X < 0 || newCoord.Y > 7 || newCoord.Y < 0){  // checks if the move falls outside the array aka board
            //System.out.println("outside");
            return true;
        }
        if(chessboard[newCoord.Y][newCoord.X] != 0){ // checks whether both pieces are white
            //System.out.println("both are white");
            return true;
        }
        return false;
    }
    public void makeMove(int[][] chessboard, int color){
        if(color == 1 && castleSide == 'l'){
            chessboard[7][3] = 500;
            chessboard[7][0] = 0;
            chessboard[7][2] = 10000;
            chessboard[7][4] = 0;
        }
        if(color == 1 && castleSide == 's'){
            chessboard[7][5] = 500;
            chessboard[7][7] = 0;
            chessboard[7][6] = 10000;
            chessboard[7][4] = 0;
        }
        if(color == -1 && castleSide == 'l'){
            chessboard[0][3] = 500;
            chessboard[0][0] = 0;
            chessboard[0][2] = 10000;
            chessboard[0][4] = 0;
        }
        if(color == -1 && castleSide == 's'){
            chessboard[0][5] = 500;
            chessboard[0][7] = 0;
            chessboard[0][6] = 10000;
            chessboard[0][4] = 0;
        }
        if(castleSide == 'n'){
            chessboard[newCoord.Y][oldCoord.X] = chessboard[oldCoord.Y][oldCoord.X];
            chessboard[oldCoord.Y][oldCoord.X] = 0;
        }
    }
}
