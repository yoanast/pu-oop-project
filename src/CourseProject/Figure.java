package CourseProject;

import java.awt.*;

public abstract class Figure {

    protected int row;
    protected int col;
    protected Color color;
    protected int attack;
    protected int shield;
    protected int health;
    protected int possibleAttackSquares;
    protected int speed;
    public int maxHealth;

    /**
     *  Конструктор за фигурите.
     */
    public Figure(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     *  Метод, чрез който визуализираме отделните фигури на дъската.
     */
    public abstract void drawFigure(Graphics g);

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getAttack() {
        return attack;
    }

    public int getShield() {
        return shield;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getPossibleAttackSquares() {
        return possibleAttackSquares;
    }

    public int getSpeed() {
        return speed;
    }

    public Color getColor() {
        return color;
    }
}
