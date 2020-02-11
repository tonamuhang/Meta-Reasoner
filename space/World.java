package space;

import java.util.*;

/**
 * World wraps a map object from local maps(Nodes) to their connected local maps(Nodes)
 */
public class World {

    private static World world = null;
    private int node_num;

    // Singleton instance of the world map
    public Map<Local, List<Local>> world_map;

    /**
     * Constructor singleton
     */
    private World(){
        this.world_map = new HashMap<>();
        this.node_num = 0;
    }


    /**
     * Used to get the unique instance of the world map
     * @return an instance of the world map
     */
    public static World getWorld(){
        if(world == null){
            world = new World();
        }
        return world;
    }


    /**
     * Add a local map(Node) to the world map.
     * @param local_map the local map node
     */
    public void addNode(Local local_map){
        local_map.id = "" + this.node_num;
        world_map.put(local_map, new LinkedList<Local>());
        this.node_num ++;
    }


    /**
     * Get the node of a local map
     * @param id a unique identifier for a local map node
     * @return the local map node corresponding to the given id
     */
    public Local getNode(String id){
        for(Local local : this.world_map.keySet()){
            if(local.id.equals(id)){
                return local;
            }
        }
        return null;
    }


    /**
     * Build an edge between two LocalMap nodes.
     * @param head The starting node
     * @param tail The ending node
     * @param bi Indicates if the edge should be bidirectional
     */
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


    // TODO: Delete the edge
    public void deleteEdge(Local head, Local tail){

    }

    /**
     * Update a node in the worldmap according to its id
     * @param node The node with updated information in it
     */
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
