import Robot.Robot;
import SpaceTime.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class SimulatedWorld extends JPanel{
    public static final Color plain = new Color(255, 255, 255);
    public static final Color robot = new Color(255, 0, 0);

    public static final int row = 20;
    public static final int col = 20;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 30;

    private  Cell[][] worldGrid = null;

    public static int robot_x = 0;
    public static int robot_y = 0;

    public SimulatedWorld(){
        this.worldGrid = new Cell[row][col];
        Random rand = new Random();


        int width = col * PREFERRED_GRID_SIZE_PIXELS;
        int height = row * PREFERRED_GRID_SIZE_PIXELS;

        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());

        int width = getWidth()/col;
        int height = getHeight()/row;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // Upper left corner of this terrain rect
                int x = i * width;
                int y = j * height;
                Color terrainColor = plain;
                if(i == robot_x && j == robot_y){
                    terrainColor = robot;
                }

                g.setColor(terrainColor);
                g.fillRect(x, y, width, height);
            }
        }
    }




    public static void main(String[] args){



        Robot robot = new Robot(10);
        Cell[][] simulatedWorld = new Cell[10][10];



        Scanner scanner = new Scanner(System.in);
        String direction = "";
        boolean flag = true;
        while(flag){
            direction = scanner.nextLine();
            Robot.Movement movement = null;

            switch (direction){
                case "w":
                    movement = Robot.Movement.UP;
                    break;
                case "s":
                    movement = Robot.Movement.DOWN;
                    break;
                case "a":
                    movement = Robot.Movement.LEFT;
                    break;
                case "d":
                    movement = Robot.Movement.RIGHT;
                    break;

                default:
                    flag = false;
                    break;
            }
            if(movement != null) {
                if(robot.makeMove(movement)){
                    switch (movement){
                        case UP:
                            robot_x+=1;
                        case RIGHT:
                    }
                }

            }

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFrame frame = new JFrame("Game");
                    SimulatedWorld map = new SimulatedWorld();
                    frame.add(map);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            });

        }





    }

}
