package CourseProject;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    public Object[][] tileCollection;

    public GameBoard() {

        this.setSize(650, 550);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.tileCollection = new Object[7][9];
        fillTileCollection();
    }

    /**
     *  Метод, чрез който визуализираме полетата по дадени ред и колона.
     */
    private void renderTiles(Graphics g, int row, int col) {
        if (this.isThereTile(row, col)) {
            Tile t = (Tile)this.getTile(row, col);
            t.drawTiles(g);
        }
    }

    /**
     *  Метод, чрез който изчертаваме игралната дъска и нейните елементи.
     */
    @Override
    public void paint(Graphics g) {

        for(int row = 0; row < 7; row++) {
            for(int col = 0; col < 9; col++) {

                this.renderTiles(g,row,col);

            }
        }
    }

    /**
     *  Метод, който ни връща обект от колекцията по зададени ред и колона.
     */
    public Object getTile(int row, int col) {
        return this.tileCollection[row][col];
    }

    /**
     *  Метод, който ни връща дали на дадени ред и колона вече има поле.
     */
    public boolean isThereTile(int row, int col) {
        return this.tileCollection[row][col] != null;
    }

    /**
     *  Метод, чрез който пълним колекцията на дъската с обекти от тип Tile.
     */
    public void fillTileCollection() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 9; j++) {
                this.tileCollection[i][j] = (new PlayerATerritory(i,j));
            }
        }

        for (int i = 2; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                this.tileCollection[i][j] = (new Battlefield(i,j));
            }
        }

        for (int i = 5; i <= 6; i++) {
            for (int j = 0; j < 9; j++) {
                this.tileCollection[i][j] = (new PlayerBTerritory(i,j));
            }
        }
    }


}
