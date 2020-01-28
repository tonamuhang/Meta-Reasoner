package space;

public class Main {
    public static void main(String args[]){
        World world = World.getWorld();

        Local node0 = new Local();
        Local node1 = new Local();
        Local node2 = new Local();

        world.addEdge(node0, node1, true);
        world.addEdge(node0, node2, true);
        world.addEdge(node1, node2, true);

        System.out.println(world.toString());
    }
}
