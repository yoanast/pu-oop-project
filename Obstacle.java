package CourseProject;

import java.awt.*;

public class Obstacle extends Tile{

    public Obstacle(int row, int col) {
        super(row, col);
        this.isObstacle = true;
        this.id = "Obstacle";
    }

    @Override
    public void drawTiles(Graphics g) {

        int tileX = this.col * this.TILE_SIZE;
        int tileY = this.row * this.TILE_SIZE;

        for(int i = 0; i < GameBoard.rN; i++) {

            g.setColor(Color.RED);
            g.fillRect(tileX,tileY,TILE_SIZE,TILE_SIZE);
            drawBorders(g,tileX,tileY);
        }
    }


}
