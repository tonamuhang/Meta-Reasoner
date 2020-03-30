package SpaceTime;

import java.util.ArrayList;

public class Cell {
    int x, y;
    ArrayList<Item> items = null;


    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item){
        if(this.items != null) {
            this.items.add(item);
        }
    }


}
