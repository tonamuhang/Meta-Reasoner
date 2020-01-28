package space;

import java.util.ArrayList;

public class Cell {
    private int x;
    private int y;
    private ArrayList<Item> items = null;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

}
