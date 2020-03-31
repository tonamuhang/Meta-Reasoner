package SpaceTime;

import javax.swing.*;
import java.awt.*;

public class LocalMapGUI extends JPanel {
    public static final Color plain = new Color(255, 255, 255);
    public static final Color robot = new Color(255, 0, 0);
    public static final Color battery = new Color(0, 255, 0);

    public static int row = 20;
    public static int col = 20;
    public static final int PREFERRED_GRID_SIZE_PIXELS = 30;

    private Cell[][] mapGrid = null;
    private LocalMap localMap = null;
    public LocalMapGUI(LocalMap localMap){
        this.localMap = localMap;
        this.mapGrid = localMap.localMap;
        col = this.localMap.size;
        row = this.localMap.size;

        int width = col * PREFERRED_GRID_SIZE_PIXELS;
        int height = row * PREFERRED_GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.clearRect(0, 0, getWidth(), getHeight());

        int width = getWidth()/col;
        int height = getHeight()/row;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // Upper left corner of this terrain rect
                int x = i * width;
                int y = j * height;
                Color terrainColor = plain;
                if(i == localMap.x && j == localMap.y){
                    terrainColor = robot;
                }
                else if(this.mapGrid[i][j].containsBattery()){
                    terrainColor = battery;
                }

                g.setColor(terrainColor);
                g.fillRect(y, x, width, height);
            }
        }
    }

}
