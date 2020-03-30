package SpaceTime;

import Robot.Robot;

import java.util.LinkedList;

public class Edge {
    MapNode destination = null;
    LinkedList<Robot.Movement> movements = null;

    public Edge(MapNode start ,MapNode destination){
        this.destination = destination;
        this.movements = start.movements;
    }


}
