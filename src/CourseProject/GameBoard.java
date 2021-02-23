package CourseProject;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {

    public GameBoard() {

        this.setSize(650, 550);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    /**
     *  Метод, чрез който визуализираме полетата по дадени ред и колона.
     */
    private void renderTiles(Graphics g, int row, int col) {
        Tile tile = new Tile(row, col);
        tile.drawTiles(g);
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

}
