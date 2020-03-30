package Robot;

import SpaceTime.Cell;
import SpaceTime.LocalMap;
import SpaceTime.MapNode;
import SpaceTime.WorldMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class Robot {
    public enum Movement{
        UP, DOWN, LEFT, RIGHT, ENTRY, EXIT
    }

    protected boolean[][] virtualMap = null;   // Holds information about localmap landmarks
    private LocalMap localMap = null;
    private WorldMap worldMap = null;
    private LinkedList<Movement> movements = null;
    protected int x, y, memory;
    private Sensor sensor = null;

    public Robot(int memory){
        this.memory = memory;
        this.virtualMap = new boolean[memory][memory];
        this.x = memory/2;
        this.y = memory/2;
        this.worldMap = new WorldMap();
        this.sensor = new Sensor();
        this.sensor.run();

        for(int i = 0; i < memory; i++){
            for(int j = 0; j < memory; j++){
                this.virtualMap[i][j] = false;
            }
        }

        this.buildLocalMap(3);  // Change localMap size here
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

            boolean is_in_localmap = this.localMap.moveRobot(movement);
            // If is outside of the current localmap, build a new one
            if(!is_in_localmap){
                MapNode start = exitLocalMap();

                // Build a new local map, and connect the two local maps
                this.buildLocalMap(3);
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
                }
            }
            // If not landmark and within the current localmap, do nothing
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
        System.out.println("Added localmap to worldmap " + this.localMap.id);

        return start;
    }
}
