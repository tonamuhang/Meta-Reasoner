package Robot;


import SimulatedWorld.SimulatedWorld;
import SpaceTime.Cell;

public class Sensor implements Runnable {

    Robot robot = null;
    public Sensor(Robot robot){
        this.robot = robot;
    }
    public void run(){

        if (this.robot != null) {
            Cell cell = SimulatedWorld.getCell();
            this.robot.localMap.updateCell(cell);
        }


    }

}
