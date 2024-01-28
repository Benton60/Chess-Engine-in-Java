public class Move {
    private Coord oldCoord;
    private Coord newCoord;
    public char castleSide = 'n';
    public int color;
    public boolean promotion = false;
    public Move(Coord beginning, Coord ending){
        oldCoord = beginning;
        newCoord = ending;
    }
    public Move(Coord beginning, Coord ending, boolean prom){
        oldCoord = beginning;
        newCoord = ending;
        promotion = prom;
    }
    public Move(){
        oldCoord = new Coord(0,0);
        newCoord = new Coord(0,0);
    }
    public Move(char side, int col){
        castleSide = side;
        color = col;
        oldCoord = new Coord(0,0);
        newCoord = new Coord(0,0);
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
    public String toText(){
        if(castleSide != 'n' && color == -1){
            return "Black to the " + castleSide;
        }
        if(castleSide != 'n' && color == 1){
            return "White to the " + castleSide;
        }
        return oldCoord.toText() + ":" + newCoord.toText();
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
            if (newChessBoard[kingPos.Y - color][kingPos.X + color] == -100 * color) {
                return true;
            }
        }catch(Exception e){
            //System.out.println(e);
        }
        try {
            if (newChessBoard[kingPos.Y - color][kingPos.X - color] == -100 * color) {
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
    public String makeMove(int[][] chessboard){
        String ret = "n";
        if(promotion){
            chessboard[newCoord.Y][oldCoord.X] = 900 * color;
            return ret;
        }
        if(color == 1 && castleSide == 'l'){
            chessboard[7][3] = 500;
            chessboard[7][0] = 0;
            chessboard[7][2] = 10000;
            chessboard[7][4] = 0;
            return ret;
        }
        if(color == 1 && castleSide == 's'){
            chessboard[7][5] = 500;
            chessboard[7][7] = 0;
            chessboard[7][6] = 10000;
            chessboard[7][4] = 0;
            return ret;
        }
        if(color == -1 && castleSide == 'l'){
            chessboard[0][3] = 500;
            chessboard[0][0] = 0;
            chessboard[0][2] = 10000;
            chessboard[0][4] = 0;
            return ret;
        }
        if(color == -1 && castleSide == 's'){
            chessboard[0][5] = 500;
            chessboard[0][7] = 0;
            chessboard[0][6] = 10000;
            chessboard[0][4] = 0;
            return ret;
        }
        if(oldCoord.X == 7 && oldCoord.Y == 7 || oldCoord.X == 7 && oldCoord.Y == 0){
            ret = "s";
        }
        if(oldCoord.X == 0 && oldCoord.Y == 7 || oldCoord.X == 0 && oldCoord.Y == 0){
            ret = "l";
        }
        if(newCoord.X == 7 && newCoord.Y == 7 || newCoord.X == 7 && newCoord.Y == 0){
            ret = "s";
        }
        if(newCoord.X == 0 && newCoord.Y == 7 || newCoord.X == 0 && newCoord.Y == 0){
            ret = "l";
        }
        if(oldCoord.X == 4 && oldCoord.Y == 7 || oldCoord.X == 4 && oldCoord.Y == 0){
            ret = "b";
        }
        if(color == 1 && castleSide == 'l'){
            chessboard[7][3] = 500;
            chessboard[7][0] = 0;
            chessboard[7][2] = 10000;
            chessboard[7][4] = 0;
            return ret;
        }
        if(color == 1 && castleSide == 's'){
            chessboard[7][5] = 500;
            chessboard[7][7] = 0;
            chessboard[7][6] = 10000;
            chessboard[7][4] = 0;
            return ret;
        }
        if(color == -1 && castleSide == 'l'){
            chessboard[0][3] = 500;
            chessboard[0][0] = 0;
            chessboard[0][2] = 10000;
            chessboard[0][4] = 0;
            return ret;
        }
        if(color == -1 && castleSide == 's'){
            chessboard[0][5] = 500;
            chessboard[0][7] = 0;
            chessboard[0][6] = 10000;
            chessboard[0][4] = 0;
            return ret;
        }
        if(castleSide == 'n'){
            chessboard[newCoord.Y][newCoord.X] = chessboard[oldCoord.Y][oldCoord.X];
            chessboard[oldCoord.Y][oldCoord.X] = 0;
        }
        return ret;
    }
    public boolean isCapture(int[][] chessboard){
        if(castleSide == 'n'){
            if(chessboard[getNewSquare().Y][getNewSquare().X] != 0){
                return true;
            }
        }
        return false;
    }
    public static void printChessBoard(int[][] board){
        for (int[] strings : board) {
            for (int string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }
    public Move clone(){
        return new Move(new Coord(getOriginalSquare().X, getOriginalSquare().Y), new Coord(getNewSquare().X, getNewSquare().Y));
    }

}
