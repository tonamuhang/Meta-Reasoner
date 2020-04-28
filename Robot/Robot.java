package Robot;

import Robot.Reasoning.ActiveState;
import SimulatedWorld.SimulatedWorld;
import SpaceTime.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Robot {
    public enum Movement{
        UP, DOWN, LEFT, RIGHT, ENTRY, EXIT
    }

    LocalMap localMap = null;
    private WorldMap worldMap = null;
    private LinkedList<Movement> movements = null;
    private int x, y, memory;
    private Sensor sensor = null;
    private Thread sensorThread = null;
    private JFrame frame = null;
    private Panel p = null;
    private LocalMapGUI localMapGUI = null;
    private int localMapSize = 5;

//    public int battery;
    ActiveState activeState = null;

    public int localMap_x;
    public int localMap_y;

    // Scores
    private float left_score = 0;
    private float right_score = 0;
    private float down_score = 0;
    private float up_score = 0;


    // Movement types
    private Movement[] movement_option = new Movement[]{Movement.UP, Movement.DOWN, Movement.LEFT, Movement.RIGHT};

    public Robot(int memory){
        this.memory = memory;
//        this.virtualMap = new boolean[memory][memory];
        this.x = memory/2;
        this.y = memory/2;
        this.worldMap = new WorldMap();
        this.sensor = new Sensor(this);

        this.activeState = new ActiveState();

//        for(int i = 0; i < memory; i++){
//            for(int j = 0; j < memory; j++){
//                this.virtualMap[i][j] = false;
//            }
//        }

        this.buildLocalMap(this.localMapSize);  // Change localMap size here
        localMap_x = this.localMap.x;
        localMap_y = this.localMap.y;

        this.worldMap.addNode(new MapNode(this.localMap));
//        sensorThread = new Thread(this.sensor);
//        sensorThread.start();
    }


    /**
     * Build a localMap that has its origin at the robot's current location
     * @param size The size of the desired LocalMap
     */
    private void buildLocalMap(int size){
        // Instantiate the localmap and add references to the virtual map.
        this.localMap = new LocalMap(size, this.x, this.y);
//        this.virtualMap[x][y] = true;

        // Initialize the movements list within the localmap.
        this.movements = new LinkedList<>();
        this.movements.add(Movement.ENTRY);

        localMap_x = this.localMap.x;
        localMap_y = this.localMap.y;

        System.out.println("Built localmap " + localMap.id);
    }


    /**
     * Validate if a given move is acceptable
     * @param movement
     * @return true if the given move is accepted, false if not
     */
    public boolean validateMove(Movement movement){
        if(activeState.getBattery() <= 0){
            return false;
        }

        switch (movement){
            case UP:
                if(this.x>0) {
                    return true;
                }
                break;
            case DOWN:
                if(this.x<this.memory-1) {
                    return true;
                }
                break;
            case LEFT:
                if(this.y>0) {
                    return true;
                }
                break;
            case RIGHT:
                if(this.y<this.memory-1) {
                    return true;
                }
                break;
            default:

                return false;
        }
        return false;
    }


    /**
     * Move the robot by the given movement.
     * @param movement
     * @return True if the movement was successfully made.
     */
    public boolean makeMove(Movement movement){

        if(validateMove(movement)){
            switch (movement){
                case UP:
                    this.x -= 1;
                    this.movements.add(Movement.UP);

                    break;
                case DOWN:
                    this.x += 1;
                    this.movements.add(Movement.DOWN);
                    break;
                case LEFT:
                    this.y -= 1;
                    this.movements.add(Movement.LEFT);

                    break;
                case RIGHT:
                    this.y += 1;
                    this.movements.add(Movement.RIGHT);

                    break;
                default:
                    break;
            }

            // After making a move, update the information of the cell using sensor
            this.sensor.run();

            boolean is_in_localmap = this.localMap.moveRobot(movement, this);
            // If is outside of the current localmap, exit the current localmap
            // and move to a new one. if a local map doesn't exist, build one
            if(!is_in_localmap){
                //TODO: Fix error handling here
                try {
                    exitLocalMap();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            // Calculate score in different positions
            for(Movement move : movement_option){
                float score = StateValidator.validate(this, new Robot(copyRobot()), move);
                switch (move){
                    case RIGHT:
                        this.right_score = score;
                        break;
                    case LEFT:
                        this.left_score = score;
                        break;
                    case DOWN:
                        this.down_score = score;
                        break;
                    case UP:
                        this.up_score = score;
                        break;
                }
            }
            scoreRepaint();

//            this.battery -= 1;
//            ActiveState.setBattery(ActiveState.getBattery() - 1);
            return true;
        }
        return false;
    }


    /**
     * Used ONLY by a copy of the robot to evaluate the score of the state.
     * @param movement
     * @return true if the movement was successfully made by the robot copy
     */
    boolean evalutateMove(Movement movement){
//        System.out.println(validateMove(movement));
        if(validateMove(movement)) {
            switch (movement) {
                case UP:
                    this.x -= 1;
                    this.movements.add(Movement.UP);
                    break;
                case DOWN:
                    this.x += 1;
                    this.movements.add(Movement.DOWN);
                    break;
                case LEFT:
                    this.y -= 1;
                    this.movements.add(Movement.LEFT);
                    break;
                case RIGHT:
                    this.y += 1;
                    this.movements.add(Movement.RIGHT);
                    break;
                default:
                    break;
            }

            boolean is_in_localmap = this.localMap.moveRobot(movement, this);

            // Check in the local map if the robot should be on a battery station
            if(is_in_localmap) {
                if (this.localMap.onBattery(this)) {
                    this.activeState.setBattery(this.activeState.getBattery() + 10);
                }
            }

            this.activeState.setBattery(this.activeState.getBattery() - 1);


//            // If is outside of the current localmap, exit the current localmap
//            // and move to a new one. if a local map doesn't exist, build one
            return true;
        }
        return false;
    }

    /**
     * Exit the current localmap and add it to the worldmap
     * @return The current localmap as a mapnode.
     */
    private void exitLocalMap() throws Exception {
        LocalMap old_local = this.localMap;
        Movement movement = this.movements.getLast();

        this.movements.add(Movement.EXIT);
        this.printMovements();

        // If the local map has a corresponding neighbor, move to it and update memory
        if(this.worldMap.hasNeighbor(this.localMap, movement)){
            this.worldMap.updateLocalMap(this.localMap);
            System.out.println("Updated localmap " + this.localMap.id);
            this.localMap = this.worldMap.getLocalMap(this.localMap, movement).enterLocalMap(this);
            this.movements = new LinkedList<>();

        }
        else{
            // Create a new local map
            this.buildLocalMap(this.localMapSize);
            SimulatedWorld.markLocalMap();  // Mark in the simulated world, just for visual marks

            System.out.println("Added localmap " + this.localMap.id + " to worldmap");
            this.worldMap.addLocalMap(old_local, this.localMap, movement);

        }
        System.out.println("Moved to localmap " + this.localMap.id);
        System.out.println();
        this.worldMap.printNeighbors(this.localMap);



    }


    // No logic below. Only GUI
    /**
     * GUI
     */
    public void createLocalMapVisual(){
        this.frame = new JFrame("LocalMap");
        p = new Panel();
        p.setLayout(new BorderLayout());
        this.localMapGUI = new LocalMapGUI(this.localMap);
        LayoutManager overlay = new OverlayLayout(localMapGUI);
        localMapGUI.setLayout(overlay);

        // score
        JLabel score_1 = new JLabel(""+ left_score);
        score_1.setForeground(Color.BLACK);
        p.add(score_1, BorderLayout.WEST);

        JLabel score_2 = new JLabel(""+ up_score, SwingConstants.CENTER);
        score_2.setForeground(Color.BLACK);
        p.add(score_2, BorderLayout.NORTH);

        JLabel score_3 = new JLabel(""+ right_score);
        score_3.setForeground(Color.BLACK);
        p.add(score_3, BorderLayout.EAST);

        JLabel score_4 = new JLabel(""+ down_score, SwingConstants.CENTER);
        score_4.setForeground(Color.BLACK);
        p.add(score_4, BorderLayout.SOUTH);



        // Localmap gui
        p.add(localMapGUI, BorderLayout.CENTER);
        frame.add(p);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    public void localMapVisualRepaint(){
//        this.frame.getContentPane().removeAll();
        for(Component c : this.p.getComponents()){
            if(c instanceof JPanel){
                p.remove(c);
            }
        }


//        Panel p = new Panel();
//        p = new Panel();
//        p.setLayout(new BorderLayout());


        this.localMapGUI = new LocalMapGUI(this.localMap);
        LayoutManager overlay = new OverlayLayout(localMapGUI);
        localMapGUI.setLayout(overlay);

        p.add(localMapGUI, BorderLayout.CENTER);
        this.frame.add(p);
        frame.pack();
        this.frame.repaint();


    }

    private void scoreRepaint(){
        for(Component c : this.p.getComponents()){
            if(c instanceof JLabel){
                this.p.remove(c);
            }
        }

        // score
        JLabel score_1 = new JLabel(""+ left_score);
        score_1.setForeground(Color.BLACK);
        p.add(score_1, BorderLayout.WEST);

        JLabel score_2 = new JLabel(""+ up_score, SwingConstants.CENTER);
        score_2.setForeground(Color.BLACK);
        p.add(score_2, BorderLayout.NORTH);

        JLabel score_3 = new JLabel(""+ right_score);
        score_3.setForeground(Color.BLACK);
        p.add(score_3, BorderLayout.EAST);

        JLabel score_4 = new JLabel(""+ down_score, SwingConstants.CENTER);
        score_4.setForeground(Color.BLACK);
        p.add(score_4, BorderLayout.SOUTH);

        this.frame.revalidate();
        this.frame.repaint();

    }


    public void localMapVisualDispose(){
        this.frame.setVisible(false);
        this.frame.dispose();
        try {
            this.sensorThread.join();
        }
        catch (Exception e){
            System.exit(0);
        }
    }

    private void printMovements(){
        StringBuilder sb = new StringBuilder("Moves taken in the localmap: ");
        for(Movement movement : this.movements){
            if(movement.toString().equals("ENTRY")){
                sb.append("ENTRY");
            }
            else {
                sb.append("->").append(movement.toString());
            }
        }
        System.out.println(sb.toString());
    }

    public Robot(Robot robot){
        this.localMap = new LocalMap(robot.localMap);
        this.worldMap = new WorldMap(robot.worldMap);
        this.activeState =  robot.activeState.clone();
        this.movements = new LinkedList<>();
        this.movements.addAll(robot.movements);
        this.x = robot.x;
        this.y = robot.y;
        this.memory = robot.memory;
        this.localMap_x = robot.localMap_x;
        this.localMap_y = robot.localMap_y;

    }
    private Robot copyRobot(){
        return new Robot(this);
    }




}
