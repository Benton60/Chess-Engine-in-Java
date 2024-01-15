import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.File;

import static java.awt.image.BufferedImage.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static int chess_Board_X_Value = 94;
    public static int chess_Board_Y_Value = 190;
    public static int chess_Board_Height = 704;
    public static int chess_Board_Width = 704;


    public static void main(String[] args) {

        char[][] chessboard = {
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}
        };
        checkForPieces(1,1);






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
    public static void checkForPieces(int file, int rank){
        int square_X = (int) (chess_Board_Width/8)* file + chess_Board_X_Value;
        int square_Y = (int) (chess_Board_Height/8)*rank + chess_Board_Y_Value;
        int square_Height = (int) chess_Board_Height/8;
        int square_Width = (int) chess_Board_Width/8;

        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect).getSubimage(square_X, square_Y, square_Width, square_Height);
            ImageIO.write(capture, "bmp", new File("screenshot.jpg"));
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}