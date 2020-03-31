package SpaceTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class WorldMap {
    HashSet<MapNode> mapNodes = null;

    public WorldMap(){
        this.mapNodes = new HashSet<>();
    }

    public void addNode(MapNode mapNode){
        this.mapNodes.add(mapNode);
    }

    public void addEdge(MapNode start, MapNode end){
        start.getEdges().add(new Edge(start, end));
    }


    public LocalMap getLocalMap(int x, int y){
        String id = "" + x + "_" + y;

        for(MapNode mapNode : this.mapNodes){
            if(mapNode.id.equals(id)){
                mapNode.localMap.reCenter();
                return mapNode.localMap;
            }
        }

        return null;
    }


}
