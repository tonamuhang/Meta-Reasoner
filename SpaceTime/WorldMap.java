package SpaceTime;

import Robot.Robot;

import java.util.*;

public class WorldMap {

//    ArrayList<MapNode> mapNodes = null;
    HashSet<MapNode> mapNodes = null;

    public WorldMap(){
        this.mapNodes = new HashSet<>();
    }

    public WorldMap(WorldMap worldMap){
        this.mapNodes = new HashSet<>();
        this.mapNodes.addAll(worldMap.mapNodes);
    }
    public void addNode(MapNode mapNode){
        this.mapNodes.add(mapNode);
    }



    /**
     * Add the local map to the world map
     * @param start
     * @param end
     * @param movement
     */
    public void addLocalMap(LocalMap start, LocalMap end, Robot.Movement movement){

        for(MapNode start_node : this.mapNodes){
            if(start_node.id.equals(start.id)){
                Robot.Movement end_entry = null;
                switch (movement){
                    case UP:
                        end_entry = Robot.Movement.DOWN;
                        break;
                    case DOWN:
                        end_entry = Robot.Movement.UP;
                        break;
                    case LEFT:
                        end_entry = Robot.Movement.RIGHT;
                        break;
                    case RIGHT:
                        end_entry = Robot.Movement.LEFT;
                        break;
                }

                for(MapNode end_node : this.mapNodes){
                    // if the end node already exists
                    if(end.id.equals(end_node.id)){
                        start_node.linkLocalMap(end_node, movement);
                        end_node.linkLocalMap(start_node, end_entry);
                        return;
                    }
                }

                // If the end node doens't exist create a new map node
                MapNode end_node = new MapNode(end, start_node, end_entry);
                // Link within the start node
                start_node.linkLocalMap(end_node, movement);

                this.mapNodes.add(end_node);
                return;
            }
        }
    }

    public boolean hasNeighbor(LocalMap start, Robot.Movement movement) throws Exception {
        for(MapNode mapNode : this.mapNodes){
            if(mapNode.id.equals(start.id)){
                switch (movement){
                    case UP:
                        return mapNode.up != null;
                    case RIGHT:
                        return mapNode.right != null;
                    case LEFT:
                        return mapNode.left != null;
                    case DOWN:
                        return mapNode.down != null;
                }
            }
        }
        throw new Exception("No node found for start node");
    }


    public void updateLocalMap(LocalMap localMap){
        for(MapNode mapNode : this.mapNodes){
            if(mapNode.id.equals(localMap.id)){
                mapNode.updateLocalMap(localMap);
            }
        }
    }

    public LocalMap getLocalMap(LocalMap start, Robot.Movement movement){
        for(MapNode mapNode : this.mapNodes){
            if(mapNode.id.equals(start.id)){
                switch (movement){
                    case UP:
                        return mapNode.up.localMap;
                    case RIGHT:
                        return mapNode.right.localMap;
                    case LEFT:
                        return mapNode.left.localMap;
                    case DOWN:
                        return mapNode.down.localMap;
                }
            }
        }

        return null;
    }

    public void printNeighbors(LocalMap localMap){
        for(MapNode mapNode : this.mapNodes){
            if(mapNode.id.equals(localMap.id)){
                System.out.println("The neighbors are ");
                if(mapNode.up != null){
                    System.out.println("UP->" + mapNode.up.id);
                }
                if(mapNode.down != null){
                    System.out.println("Down->" + mapNode.down.id);
                }
                if(mapNode.left != null){
                    System.out.println("Left->" + mapNode.left.id);
                }
                if(mapNode.right != null){
                    System.out.println("Right->" + mapNode.right.id);
                }
            }
        }
    }


//    HashSet<MapNode> mapNodes = null;
//
//    public WorldMap(){
//        this.mapNodes = new HashSet<>();
//    }
//
//    public void addNode(MapNode mapNode){
//        this.mapNodes.add(mapNode);
//    }
//
//    public void addEdge(MapNode start, MapNode end){
//        start.getEdges().add(new Edge(start, end));
//    }
//
//
//    public LocalMap getLocalMap(int x, int y){
//        String id = "" + x + "_" + y;
//
//        for(MapNode mapNode : this.mapNodes){
//            if(mapNode.id.equals(id)){
//                mapNode.localMap.reCenter();
//                return mapNode.localMap;
//            }
//        }
//
//        return null;
//    }
//
//    public void printLocalMapConnection(LocalMap localMap){
//        StringBuilder sb = new StringBuilder(localMap.id);
//        for(MapNode mapNode : this.mapNodes){
//            if(mapNode.id.equals(localMap.id)){
//                for(Edge edge : mapNode.getEdges()){
//                    String end_id = edge.destination.id;
//                    sb.append("->").append(end_id);
//                }
//            }
//        }
//        sb.append("\n");
//        System.out.println(sb.toString());
//    }

}
