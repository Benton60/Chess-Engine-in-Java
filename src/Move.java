public class Move {
    private Coord oldCoord;
    private Coord newCoord;
    public Move(Coord beginning, Coord ending){
        oldCoord = beginning;
        newCoord = ending;
    }
    public boolean isLegal(int[][] chessboard){
        if(newCoord.X > 7 || newCoord.X < 0 || newCoord.Y > 7 || newCoord.Y < 0){  // checks if the move falls outside the array aka board
            System.out.println("outside");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] > 0 && chessboard[oldCoord.Y][oldCoord.X] > 0){ // checks whether both pieces are white
            System.out.println("both are white");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] < 0 && chessboard[oldCoord.Y][oldCoord.X] < 0){ // checks whether both pieces are black
            System.out.println("both are black");
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
        return oldCoord.toString() + " : " + newCoord.toString();
    }
    public boolean areNotSameColor(int[][] chessboard){
        if(chessboard[newCoord.Y][newCoord.X] > 0 && chessboard[oldCoord.Y][oldCoord.X] > 0){ // checks whether both pieces are white
            System.out.println("both are white");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] < 0 && chessboard[oldCoord.Y][oldCoord.X] < 0){ // checks whether both pieces are black
            System.out.println("both are black");
            return false;
        }
        if(chessboard[newCoord.Y][newCoord.X] == 0){
            return false;
        }
        return true;
    }
}
