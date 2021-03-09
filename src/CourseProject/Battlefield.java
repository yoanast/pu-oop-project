package CourseProject;

import java.awt.*;

public class Battlefield extends Tile {

    public Color color;

    /**
     * Конструктор за полетата.
     */
    public Battlefield(int row, int col) {
        super(row, col);
        this.id = "BF";
        color = lightGray;
    }

    @Override
    public void drawTiles(Graphics g) {

        int tileX = this.col * this.TILE_SIZE;
        int tileY = this.row * this.TILE_SIZE;

        if (this.row == 2 || this.row == 3 || this.row == 4) {
            g.setColor(lightGray);
            g.fillRect(tileX,tileY,TILE_SIZE,TILE_SIZE);
            drawBorders(g,tileX,tileY);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
