package space;

/**
 * A portal that connects two Local maps by defining the entrance and the exit.
 */
public class Portal extends Item{

    private Local entrance = null;
    private Local exit = null;
    private double cost = 0;

    public Portal(String id, Coordinate coordinate, Local entrance, double cost){
        super(id, coordinate);
        this.entrance = entrance;
        this.cost = cost;
    }

    public Portal(String id, Coordinate coordinate, Local entrance, Local exit, double cost){
        super(id, coordinate);
        this.entrance = entrance;
        this.exit = exit;
        this.cost = cost;
    }

    public void connect(Local exit){
        this.exit = exit;
    }

    public static void connect(Portal portal, Local entrance, Local exit){
        portal.entrance = entrance;
        portal.exit = exit;
    }

    public Local getEntrance() {
        return this.entrance;
    }

    public Local getExit() {
        return this.exit;
    }

    public double getCost(){
        return this.cost;
    }
}
