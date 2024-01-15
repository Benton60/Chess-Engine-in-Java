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
    public static int chess_Board_X_Value = 95;
    public static int chess_Board_Y_Value = 188;
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
        for(int i =1; i <=8;i++){
            for(int j =1; j<=8;j++){
                checkForPieces(j, i);
            }
            System.out.println();
        }
        //checkForPieces(1,1);







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
        int square_X = (int) (chess_Board_Width/8)* (file-1) + chess_Board_X_Value;
        int square_Y = (int) (chess_Board_Height/8)* (rank-1) + chess_Board_Y_Value;
        int square_Height = (int) chess_Board_Height/8;
        int square_Width = (int) chess_Board_Width/8;
        try {
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect).getSubimage(square_X, square_Y, square_Width, square_Height);
            ImageIO.write(capture, "bmp", new File("screenshot.jpg"));
            capture = new Robot().createScreenCapture(screenRect).getSubimage(square_X, square_Y+square_Height/2, square_Width, 1);
            ImageIO.write(capture, "bmp", new File("line.jpg"));
            int countForBlack = 0;
            int countForWhite = 0;
            for(int i = 0; i < square_Width; i++){
                Color col = new Color(capture.getRGB(i,0));
                //System.out.println(col);
                if(col.getRed() == 0){
                    countForBlack ++;
                }
                if(col.getRed() == 255){
                    countForWhite++;
                }
            }
            if(countForBlack > 10){
                System.out.print(countForBlack + " ");
            }else{
                System.out.print(countForWhite + " ");
            }
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}