package space;

import AI.Robot;

public class Local {

    String id;
    private Cell[][] grid_coordinate;
    public int size_x = 100;
    public int size_y = 100;

    /**
     * Constructor
     * Constructs a local map of size 100x100
     */
    public Local(){
        this.grid_coordinate = new Cell[100][100];
        for(int i=0; i<size_x; i++){
            for(int j=0; j<size_y; j++){
                this.grid_coordinate[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Constructor
     * @param size integer indicating the size of the local map size x size
     */
    public Local(int size){
        this.size_y = size;
        this.size_x = size;
        this.grid_coordinate = new Cell[size][size];
        for(int i=0; i<size_x; i++){
            for(int j=0; j<size_y; j++){
                this.grid_coordinate[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Constructor
     * @param x integer indicating the size of the row of localmap
     * @param y integer indicating the size of the column of localmap
     */
    public Local(int x, int y){
        this.size_y = y;
        this.size_x = x;
        this.grid_coordinate = new Cell[x][y];
        for(int i=0; i<size_x; i++){
            for(int j=0; j<size_y; j++){
                this.grid_coordinate[i][j] = new Cell(i, j);
            }
        }
    }


    /**
     * Getter for localmap
     * @return Cell[][] the localmap
     */
    public Cell[][] getLocalMap(){
        return this.grid_coordinate;
    }

    /**
     * Add an item to the local map node
     * @param item An object scanned in the real world
     */
    public void addItem(Item item){
        int x = (int)item.coordinate.x;
        int y = (int)item.coordinate.y;

        this.grid_coordinate[x][y].addItem(item);
    }


    /**
     * Remove the item from the cell of local map
     * @param item The item to be removed
     */
    public void removeItem(Item item){
        int x = (int)item.coordinate.x;
        int y = (int)item.coordinate.y;
        this.grid_coordinate[x][y].removeItem(item);
    }


    /**
     * optimization required, now it checks all cells for the item then add the item again
     * @param item The item to be updated
     */
    public void updateItem(Item item){

        for(Cell[] cells : this.grid_coordinate){
            for(Cell cell : cells){
                cell.removeItem(item);
            }
        }

        this.addItem(item);
    }


    public void updateRobot(Robot robot){

        robot.validateBound();
        for(Cell[] cells : this.grid_coordinate){
            for(Cell cell : cells){
                cell.removeItem(robot);
            }
        }
        this.addItem(robot);

    }
    @Override
    public String toString(){
        return this.id;
    }

    public void printLocal(){
        for(int i = 0; i < this.grid_coordinate.length; i++){
            for(int j = 0; j < this.grid_coordinate[i].length; j++){
                if(grid_coordinate[i][j] != null){
                    if(grid_coordinate[i][j].getItems().size() != 0){
                        if(grid_coordinate[i][j].containsRobot()) {
                            System.out.print("X");
                        }
                        else{
                            System.out.print("I");
                        }
                    }
                    else {
                        System.out.print("O");
                    }
                }

            }
            System.out.println();
        }
    }


    // TODO: Refactor add check
    public String getCell(int x, int y){
        if(this.grid_coordinate[x][y].containsRobot()){
            return "X";
        }
        else if(this.grid_coordinate[x][y].getItems().size() != 0){
            return "I";
        }
        return "O";
    }


    /**
     * Add a portal to the local map
     * @param portal An item that connects two local maps
     */
    // TODO: Optimize
    public void addPortal(Portal portal){
        this.addItem(portal);
    }

    /**
     * Transfer an item from this map to the target map.
     * @param item  An object in the space
     * @param target_map  A grid map node in the world space
     */
    // TODO: Condition checks should be done either here or outside
    public void transferItem(Item item, Local target_map){
        this.removeItem(item);
        target_map.addItem(item);
    }


}
