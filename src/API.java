
import javax.swing.*;
        import java.awt.*;
        import java.awt.image.BufferedImage;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.HashMap;
import java.util.Scanner;

public class API implements Runnable {
    public static JFrame frame = new JFrame();
    public static JPanel panel = new JPanel() {
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, 500, 500);
            g.setColor(Color.black);
            g.drawString("Time: " + (System.nanoTime()-time)/1000000000,10,20);
            for(int i = 1; i <= 8; i++){
                for(int j = 1; j <= 8; j++){
                    g.drawString(Integer.toString(chessboard[j-1][i-1]),i*20, j*20+50);
                }
            }
        }
    };
    public static int chess_Board_X_Value = 95; //This is the top right X point of the chess board and is used to calculate
    //piece positions and where to move the mouse.
    public static int chess_Board_Y_Value = 188; //This is the top right Y point of the chess board and is used to calculate
    //piece positions and where to move the mouse.
    public static int chess_Board_Height = 704; //Total height of the chess board in pixels
    public static int chess_Board_Width = 704; //Total width of the chess board in pixels
    public static int[][] chessboard = new int[8][8]; //This is the chessboard that will eventually be passed through to the engine class for evaluation
    public static int[][] oldChessboard = new int[8][8];
    public static HashMap<Integer, Integer> pieceCorrelations = new HashMap<>(); //This stores the pixel values for each piece
    public static double time = 0; // Creates a timer to check how long it takes to run the eval.
    private static boolean bCanCastleL = true;
    private static boolean bCanCastleS = true;
    private static boolean canCastleL = true;
    private static boolean canCastleS = true;
    private static boolean areWhite = true;


    public void run(){
        addPiecePixelValues();
        setUpOutPutWindow();
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        checkForPieces(oldChessboard);
        if(oldChessboard[0][0] == 500){
            System.out.println("Black");
            areWhite = false;
            checkForPieces(oldChessboard);
            checkForPieces(chessboard);
            while(true){
                waitForUpdate();
                startEngine();
                waitForUpdate();
            }
        }else{
            System.out.println("White");
            checkForPieces(oldChessboard);
            checkForPieces(chessboard);
            while(true) {
                startEngine();
                waitForUpdate();
                waitForUpdate();
            }
        }

    }
    public void waitForUpdate(){
        while(areEqual(oldChessboard, chessboard)){
            checkForPieces(chessboard);
        }
        copyArrays(oldChessboard, chessboard);
    }
    public static void checkForPieces(int[][] board) {
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage wholeScreen = new Robot().createScreenCapture(screenRect);
            for (int file = 1; file <= 8; file++) {
                for (int rank = 1; rank <= 8; rank++) {
                    int square_X = (chess_Board_Width / 8) * (file - 1) + chess_Board_X_Value;
                    int square_Y = (chess_Board_Height / 8) * (rank - 1) + chess_Board_Y_Value;
                    int square_Height = chess_Board_Height / 8;
                    int square_Width = chess_Board_Width / 8;
                    //Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    //BufferedImage capture = new Robot().createScreenCapture(screenRect).getSubimage(square_X, square_Y, square_Width, square_Height);
                    //ImageIO.write(capture, "bmp", new File("screenshot.jpg"));
                    BufferedImage capture = wholeScreen.getSubimage(square_X, square_Y + square_Height / 2, square_Width, 1);
                    //ImageIO.write(capture, "bmp", new File("line.jpg"));
                    int countForBlack = 0;
                    int countForWhite = 0;
                    for (int i = 0; i < square_Width; i++) {
                        Color col = new Color(capture.getRGB(i, 0));
                        if (col.getRed() == 0) {
                            countForBlack++;
                        }
                        if (col.getRed() == 255) {
                            countForWhite++;
                        }
                    }
                    if (countForBlack > 10) {
                        board[rank - 1][file - 1] = pieceCorrelations.get(countForBlack);
                        //System.out.print(countForBlack + " ");
                    } else {
                        board[rank - 1][file - 1] = pieceCorrelations.get(countForWhite);
                        //System.out.print(countForWhite + " ");
                    }
                }
                //System.out.println();
            }
        }catch(Exception e){
            System.out.println(e);
        }
        playingBlack(board);
    }
    public static void addPiecePixelValues(){
        pieceCorrelations.put(28, -500);//Black Rook
        pieceCorrelations.put(54, -300);//Black knight
        pieceCorrelations.put(31, -350);//Black Bishop
        pieceCorrelations.put(44, -900);//Black Queen
        pieceCorrelations.put(43, -10000);//Black King
        pieceCorrelations.put(27, -100);//Black Pawn
        pieceCorrelations.put(21, 500);//White Rook
        pieceCorrelations.put(42, 300);//White knight
        pieceCorrelations.put(24, 350);//White Bishop
        pieceCorrelations.put(10, 900);//White Queen
        pieceCorrelations.put(50, 10000);//White King
        pieceCorrelations.put(19, 100);//White Pawn
        pieceCorrelations.put(0, 0); //This is for empty squares, so they don't say 'null' when the array is printed. Cuz that's annoying
    }
    public static void setUpOutPutWindow(){
        JFrame frame = new JFrame();
        frame.setBounds(1000, 10, 500, 500);
        frame.setDefaultCloseOperation(3);
        frame.add(panel);
        frame.setVisible(true);
    }
    public static void printChessBoard(int[][] board){
        for (int[] strings : board) {
            for (int string : strings) {
                System.out.print(string + " ");
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
    public static Move getLastMove(int[][] board, int[][] newBoard){
        ArrayList<Coord> changes = new ArrayList<>();
        Coord endMove = new Coord(0,0);
        Coord startMove = new Coord(0,0);
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j]  != newBoard[i][j]){
                    changes.add(new Coord(j, i));
                    if(newBoard[i][j] == 0){
                        startMove = new Coord(j, i);
                    }
                    if(newBoard[i][j] != 0){
                        endMove = new Coord(j, i);
                    }
                }

            }
        }
        if(changes.size() > 2){
            return new Move();
        }
        return new Move(startMove, endMove);
    }
    public static void updateCastling(int[][] board){
        if(chessboard[0][0] != -500){
            bCanCastleL = false;
        }
        if(chessboard[0][7] != -500){

        }
        if(chessboard[7][0] != 500){
            canCastleL = false;
        }
        if(chessboard[7][7] != 500){
            canCastleS = false;
        }
        if(chessboard[0][4] != -10000){
            bCanCastleL = false;
            bCanCastleS = false;
        }
        if(chessboard[7][4] != 10000){
            canCastleS = false;
            canCastleL = false;
        }
    }
    public static boolean areEqual(int[][] b, int[][] a){
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void flipCopy(int[][] board, int[][] newBoard){
        for(int i = 0; i < board.length; i ++){
            for(int j = 0; j < board.length; j++){
                board[i][j] = newBoard[i][j] * -1;
            }
        }
    }
    public static void startEngine(){
        panel.repaint();
        frame.repaint();
        int[][] posBoard = new int[8][8];
        copyArrays(posBoard, chessboard);
        Position pos = new Position(posBoard,1, getLastMove(oldChessboard, chessboard), canCastleL, canCastleS, bCanCastleL, bCanCastleS);
        Thread test1 = new Thread(new Engine(pos, 100));
        test1.start();
    }
    public static void playingBlack(int[][] board){
        if(!areWhite){
            int[][] inbetween = new int[8][8];
            flipCopy(inbetween, board);
            copyArrays(board, inbetween);
        }
        updateCastling(board);
    }
}
