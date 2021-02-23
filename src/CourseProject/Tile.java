package CourseProject;

import java.awt.*;

public class Tile {

    private int row;
    private int col;
    public static final int TILE_SIZE = 70;
    public static Color lightGray = new Color(221, 221, 221);
    public static Color nGray = new Color(170, 170, 170);
    public static Color nBlack = new Color(68, 68, 68);

    /**
     *  Конструктор за полетата.
     */
    public Tile (int row, int col) {

        this.row = row;
        this.col = col;
    }

    /**
     *  Метод, чрез който визуализираме отделните полета на дъската.
     */
    public void drawTiles(Graphics g) {

        int tileX = this.col * this.TILE_SIZE;
        int tileY = this.row * this.TILE_SIZE;

        drawFirstTiles(g,tileX,tileY);

        if (this.row == 0 && this.col % 2 == 0 || this. row == 6 && this.col % 2 == 0 ||
        this.row == 1 && this.col % 2 == 1 || this.row == 5 && this.col % 2 == 1) {
            g.setColor(nGray);
            g.fillRect(tileX,tileY,TILE_SIZE, TILE_SIZE);
            drawBorders(g,tileX,tileY);
        } else if (this.row == 0 && this.col % 2 == 1 || this. row == 6 && this.col % 2 == 1 ||
                   this.row == 1 && this.col % 2 == 0 || this.row == 5 && this.col % 2 == 0) {
            g.setColor(nBlack);
            g.fillRect(tileX,tileY,TILE_SIZE, TILE_SIZE);
            drawBorders(g,tileX,tileY);
        } else {
            g.setColor(lightGray);
            g.fillRect(tileX,tileY,TILE_SIZE,TILE_SIZE);
            drawBorders(g,tileX,tileY);
        }

    }

    public void drawFirstTiles(Graphics g, int tileX, int tileY) {

        if(this.row == 0 && this.col == 0 || this.row == 6 && this.col == 0) {
            g.setColor(nGray);
            g.fillRect(tileX,tileY,TILE_SIZE, TILE_SIZE);
            drawBorders(g,tileX,tileY);
        } else if (this.row == 1 && this.col == 0 || this.row == 5 && this.col == 0) {
            g.setColor(nBlack);
            g.fillRect(tileX,tileY,TILE_SIZE, TILE_SIZE);
            drawBorders(g,tileX,tileY);
        }
    }

    /**
     *  Метод, чрез който визуализираме рамка на полетата.
     */
    public void drawBorders(Graphics g, int tileX, int tileY) {

        g.setColor(nBlack);
        g.drawRect(tileX,tileY,TILE_SIZE,TILE_SIZE);
    }

}
