import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.File;
import java.util.HashMap;

import static java.awt.image.BufferedImage.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static int chess_Board_X_Value = 95;
    public static int chess_Board_Y_Value = 188;
    public static int chess_Board_Height = 704;
    public static int chess_Board_Width = 704;
    public static String[][] chessboard = new String[8][8];
    public static HashMap<Integer, String> pieceCorrelations = new HashMap<Integer, String>();
    public static double time = 0;
    public static void main(String[] args) {
        time = System.nanoTime();
        pieceCorrelations.put(28, "BR");//Black Rook
        pieceCorrelations.put(54, "Bk");//Black knight
        pieceCorrelations.put(31, "BB");//Black Bishop
        pieceCorrelations.put(44, "BQ");//Black Queen
        pieceCorrelations.put(43, "BK");//Black King
        pieceCorrelations.put(27, "BP");//Black Pawn
        pieceCorrelations.put(21, "WR");//White Rook
        pieceCorrelations.put(42, "Wk");//White knight
        pieceCorrelations.put(24, "WB");//White Bishop
        pieceCorrelations.put(10, "WQ");//White Queen
        pieceCorrelations.put(50, "WK");//White King
        pieceCorrelations.put(19, "WP");//White Pawn
        pieceCorrelations.put(0, "  "); //This is for empty squares, so they don't say 'null' when the array is printed. Cuz that's annoying

        checkForPieces();
        System.out.println("\n\n\n");

        for (String[] strings : chessboard) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        //checkForPieces(1,1);
        System.out.println((System.nanoTime()-time)/1000000000);






        JFrame frame = new JFrame();
        frame.setBounds(10, 10, 500, 500);
        frame.setDefaultCloseOperation(3);
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.red);
                g.fillRect(0, 0, 500, 500);
            }
        };
        frame.add(panel);
        frame.setVisible(true);
    }
    public static void checkForPieces() {
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage wholeScreen = new Robot().createScreenCapture(screenRect);
            for (int file = 1; file <= 8; file++) {
                for (int rank = 1; rank <= 8; rank++) {
                    int square_X = (int) (chess_Board_Width / 8) * (file - 1) + chess_Board_X_Value;
                    int square_Y = (int) (chess_Board_Height / 8) * (rank - 1) + chess_Board_Y_Value;
                    int square_Height = (int) chess_Board_Height / 8;
                    int square_Width = (int) chess_Board_Width / 8;
                    //Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                    //BufferedImage capture = new Robot().createScreenCapture(screenRect).getSubimage(square_X, square_Y, square_Width, square_Height);
                    //ImageIO.write(capture, "bmp", new File("screenshot.jpg"));
                    BufferedImage capture = wholeScreen.getSubimage(square_X, square_Y + square_Height / 2, square_Width, 1);
                    //ImageIO.write(capture, "bmp", new File("line.jpg"));
                    int countForBlack = 0;
                    int countForWhite = 0;
                    for (int i = 0; i < square_Width; i++) {
                        Color col = new Color(capture.getRGB(i, 0));
                        //System.out.println(col);
                        if (col.getRed() == 0) {
                            countForBlack++;
                        }
                        if (col.getRed() == 255) {
                            countForWhite++;
                        }
                    }
                    if (countForBlack > 10) {
                        chessboard[rank - 1][file - 1] = pieceCorrelations.get(countForBlack);
                        System.out.print(countForBlack + " ");
                    } else {
                        chessboard[rank - 1][file - 1] = pieceCorrelations.get(countForWhite);
                        System.out.print(countForWhite + " ");
                    }
                }
                System.out.println();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
