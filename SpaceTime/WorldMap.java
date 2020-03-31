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

    public void printLocalMapConnection(LocalMap localMap){
        StringBuilder sb = new StringBuilder(localMap.id);
        for(MapNode mapNode : this.mapNodes){
            if(mapNode.id.equals(localMap.id)){
                for(Edge edge : mapNode.getEdges()){
                    String end_id = edge.destination.id;
                    sb.append("->").append(end_id);
                }
            }
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

}
