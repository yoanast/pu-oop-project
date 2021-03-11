package CourseProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JFrame implements MouseListener {

    public static int firstRandomNumber;
    public static int secondRandomNumber;
    public static int rN;
    public static Random rand = new Random();
    public Object[][] tileCollection;
    public Object[][] figureCollection;
    public Object selectedFigure;
    public Object figureUnderAttack;
    public int roundCounter;
    public static int oldRow;
    public static int oldCol;
    public static Player p1 = new Player(1, true, 0, 0);
    public static Player p2 = new Player(2, false, 0, 0);
    ArrayList<String> p1PinkDeadFigures = new ArrayList<>();
    ArrayList<String> p2CyanDeadFigures = new ArrayList<>();

    public GameBoard() {

        this.setSize(638, 500);
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

        if (this.isThereFigure(row, col) && this.selectedFigure == null) {
            Figure fig = (Figure) figureCollection[row][col];
            if (p1.isActive) {
                if (fig.getColor() == Color.PINK) {
                    this.selectedFigure = this.getFigure(row, col);
                    oldRow = row;
                    oldCol = col;
                    JOptionPane.showMessageDialog(null, "Фигурата е избрана успешно за Играч 1!");
                    p1.isActive = false;
                    p2.isActive = true;
                    roundCounter += 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Ред е на Играч 1 и той може да " +
                            "избира само розовите фигури!");
                    selectedFigure = null;
                }
            } else if (p2.isActive) {
                if (fig.getColor() == Color.CYAN) {
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
        } else if (this.selectedFigure != null && !isThereFigure(row, col)) {
            actionFrame(row, col);

        } else if (this.selectedFigure != null && isThereFigure(row, col)) {
            this.figureUnderAttack = this.getFigure(row,col);
            actionFrame(row,col);

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

    public void visualizePoints() {
        String tempP1 = "Точки на Играч 1: " + p1.getPointsReceived();
        String tempP2 = "Точки на Играч 2: " + p2.getPointsReceived();
        this.setTitle( tempP1 + "                   Knights, Elves and Dwarves                   " + tempP2);

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
                visualizePoints();
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
     *  Методи, чрез които получаваме случайни числа и ги записваме на променливите.
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

    /**
     *  Метод, чрез който създаваме меню с бутони, чрез които ирачът избира хода на събитията.
     */
    public void actionFrame(int row, int col) {
        JFrame f = new JFrame("Choose operation");
        JLabel tb = new JLabel("ACTIONS");
        tb.setBounds(150,50,120,30);
        JButton attackBtn = new JButton("Attack");
        attackBtn.setBounds(20,100,95,30);
        attackBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(figureCollection[row][col] instanceof Figure) {
                    if (isAttackValid(row, col)) {
                        attackFigure();
                        hasFigureDied(row, col);
                        isGameOver();
                        updateBoardAfterAttackOrHeal();
                        f.dispose();
                    } else JOptionPane.showMessageDialog(null, "Невъзможна атака, изберете друга цел!");
                    f.dispose();
                } else if (tileCollection[row][col] instanceof Obstacle) {
                    attackObstacle(row, col);
                    updateBoardAfterAttackOrHeal();
                    JOptionPane.showMessageDialog(null, "Вие унищожихте препятствието!");
                    f.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Изберете цел, която да атакувате!" +
                    " Ако просто искате да се преместите, изберете опцията 'Move' от менюто.");
                    f.dispose();
                }
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
                    f.dispose();
                } else if (tileCollection[row][col] instanceof Obstacle ) {
                    JOptionPane.showMessageDialog(null, "Не може да се преместите върху " +
                            "препятствие, ако желаете това изберете опция 'Атака'");
                    f.dispose();
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
                if (figureCollection [row][col] instanceof Figure ) {
                    isHealValid();
                } else {
                    JOptionPane.showMessageDialog(null, "Имате право да лекувате само фигури!");
                }
                f.dispose();
            }
        });
        f.add(attackBtn);f.add(moveBtn);f.add(healBtn);
        f.getContentPane().add(tb);
        f.setSize(350,200);
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);

    }

    /**
     *  Методи, чрез които определяме движението на фигурите по дъската.
     */
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

    /**
     *  Методи, чрез които опресняваме позииците на фигурите и визуализацията на дъската.
     */
    public void updateBoard(int row, int col) {
        this.figureCollection[row][col] = this.selectedFigure;
        this.figureCollection[oldRow][oldCol] = null;
        this.selectedFigure = null;
        this.repaint();
    }

    public void updateBoardAfterAttackOrHeal() {
        this.selectedFigure = null;
        this.repaint();
    }

    /**
     *  Методи, чрез които определяме правилата за атака на фигурите и реализираме атака.
     */
    public void attackFigure() {
        Figure sFig = (Figure)this.selectedFigure;
        Figure aFig = (Figure)this.figureUnderAttack;
        int attackedFigureCurrentHealth = aFig.getHealth();
        int dmgCaused = sFig.getAttack() - aFig.getShield();
        int isAttUnsuccessfulResult = isAttackUnsuccessful();

        if(isAttUnsuccessfulResult != attackedFigureCurrentHealth) {
            if(isAttUnsuccessfulResult == 3) {
                dmgCaused = dmgCaused / 2;
            }
            aFig.setHealth(attackedFigureCurrentHealth - dmgCaused);
            JOptionPane.showMessageDialog(null, "Успешна атака!");
            if(sFig.getColor() == Color.PINK) {
                p1.pointsReceived = p1.pointsReceived + dmgCaused;
            } else if (sFig.getColor() == Color.CYAN) {
                p2.pointsReceived = p2.pointsReceived + dmgCaused;
            }


        } else {
            JOptionPane.showMessageDialog(null, "Атаката беше неуспешна!");
        }
    }

    public boolean isAttackValid(int row, int col) {
        if(this.selectedFigure instanceof Knight) {
            if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
            row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1) {
                return true;
            }
        } else if (this.selectedFigure instanceof Dwarf) {
            if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
            row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1 ||
            row == oldRow + 2 && col == oldCol || row == oldRow && col == oldCol + 2 ||
            row == oldRow - 2 && col == oldCol || row == oldRow && col == oldCol - 2) {
                return true;
            }
        } else if (this.selectedFigure instanceof Elf) {
            if (row == oldRow + 1 && col == oldCol || row == oldRow && col == oldCol + 1 ||
            row == oldRow - 1 && col == oldCol || row == oldRow && col == oldCol - 1 ||
            row == oldRow + 2 && col == oldCol || row == oldRow && col == oldCol + 2 ||
            row == oldRow - 2 && col == oldCol || row == oldRow && col == oldCol - 2 ||
            row == oldRow + 3 && col == oldCol || row == oldRow && col == oldCol + 3 ||
            row == oldRow - 3 && col == oldCol || row == oldRow && col == oldCol - 3) {
                return true;
            }
        } return false;

    }

    public void attackObstacle(int row, int col) {
        if(isAttackValid(row,col)) {
            Figure fig = (Figure) this.selectedFigure;
            fig.move(row, col);
            updateBoard(row, col);
            this.tileCollection[row][col] = new Battlefield(row,col);
            Battlefield bf = (Battlefield) tileCollection[row][col];
            bf.setColor(Color.lightGray);
        }
    }

    public int isAttackUnsuccessful() {

        int diceResult = 0;

        for (int i = 0; i < 3; i++) {
            diceResult += rand.nextInt(7 - 1) + 1;
        }
        return diceResult;
    }

    /**
     *  Метод, чрез който проверяваме дали съответна фигура е загубила цялото си здраве и
     *  ако е така я премахваме от игралното поле.
     */
    public void hasFigureDied(int row, int col) {
        Figure sFig = (Figure)this.selectedFigure;
        Figure aFig = (Figure)this.figureUnderAttack;
        String figureType = "";

        if (aFig.getPossibleAttackSquares() == 1) {
            figureType = "Knight";
        } else if (aFig.getPossibleAttackSquares() == 2) {
            figureType = "Dwarf";
        } else if (aFig.getPossibleAttackSquares() == 3) {
            figureType = "Elf";
        }

        if (aFig.getHealth() <= 0) {
            this.figureCollection[row][col] = this.figureUnderAttack;
            this.figureCollection[row][col] = null;
            this.figureUnderAttack = null;
            JOptionPane.showMessageDialog(null, "Фигурата беше унищожена!");

            if(sFig.getColor() == Color.CYAN) {
                p1.setFiguresLost(p1.getFiguresLost() + 1);
                p1PinkDeadFigures.add(figureType);
            } else if (sFig.getColor() == Color.PINK) {
                p2.setFiguresLost(p2.getFiguresLost() + 1);
                p2CyanDeadFigures.add(figureType);
            }
        }

    }

    /**
     *  Методи, чрез които определяме правилата за лекуване на фигурите и реализираме лекуване.
     */
    public void healFigure() {

        rN = rand.nextInt(7 - 1) + 1;

        Figure fig = (Figure) this.selectedFigure;
        int currentHealth = fig.getHealth();
        if(currentHealth < fig.maxHealth) {
            fig.setHealth(currentHealth + rN);
            JOptionPane.showMessageDialog(null, "Успешно излекувахте фиурата си с " + rN + " здраве");
            updateBoardAfterAttackOrHeal();
        } else {
            JOptionPane.showMessageDialog(null, "Не може да излекувате фигурата, защото " +
                    "здравето й е непокътнато.");
        }
    }

    public void isHealValid() {
        Figure sFig = (Figure) this.selectedFigure;
        Figure aFig = (Figure)this.figureUnderAttack;
        if (sFig.getColor() == Color.PINK) {
            if(aFig.getColor() == Color.PINK) {
                healFigure();
            } else {
                JOptionPane.showMessageDialog(null, "Имате право да лекувате само своя фигура(розова)");
            }
        } else if (sFig.getColor() == Color.CYAN) {
            if(aFig.getColor() == Color.CYAN) {
                healFigure();
            } else {
                JOptionPane.showMessageDialog(null, "Имате право да лекувате само своята фигура(синя)");
            }
        }
    }

    /**
     *  Метод, чрез който проверяваме дали някой от играчите е загубил всичките си фигури,
     *  в такъв случай играта приключва и другия играч печели.
     */
    public void isGameOver() {
        if(p1.getFiguresLost() == 6) {
            JOptionPane.showMessageDialog(null, "Честито! Играч 2 със сините фигури спечели играта!\n" +
                    "Брой точки на Играч 2: " + p2.getPointsReceived() + "\nБрой точки на Играч 1: " + p1.getPointsReceived() +
                    "\nБрой рундове за които е приключила играта:" + roundCounter + "\nВсички унищожени единици на Играч 2: " +
                    p2CyanDeadFigures.toString() + "\nВсички унищожени единици на Играч 1: " + p1PinkDeadFigures.toString());
            System.exit(0);
        } else if (p2.getFiguresLost() == 6) {
            JOptionPane.showMessageDialog(null, "Честито! Играч 1 с розовите фигури спечели играта!/n" +
                    "Брой точки на Играч 1: " + p1.getPointsReceived() + "\nБрой точки на Играч 2: " + p2.getPointsReceived() +
                    "\nБрой рундове за които е приключила играта:" + roundCounter + "\nВсички унищожени единици на Играч 1: " +
                    p1PinkDeadFigures.toString() + "\nВсички унищожени единици на Играч 2: " + p2CyanDeadFigures.toString());
            System.exit(0);
        }
    }




}
