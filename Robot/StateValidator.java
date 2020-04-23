package Robot;

import Robot.Reasoning.ActiveState;

public class StateValidator {
//    public static Robot meta;
//    public static Robot meta_copy;

    public static float validate(Robot meta, Robot meta_copy, Robot.Movement movement){
//        StateValidator.meta_copy = meta_copy;
//        StateValidator.meta = meta;

        ActiveState state = meta.activeState;
        ActiveState state_copy = null;

//        System.out.println(meta_copy.evalutateMove(movement));

        if(meta_copy.evalutateMove(movement)){
            state_copy = meta_copy.activeState;
        }
        else {
            return -9999;
        }



        return getMoodScore(state_copy);
    }


    public static int batteryScore(ActiveState state){
        if(state.getBattery() <= 0){
            return -9999;
        }
        else{
            return state.getBattery();
        }
    }

    public static int temperatureScore(ActiveState state){
        if(state.getTemperature() > 40){
            return -9999;
        }
        else{
//            return state.getTemperature();
            return 1;
        }
    }

    // Higher mood score -> better
    public static float getMoodScore(ActiveState state){
        float mood_score;
        mood_score = batteryScore(state) + 0.0f/temperatureScore(state);
        return mood_score;
    }
}
