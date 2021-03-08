package CourseProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameBoard extends JFrame implements MouseListener {

    public static int firstRandomNumber;
    public static int secondRandomNumber;
    public static int rN;
    public static Random rand = new Random();
    public Object[][] tileCollection;
    public Object[][] figureCollection;
    public Object selectedFigure;
    public static int oldRow;
    public static int oldCol;
    public static Player p1 = new Player(1, true);
    public static Player p2 = new Player(2, false);

    public GameBoard() {

        this.setSize(650, 550);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addMouseListener(this);
        this.tileCollection = new Object[7][9];
        fillTileCollection();
        this.figureCollection = new Object[7][9];
        fillP1FigureCollection();
        fillP2FigureCollection();
        JOptionPane.showMessageDialog(null, "Играта започва! Ред е на Играч 1 с розовите фигури :)");
    }

    /**
     *  Метод, който прихваща натискане на бутона на мишката.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = this.getDimensionsBasedOnCoordinates(e.getY());
        int col = this.getDimensionsBasedOnCoordinates(e.getX());

        if(this.isThereFigure(row,col) && this.selectedFigure == null) {
            Figure fig = (Figure) figureCollection[row][col];
            if(p1.isActive) {
                if (fig.getColor() == Color.PINK) {
                    this.selectedFigure = this.getFigure(row, col);
                    oldRow = row;
                    oldCol = col;
                    JOptionPane.showMessageDialog(null, "Фигурата е избрана успешно за Играч 1!");
                    p1.isActive = false;
                    p2.isActive = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Ред е на Играч 1 и той може да " +
                            "избира само розовите фигури!");
                    selectedFigure = null;
                }
            } else if (p2.isActive) {
                if(fig.getColor() == Color.CYAN) {
                    this.selectedFigure = this.getFigure(row, col);
                    oldRow = row;
                    oldCol = col;
                    JOptionPane.showMessageDialog(null, "Фигурата е избрана успешно за Играч 2!");
                    p1.isActive = true;
                    p2.isActive = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Ред е на Играч 2 и той може да " +
                            "избира само сините фигури!");
                    selectedFigure = null;
                }
            }
        } else if (this.selectedFigure != null && !isThereFigure(row, col) ||
                   this.selectedFigure != null && isThereFigure(row, col) ) {
            actionFrame(row, col);
        } else if (this.selectedFigure != null && oldRow == row && oldCol == col) {
            JOptionPane.showMessageDialog(null, "Вие избрахте същата фигура, " +
            "възможно е само да я излекувате");
            actionFrame(row, col);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

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
     *  Метод, чрез който визуализираме фигурите по дадени ред и колона.
     */
    private void renderFigures(Graphics g, int row, int col) {

        if(this.isThereFigure(row,col)) {
            Figure fig = (Figure)this.getFigure(row,col);
            fig.drawFigure(g);
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
                this.renderFigures(g, row, col);
            }
        }
    }

    /**
     *  Метод, който ни връща обект от колекцията по зададени ред и колона.
     */
    public Object getFigure(int row, int col) {
        return this.figureCollection[row][col];
    }

    /**
     *  Метод, който ни връща дали на дадени ред и колона има фигура.
     */
    public boolean isThereFigure(int row, int col) {
        return this.getFigure(row, col) != null;
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

        getRandomNumberOfObstacles();
        for(int i = 0; i <= rN; i++) {
            getRandomPositionForObstacle();
            this.tileCollection[firstRandomNumber][secondRandomNumber] =
                    new Obstacle(firstRandomNumber,secondRandomNumber);
        }
    }

    /**
     *  Метод, чрез който получаваме две случайни числа и ги записваме на променливите.
     */
    public static void getRandomPositionForP1Figure() {

        firstRandomNumber = rand.nextInt(2);
        secondRandomNumber = rand.nextInt(9);
    }

    public static void getRandomPositionForP2Figure() {
        firstRandomNumber = rand.nextInt(7);
        secondRandomNumber = rand.nextInt(9);
        while (firstRandomNumber != 5 && firstRandomNumber != 6 ) {
            firstRandomNumber = rand.nextInt(7);
            secondRandomNumber = rand.nextInt(9);
        }
    }

    public static void getRandomNumberOfObstacles() {
        while (rN == 0) {
            rN = rand.nextInt(5);
        }
    }

    public static void getRandomPositionForObstacle() {
        firstRandomNumber = rand.nextInt(9);
        secondRandomNumber = rand.nextInt(9);
        while (firstRandomNumber != 2 && firstRandomNumber != 3 && firstRandomNumber != 4) {
            firstRandomNumber = rand.nextInt(9);
        }
    }

    /**
     *  Метод, чрез който пълним колекция с обекти от тип Figure.
     */
    public void fillP1FigureCollection() {

        for(int i = 0; i < 2; i++) {
            getRandomPositionForP1Figure();
            if(!this.isThereFigure(firstRandomNumber, secondRandomNumber)) {
                this.figureCollection[firstRandomNumber][secondRandomNumber] =
                        (new Knight(firstRandomNumber, secondRandomNumber, Color.PINK));
            } else i--;
        }

        for(int i = 0; i < 2; i++) {
            getRandomPositionForP1Figure();
            if(!this.isThereFigure(firstRandomNumber, secondRandomNumber)) {
                this.figureCollection[firstRandomNumber][secondRandomNumber] =
                        (new Elf(firstRandomNumber, secondRandomNumber, Color.PINK));
            } else i--;
        }

        for(int i = 0; i < 2; i++) {
            getRandomPositionForP1Figure();
            if(!this.isThereFigure(firstRandomNumber, secondRandomNumber)) {
                this.figureCollection[firstRandomNumber][secondRandomNumber] =
                        (new Dwarf(firstRandomNumber, secondRandomNumber, Color.PINK));
            } else i--;
        }
    }

    /**
     *  Метод, чрез който пълним колекция с обекти от тип Figure.
     */
    public void fillP2FigureCollection() {

        for(int i = 0; i < 2; i++) {
            getRandomPositionForP2Figure();
            if(!this.isThereFigure(firstRandomNumber, secondRandomNumber)) {
                this.figureCollection[firstRandomNumber][secondRandomNumber] =
                        (new Knight(firstRandomNumber, secondRandomNumber, Color.CYAN));
            } else i--;
        }

        for(int i = 0; i < 2; i++) {
            getRandomPositionForP2Figure();
            if(!this.isThereFigure(firstRandomNumber, secondRandomNumber)) {
                this.figureCollection[firstRandomNumber][secondRandomNumber] =
                        (new Elf(firstRandomNumber, secondRandomNumber, Color.CYAN));
            } else i--;
        }

        for(int i = 0; i < 2; i++) {
            getRandomPositionForP2Figure();
            if(!this.isThereFigure(firstRandomNumber, secondRandomNumber)) {
                this.figureCollection[firstRandomNumber][secondRandomNumber] =
                        (new Dwarf(firstRandomNumber, secondRandomNumber, Color.CYAN));
            } else i--;
        }
    }

    private int getDimensionsBasedOnCoordinates(int coordinates) {
        return coordinates / Tile.TILE_SIZE;
    }

    public void actionFrame(int row, int col) {
        JFrame f = new JFrame("Choose operation");
        JLabel tb = new JLabel("ACTIONS");
        tb.setBounds(150,50,120,30);
        JButton attackBtn = new JButton("Attack");
        attackBtn.setBounds(20,100,95,30);
        attackBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            }
        });
        JButton moveBtn = new JButton("Move");
        moveBtn.setBounds(120,100,95,30);
        moveBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if (figureCollection[row][col] instanceof Figure ) {
                    JOptionPane.showMessageDialog(null, "Не може да се преместите върху " +
                            "противникова фигура, ако желаете това изберете опция 'Атака', а върху своя фигура" +
                            "може да се използзва само опцията 'Лекуване'");
                } else if (figureCollection[row][col] instanceof Obstacle ) {
                    JOptionPane.showMessageDialog(null, "Не може да се преместите върху " +
                            "препятствие, ако желаете това изберете опция 'Атака'");
                } else {
                    moveFigure(row, col);
                    f.dispose();
                }
            }
        });

        JButton healBtn = new JButton("Heal");
        healBtn.setBounds(220,100,95,30);
        healBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            }
        });
        f.add(attackBtn);f.add(moveBtn);f.add(healBtn);
        f.getContentPane().add(tb);
        f.setSize(350,200);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);


    }

    public void moveFigure(int row, int col) {
        if (this.selectedFigure instanceof Knight) {
            moveKnight(row, col);
        } else if (this.selectedFigure instanceof Dwarf) {
            moveDwarf(row, col);
        } else if (this.selectedFigure instanceof Elf) {
            moveElf(row, col);
        }
    }

    public void moveKnight(int row, int col) {
        if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
        row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1) {
            Figure fig = (Figure) this.selectedFigure;
            fig.move(row, col);
            updateBoard(row, col);
        } else {
            JOptionPane.showMessageDialog(null, "Невъзможен ход, изберете друга позиция!",
            "Грешка", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void moveDwarf(int row, int col) {
        if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
        row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1 ||
        row == oldRow + 2 && col == oldCol || row == oldRow && col == oldCol + 2 ||
        row == oldRow - 2 && col == oldCol || row == oldRow && col == oldCol - 2 ||
        row == oldRow + 1 && col == oldCol + 1 || row == oldRow - 1 && col == oldCol - 1 ||
        row == oldRow + 1 && col == oldCol - 1 || row == oldRow - 1 && col == oldCol + 1)  {
            Figure fig = (Figure) this.selectedFigure;
            fig.move(row, col);
            updateBoard(row, col);
        }  else {
            JOptionPane.showMessageDialog(null, "Невъзможен ход, изберете друга позиция!",
            "Грешка", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void moveElf(int row, int col) {
        if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
        row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1 ||
        row == oldRow + 2 && col == oldCol || row == oldRow && col == oldCol + 2 ||
        row == oldRow - 2 && col == oldCol || row == oldRow && col == oldCol - 2 ||
        row == oldRow + 1 && col == oldCol + 1 || row == oldRow - 1 && col == oldCol - 1 ||
        row == oldRow + 1 && col == oldCol - 1 || row == oldRow - 1 && col == oldCol + 1 ||
        row == oldRow + 3 && col == oldCol || row == oldRow && col == oldCol + 3 ||
        row == oldRow - 3 && col == oldCol || row == oldRow && col == oldCol - 3 ||
        row == oldRow + 2 && col == oldCol + 1 || row == oldRow - 2 && col == oldCol - 1 ||
        row == oldRow + 2 && col == oldCol - 1 || row == oldRow - 2 && col == oldCol + 1 ||
        row == oldRow + 1 && col == oldCol + 2 || row == oldRow - 1 && col == oldCol - 2 ||
        row == oldRow + 1 && col == oldCol - 2 || row == oldRow - 1 && col == oldCol + 2)  {
            Figure fig = (Figure) this.selectedFigure;
            fig.move(row, col);
            updateBoard(row, col);
        }  else {
            JOptionPane.showMessageDialog(null, "Невъзможен ход, изберете друга позиция!",
            "Грешка", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updateBoard(int row, int col) {
        this.figureCollection[row][col] = this.selectedFigure;
        this.figureCollection[oldRow][oldCol] = null;
        this.selectedFigure = null;
        this.repaint();
    }

    public void attackFigure() {
        Figure fig = (Figure)this.selectedFigure;

    }

    public void knightAttackRules(int row, int col) {
        if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
            row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1) {

        }
    }

}
