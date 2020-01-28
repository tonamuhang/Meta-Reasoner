package space;

import java.util.*;

public class World {

    private static World world = null;
    private int node_num;

    public Map<Local, List<Local>> world_map;

    private World(){
        this.world_map = new HashMap<>();
        this.node_num = 0;
    }


    public static World getWorld(){
        if(world == null){
            world = new World();
        }
        return world;
    }


    public void addNode(Local local_map){
        local_map.id = "" + this.node_num;
        world_map.put(local_map, new LinkedList<Local>());
        this.node_num ++;
    }


    // Build an edge between two LocalMap nodes.
    public void addEdge(Local head, Local tail, boolean bi){
        if(!world_map.containsKey(head)){
            addNode(head);
        }
        if(!world_map.containsKey(tail)){
            addNode(tail);
        }

        world_map.get(head).add(tail);

        if(bi){
            world_map.get(tail).add(head);
        }
    }


    // Update a node in the worldmap according to its id
    public void updateLocal(Local node){
        for(Local local : this.world_map.keySet()){
            if(local.id.equals(node.id)){
                local = node;
            }

            List<Local> neighbors = this.world_map.get(local);

            for(Local neighbor : neighbors){
                if(neighbor.id.equals(node.id)){
                    neighbor = node;
                }
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for(Local node : this.world_map.keySet()){
            builder.append(node.toString() + ": ");
            for (Local neighbors : this.world_map.get(node)){
                builder.append(neighbors.toString() + " ");

            }
            builder.append("\n");
        }

        return builder.toString();
    }


}
