package SpaceTime;

import Robot.Robot;

import java.util.HashMap;
import java.util.LinkedList;

public class Item {
    int x, y;
    String id;
    HashMap<LocalMap, LinkedList<Robot.Movement>> movements = null;

    public Item(int x, int y, String id){
        this.x = x;
        this.y = y;
        this.id = id;
        this.movements = new HashMap<>();
    }


    /**
     * Called when the item is being added to a localmap. Records how the robot
     * reach the item within the given localmap.
     * @param localMap
     * @param movements
     */
    public void addItem(LocalMap localMap, LinkedList<Robot.Movement> movements){
        if(this.movements != null){
            this.movements.put(localMap, movements);
        }
    }

}
