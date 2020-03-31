package SpaceTime;

import Robot.Robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class LocalMap {
    Cell[][] localMap = null;
    int size = 0;
    public String id;
    int x, y;   // The robot's coordinate within the local map

    public LocalMap(int size, int x, int y){
        this.localMap = new Cell[size][size];
        this.size = size;
        this.initializeCells();
        this.id = "" + x + "_" + y;

        // Coordinate initialized to the center of the local map
        this.x = size/2;
        this.y = size/2;
    }


    private void initializeCells(){
        if(this.localMap!=null) {
            for (int i = 0; i < this.size; i++) {
                for (int j = 0; j < this.size; j++) {
                    this.localMap[i][j] = new Cell(i, j);
                }
            }
        }
    }


    /**
     * Move the robot within the local map.
     * @param movement
     * @return If true, do nothing. If false, robot will build a new local map
     */
    public boolean moveRobot(Robot.Movement movement){
        switch (movement){
            case UP:
                if(this.x>0){
                    this.x -= 1;
                    return true;
                }
                return false;
            case DOWN:
                if(this.x<this.size-1){
                    this.x += 1;
                    return true;
                }
                return false;
            case LEFT:
                if(this.y>0){
                    this.y -= 1;
                    return true;
                }
                return false;
            case RIGHT:
                if(this.y<this.size-1){
                    this.y += 1;
                    return true;
                }
                return false;
            default:
                return false;
        }



    }

    public void reCenter(){
        this.x = this.size/2;
        this.y = this.size/2;
    }

    public void updateCell(Cell cell){
        this.localMap[x][y].clear();
        this.localMap[this.x][this.y].addItem(cell.items);
    }

    public boolean checkBatteryStation(){
        return this.localMap[x][y].containsBattery();
    }

}
