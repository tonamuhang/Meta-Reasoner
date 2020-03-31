package Robot;

import SpaceTime.*;

import javax.swing.*;
import java.util.LinkedList;

public class Robot {
    public enum Movement{
        UP, DOWN, LEFT, RIGHT, ENTRY, EXIT
    }

    protected boolean[][] virtualMap = null;   // Holds information about localmap landmarks
    protected LocalMap localMap = null;
    private WorldMap worldMap = null;
    private LinkedList<Movement> movements = null;
    protected int x, y, memory;
    private Sensor sensor = null;
    private Thread sensorThread = null;
    JFrame frame = null;
    LocalMapGUI localMapGUI = null;
    private int localMapSize = 5;
    public int battery = 100;

    public Robot(int memory){
        this.memory = memory;
        this.virtualMap = new boolean[memory][memory];
        this.x = memory/2;
        this.y = memory/2;
        this.worldMap = new WorldMap();
        this.sensor = new Sensor(this);


        for(int i = 0; i < memory; i++){
            for(int j = 0; j < memory; j++){
                this.virtualMap[i][j] = false;
            }
        }

        this.buildLocalMap(this.localMapSize);  // Change localMap size here

//        sensorThread = new Thread(this.sensor);
//        sensorThread.start();
    }


    /**
     * Build a localMap that has its origin at the robot's current location
     * @param size The size of the desired LocalMap
     */
    public void buildLocalMap(int size){
        // Instantiate the localmap and add references to the virtual map.
        this.localMap = new LocalMap(size, this.x, this.y);
        this.virtualMap[x][y] = true;

        // Initialize the movements list within the localmap.
        this.movements = new LinkedList<>();
        this.movements.add(Movement.ENTRY);

        System.out.println("Built localmap " + localMap.id);
    }




    public boolean validateMove(Movement movement){
        if(this.battery <= 0){
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

            if(this.localMap.checkBatteryStation()){
                this.battery += 10;
            }

            boolean is_in_localmap = this.localMap.moveRobot(movement);
            // If is outside of the current localmap, build a new one
            if(!is_in_localmap && !this.virtualMap[x][y]){
                MapNode start = exitLocalMap();

                // Build a new local map, and connect the two local maps
                this.buildLocalMap(this.localMapSize);
                this.virtualMap[x][y] = true;
                MapNode end = new MapNode(this.localMap, this.movements);
                this.worldMap.addEdge(start, end);
            }
            // If it is a landmark and is a different localmap,
            // exit the current local map, and retrieve a localmap from the worldmap.
            else if(this.virtualMap[x][y]){
                if(!this.localMap.id.equals(x+"_"+y)) {
                    exitLocalMap();
                    this.localMap = this.worldMap.getLocalMap(this.x, this.y);

                    // Initialize the movement list
                    this.movements = new LinkedList<>();
                    this.movements.add(Movement.ENTRY);
                    System.out.println("retrieved " + this.localMap.id);

                    this.worldMap.printLocalMapConnection(this.localMap);
                }
            }
            // If not landmark and within the current localmap, do nothing

            this.battery -= 1;
            return true;
        }
        return false;
    }


    /**
     * Exit the current localmap and add it to the worldmap
     * @return The current localmap as a mapnode.
     */
    public MapNode exitLocalMap(){
        LocalMap old_local = this.localMap;
        this.movements.add(Movement.EXIT);
        MapNode start = new MapNode(old_local, this.movements);
        this.worldMap.addNode(start);
        System.out.println("Added localmap " + this.localMap.id + " to worldmap");
        this.printMovements();

        return start;
    }


    public void createLocalMapVisual(){
        this.frame = new JFrame("LocalMap");
        this.localMapGUI = new LocalMapGUI(this.localMap);
        frame.add(localMapGUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void localMapVisualRepaint(){
        this.frame.getContentPane().removeAll();
        this.frame.add(new LocalMapGUI(this.localMap));
        frame.pack();
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

    public void printMovements(){
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
}
