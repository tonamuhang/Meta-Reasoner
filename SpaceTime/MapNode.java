package SpaceTime;

import Robot.Robot;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;

/**
 * Wraps a LocalMap and the list of movement within the localmap.
 */
public class MapNode {
    LocalMap localMap = null;
    LinkedList<Robot.Movement> movements = null;
    String id;
    LinkedList<Edge> edgeList = null;
    MapNode left = null;
    MapNode right = null;
    MapNode down = null;
    MapNode up = null;

    public MapNode(LocalMap localMap){
        this.localMap = localMap;
        this.id = localMap.id;
        this.movements = new LinkedList<>();
    }

    public MapNode(LocalMap localMap, MapNode previous, Robot.Movement movement){
        this.localMap = localMap;
        this.id = localMap.id;
        this.movements = new LinkedList<>();

        this.linkLocalMap(previous, movement);
    }


    /**
     * Link the mapnode with the given mapnode to the cardinal direction
     * @param node
     * @param movement
     */
    public void linkLocalMap(MapNode node, Robot.Movement movement){
        if(node.id.equals(this.id)){
            System.out.println("you can't link to yourself");
            return;
        }

        switch (movement){
            case UP:
                this.up = node;
                break;
            case DOWN:
                this.down = node;
                break;
            case LEFT:
                this.left = node;
                break;
            case RIGHT:
                this.right = node;
                break;
        }
    }

    public void updateLocalMap(LocalMap localMap){
        if(localMap.id.equals(this.id)){
            this.localMap = localMap;
        }
    }

    public LocalMap getLocalMap(){
        return this.localMap;
    }

    public LinkedList<Edge> getEdges(){
        return this.edgeList;
    }


}
