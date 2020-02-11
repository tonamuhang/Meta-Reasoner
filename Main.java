import AI.Robot;
import space.Coordinate;
import space.Item;
import space.Local;
import space.World;

import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        World world = World.getWorld();
        boolean flag = true;
        Robot robot = new Robot("AI", new Coordinate(0, 0), 1);
        Local node0 = new Local(5);
        Local node1 = new Local();
        Local node2 = new Local();

        world.addEdge(node0, node1, true);
        world.addEdge(node0, node2, true);
        world.addEdge(node1, node2, true);
        world.addEdge(node0, new Local(), true);
        Local node3 = world.getNode("3");
        robot.moveToLocal(node0);
        node0.addItem(new Item("item", 2, 3));

        System.out.println(world.toString());

//        world.getNode("0").printLocal();


        Scanner scanner = new Scanner(System.in);
        String direction;


        while(flag){
            System.out.println();
            robot.local_map.printLocal();
            direction = scanner.nextLine();
            switch (direction){
                case "w":
                    robot.moveVertical(-1);
                    break;
                case "s":
                    robot.moveVertical(1);
                    break;
                case "a":
                    robot.moveHorizontal(-1);
                    break;
                case "d":
                    robot.moveHorizontal(1);
                    break;
                default:
                    flag = false;
            }
            robot.checkSurrounding();
            System.out.println();

        }
    }
}
