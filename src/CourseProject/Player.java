package CourseProject;

public class Player {

    int id;
    boolean isActive;
    int pointsReceived;
    int figuresLost;

    public Player(int id, boolean isActive, int pointsReceived, int figuresLost) {
        this.id = id;
        this.isActive = isActive;
        this.pointsReceived = pointsReceived;
        this.figuresLost = figuresLost;
    }

    public int getPointsReceived() {
        return pointsReceived;
    }

    public int getFiguresLost() {
        return figuresLost;
    }

    public void setFiguresLost(int figuresLost) {
        this.figuresLost = figuresLost;
    }
}
