package space;

public class Item {
    public String id;
    public Coordinate coordinate;

    public Item(String id, double x, double y){
        this.coordinate = new Coordinate(x, y);
        this.id = id;
    }

    public Item(String id, Coordinate coordinate){
        this.id = id;
        this.coordinate = coordinate;
    }


}
