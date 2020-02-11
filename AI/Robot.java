package AI;

import space.Coordinate;
import space.Item;
import space.Local;
import space.World;


// TODO:
// Live copy of the grid when entering, make changes
// on the live copy, write the copy to the local map

public class Robot extends Item{

    int sight = 1;


    public Local local_map = null;


    public Robot(String id, Coordinate coordinate, int sight){
        super(id, coordinate);
        this.sight = sight;
    }


    /**
     * Remove the robot from the previous localmap(if any) and add it to the new one
     * @param local The local map that the robot is moving to
     */
    public void moveToLocal(Local local){
        if(this.local_map != null){
            this.local_map.removeItem(this);
        }
        this.local_map = local;
        this.local_map.addItem(this);
    }


    /**
     * Move horizontally in the local map by some units
     * @param distance Pos for right, neg for left
     */
    public void moveHorizontal(int distance){
        // Move left
        if(this.coordinate.y + distance >= 0 && distance < 0){
            try {
                this.coordinate.y += distance;
                local_map.updateRobot(this);
                return;
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.err.println("Reached left boundary");
            }
        }


        //Move right
        if(this.coordinate.y + distance < local_map.size_y && distance > 0){
            try {
                this.coordinate.y += distance;
                local_map.updateRobot(this);
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.err.println("Reached right boundary");

            }
        }

    }

    /**
     *
     * @param distance Positive for down, negative for up
     */
    public void moveVertical(int distance){
        // Move up
        if(this.coordinate.x + distance >= 0 && distance < 0){
            try {
                this.coordinate.x += distance;
                local_map.updateRobot(this);
                return;
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.err.println("Reached upper boundary");
            }

        }


        //Move down
        if(this.coordinate.x + distance < local_map.size_x && distance > 0){
            try {
                this.coordinate.x += distance;
                local_map.updateRobot(this);
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.err.println("Reached lower boundary");
            }
        }

    }

    /**
     * Check the surroundings
     */
    public void checkSurrounding(){
        int start_x = (int)this.coordinate.x;
        int start_y = (int)this.coordinate.y;
        int x_index;
        int y_index;

        StringBuilder sb;
        for(int i = -this.sight; i <= this.sight; i++){
            sb = new StringBuilder("");
            x_index = i + start_x;
            if(x_index < 0){
                continue;
            }
            if(x_index >= this.local_map.size_x){
                continue;
            }

            for(int j = -this.sight; j <= this.sight; j++){

                y_index = j + start_y;
                if(y_index < 0){
                    continue;
                }
                if(y_index >= this.local_map.size_y){
                    continue;
                }
                sb.append(this.local_map.getCell(x_index, y_index));
            }

            System.out.println(sb.toString());

        }

    }


    public void validateBound(){
        if(this.coordinate.x > this.local_map.size_x){
            this.coordinate.x = this.local_map.size_x - 1;
        }
        if(this.coordinate.x < 0){
            this.coordinate.x = 0;
        }
        if(this.coordinate.y < 0){
            this.coordinate.y = 0;
        }
        if(this.coordinate.y > this.local_map.size_y){
            this.coordinate.y = this.local_map.size_y - 1;
        }
    }

}
