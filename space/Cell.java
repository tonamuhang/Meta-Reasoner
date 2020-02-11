package space;

import javafx.scene.layout.CornerRadii;

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
    public void removeItem(Item item){
        this.items.remove(item);
    }

    public boolean contains(Item item){
        return this.items.contains(item);
    }


    public boolean containsRobot(){
        for(Item item : items){
            if(item.getClass().toString().equals(AI.Robot.class.toString())){
                return true;
            }
        }
        return false;
    }

}
