package Robot.Reasoning;

public class ActiveState {
    int battery = 100;
    int temperature = 0;

    public ActiveState(){
    }

    public ActiveState(ActiveState state){
        this.battery = state.battery;
        this.temperature = state.temperature;
    }

    public int getBattery(){
        return battery;
    }

    public int getTemperature(){
        return temperature;
    }

    public void setBattery(int battery){
        this.battery = battery;
    }

    public ActiveState clone(){
        return new ActiveState(this);
    }


}
