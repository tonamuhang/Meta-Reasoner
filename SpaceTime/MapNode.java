package SpaceTime;

import Robot.Robot;

import java.util.LinkedList;

/**
 * Wraps a LocalMap and the list of movement within the localmap.
 */
public class MapNode {
    LocalMap localMap = null;
    LinkedList<Robot.Movement> movements = null;
    String id;
    LinkedList<Edge> edgeList = null;

    public MapNode(LocalMap localMap, LinkedList<Robot.Movement> movements){
        this.localMap = localMap;
        this.movements = movements;
        this.id = localMap.id;
        this.edgeList = new LinkedList<>();
    }

    public LocalMap getLocalMap(){
        return this.localMap;
    }

    public LinkedList<Edge> getEdges(){
        return this.edgeList;
    }


}
