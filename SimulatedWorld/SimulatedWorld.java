package SimulatedWorld;

import Robot.Reasoning.ActiveState;
import Robot.Robot;
import SpaceTime.Cell;
import SpaceTime.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.util.Random;
import java.util.Scanner;

public class SimulatedWorld extends JPanel{
    public static final Color plain = new Color(255, 255, 255);
    public static final Color robot_color = new Color(255, 0, 0);
    public static final Color battery = new Color(0, 255, 0);
    public static final Color localMap_point = new Color(0, 0, 255);

    public static int row = 20;
    public static int col = 20;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 30;

    private static Cell[][] worldGrid = null;

    public static int robot_x = 0;
    public static int robot_y = 0;

    private static boolean mark_local = false;

    /*** Robot's physical states***/
    public static ActiveState activeState = null;

    public SimulatedWorld(Cell[][] worldGrid){
        SimulatedWorld.worldGrid = worldGrid;
        row = worldGrid.length;
        col = worldGrid[0].length;

        Random rand = new Random();
        robot_x = rand.nextInt(20);
        robot_y = rand.nextInt(20);
        activeState = new ActiveState();

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
                    terrainColor = robot_color;
                }
                else if(worldGrid[i][j].containsBattery()){
                    terrainColor = battery;
                }
                else if(worldGrid[i][j].is_localmap){
                    terrainColor = localMap_point;
                }

                g.setColor(terrainColor);
                g.fillRect(y, x, width, height);
            }
        }

    }



    public static void main(String[] args){
        int worldsize = 20;


        // Initialize the cells
        Cell[][] simulatedWorld = new Cell[worldsize][worldsize];
        for(int i = 0; i < worldsize; i++){
            for(int j = 0; j < worldsize; j++){
                simulatedWorld[i][j] = new Cell(i, j);
            }
        }


        /*
         * Modify the simulated map here
         */
        /*-------------------------------------------------------------*/

        Item battery1 = new Item(3, 0, "Battery");
        Item battery2 = new Item(4, 5, "Battery");
        simulatedWorld[3][0].addItem(battery1);
        simulatedWorld[4][5].addItem(battery2);

        /*-------------------------------------------------------------*/

        JFrame frame = new JFrame("Meta");
        SimulatedWorld map = new SimulatedWorld(simulatedWorld);
        LayoutManager overlay = new OverlayLayout(map);
        map.setLayout(overlay);
        worldGrid[robot_x][robot_y].is_localmap = true;




        Robot robot = new Robot(100);
        robot.createLocalMapVisual();

        JLabel battery = new JLabel(""+ activeState.getBattery());
        battery.setForeground(Color.BLACK);
        battery.setAlignmentX(0);
        battery.setAlignmentY(0);
        map.add(battery);

        frame.add(map);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Scanner scanner = new Scanner(System.in);
        String direction = "";
        boolean flag = true;
        int counter = 20;

        while(flag){
            battery.setText(activeState.getBattery()+"");
            direction = scanner.nextLine();
            Robot.Movement movement = null;
//            robot.scoreRepaint();

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

                if(validateMove(movement) ){
                    if(robot.validateMove(movement)) {
                        activeState.setBattery(activeState.getBattery() - 1);


                        robot.makeMove(movement);
                        switch (movement) {
                            case UP:
                                robot_x -= 1;
                                break;
                            case DOWN:
                                robot_x += 1;
                                break;
                            case RIGHT:
                                robot_y += 1;
                                break;
                            case LEFT:
                                robot_y -= 1;
                                break;
                            default:
                                break;
                        }
                        if(mark_local){
                            mark_local = false;
                            worldGrid[robot_x][robot_y].is_localmap = true;
                        }
                        if(worldGrid[robot_x][robot_y].containsBattery()){
                            activeState.setBattery(activeState.getBattery() + 10);
                        }


                    }
                }

            }
            robot.localMapVisualRepaint();
            frame.repaint();

            counter -= 1;
            if(counter == 0){
                simulatedWorld[3][0].removeItem(battery1);
                simulatedWorld[7][3].addItem(new Item(7, 3, "Battery"));
            }
        }
        frame.setVisible(false);
        frame.dispose();
        robot.localMapVisualDispose();

    }

    public static boolean validateMove(Robot.Movement movement){
        switch (movement){
            case UP:
                return robot_x > 0;
            case DOWN:
                return robot_x < row - 1;
            case RIGHT:
                return robot_y < col - 1;
            case LEFT:
                return robot_y > 0;
            default:
                return false;
        }
    }

    public static Cell getCell(){
        return worldGrid[robot_x][robot_y];
    }

    public static void markLocalMap(){
        mark_local = true;
    }

}
