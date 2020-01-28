package space;

import java.util.ArrayList;

public class Local {

    String id;
    private Cell[][] grid_coordinate;
    private int size_x = 100;
    private int size_y = 100;

    public Local(){
        this.grid_coordinate = new Cell[100][100];
        for(int i=0; i<size_x; i++){
            for(int j=0; j<size_y; j++){
                this.grid_coordinate[i][j] = new Cell(i, j);
            }
        }
    }

    public Local(int size){
        this.grid_coordinate = new Cell[size][size];
        for(int i=0; i<size_x; i++){
            for(int j=0; j<size_y; j++){
                this.grid_coordinate[i][j] = new Cell(i, j);
            }
        }
    }

    public Local(int x, int y){
        this.grid_coordinate = new Cell[x][y];
        for(int i=0; i<size_x; i++){
            for(int j=0; j<size_y; j++){
                this.grid_coordinate[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getLocalMap(){
        return this.grid_coordinate;
    }

    public void addItem(Item item){
        int x = (int)item.coordinate.x;
        int y = (int)item.coordinate.y;

        this.grid_coordinate[x][y].addItem(item);
    }


    @Override
    public String toString(){
        return this.id;
    }


}
