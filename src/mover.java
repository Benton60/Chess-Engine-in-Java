import java.awt.*;
import java.awt.event.InputEvent;

public class mover implements Runnable{
    private Move move;
    public static int chess_Board_X_Value = 150; //This is the bottom left X point of the chess board and is used to calculate
    //piece positions and where to move the mouse.
    public static int chess_Board_Y_Value = 250; //This is the bottom left Y point of the chess board and is used to calculate
    //piece positions and where to move the mouse.
    public mover(Move mov){
        move = mov;
    }
    public void run() {
        System.out.println("Moving Now");
        Coord start = move.getOriginalSquare();
        Coord end = move.getNewSquare();
        if (move.castleSide != 'n') {
            if(move.castleSide == 'l' && move.color == -1){
                try{
                    Robot robot = new Robot();
                    robot.mouseMove(chess_Board_X_Value + 4 * 85, chess_Board_Y_Value + 0 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(chess_Board_X_Value + 2 * 85, chess_Board_Y_Value + 0 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }catch(Exception e){}
            }
            if(move.castleSide == 'l' && move.color == 1){
                try{
                    Robot robot = new Robot();
                    robot.mouseMove(chess_Board_X_Value + 4 * 85, chess_Board_Y_Value + 7 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(chess_Board_X_Value + 2 * 85, chess_Board_Y_Value + 7 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }catch(Exception e){}
            }
            if(move.castleSide == 's' && move.color == -1){
                try{
                    Robot robot = new Robot();
                    robot.mouseMove(chess_Board_X_Value + 4 * 85, chess_Board_Y_Value + 0 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(chess_Board_X_Value + 6 * 85, chess_Board_Y_Value + 0 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }catch(Exception e){}
            }
            if(move.castleSide == 's' && move.color == 11){
                try{
                    Robot robot = new Robot();
                    robot.mouseMove(chess_Board_X_Value + 4 * 85, chess_Board_Y_Value + 7 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseMove(chess_Board_X_Value + 6 * 85, chess_Board_Y_Value + 7 * 85);
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }catch(Exception e){}
            }
        } else {
            try {
                Robot robot = new Robot();
                robot.mouseMove(chess_Board_X_Value + start.X * 85, chess_Board_Y_Value + start.Y * 85);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                //this.wait(200);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseMove(chess_Board_X_Value + end.X * 85, chess_Board_Y_Value + end.Y * 85);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                //robot.mouseMove(150,850);
                //this.wait(200);
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }
}
