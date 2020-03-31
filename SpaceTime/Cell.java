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

    public void addItem(ArrayList<Item> items){
        if(this.items != null){
            this.items.addAll(items);
        }
    }

    public void removeItem(Item item){
        for(Item i : this.items){
            if(i.id.equals(item.id)){
                this.items.remove(i);
                return;
            }
        }
    }

    public void clear(){
        this.items.clear();
    }

    public boolean containsBattery(){
        for(Item i : this.items){
            if(i.id.equals("Battery")){
                return true;
            }
        }
        return false;
    }




}
