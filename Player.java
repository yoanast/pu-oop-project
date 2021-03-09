package CourseProject;

public class Player {

    int id;
    boolean isActive;
    int pointsReceived;

    public Player(int id, boolean isActive, int pointsReceived) {
        this.id = id;
        this.isActive = isActive;
        this.pointsReceived = pointsReceived;
    }

    public int getPointsReceived() {
        return pointsReceived;
    }
}
