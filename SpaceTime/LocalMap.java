package SpaceTime;

import Robot.Robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class LocalMap {
    Cell[][] localMap = null;
    int size = 0;
    public String id;
    public int x, y;   // The robot's coordinate within the local map

    public LocalMap(LocalMap localMap){
        this.size = localMap.size;
        this.localMap = new Cell[size][size];
        this.id = localMap.id;
        this.x = localMap.x;
        this.y = localMap.y;


        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                Cell cell = new Cell(localMap.localMap[i][j]);
                this.localMap[i][j] = cell;
            }
        }
    }

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
    public boolean moveRobot(Robot.Movement movement, Robot robot){

        switch (movement){
            case UP:
                if(this.x>0){
                    this.x -= 1;
                    robot.localMap_x -= 1;
                    return true;
                }
                else{
                    robot.localMap_x = mod(this.x - 1, this.size) - this.size / 2;
                }
                return false;
            case DOWN:
                if(this.x<this.size-1){
                    this.x += 1;
                    robot.localMap_x += 1;
                    return true;
                }
                else{
                    robot.localMap_x = mod(this.x + 1, this.size) + this.size / 2;
                }
                return false;
            case LEFT:
                if(this.y>0){
                    this.y -= 1;
                    robot.localMap_y -= 1;
                    return true;
                }
                else{
                    robot.localMap_y = mod(this.y - 1, this.size) - this.size / 2;

                }
                return false;
            case RIGHT:
                if(this.y<this.size-1){
                    this.y += 1;
                    robot.localMap_y += 1;
                    return true;
                }
                else{
                    robot.localMap_y = mod(this.y + 1, this.size) + this.size / 2;
                }
                return false;
            default:
                return false;
        }
    }

    public boolean onBattery(Robot robot){
//        System.out.println("x " + this.x);
//        System.out.println("y " + this.y);
//        System.out.println("robot x " + robot.localMap_x);
//        System.out.println("robot y " + robot.localMap_y);
        return this.localMap[this.x][this.y].containsBattery();
    }

    public LocalMap enterLocalMap(Robot robot){
//        System.out.println("Robot local x =" + Robot.localMap_x);
//        System.out.println("Robot local y =" + Robot.localMap_y);
//        System.out.println("Local Map x = " + this.x);
//        System.out.println("Local Map y = " + this.y);
        this.x = robot.localMap_x;
        this.y = robot.localMap_y;
        return this;
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

    private int mod(int a, int b)
    {
        int ret = a % b;
        if (ret < 0)
            ret += b;
        return ret;
    }


}

