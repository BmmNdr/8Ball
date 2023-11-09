public class Ball extends Thread {

    private GameManager game;
    public Vector coordinate;
    public Vector speed; // direction and intesity
    public boolean isHalf;
    public boolean isPotted;
    public int number;
    // Mass is equal for every ball

    public Ball(GameManager game, Vector coordinate, boolean isHalf, int number) {
        this.game = game;
        this.coordinate = coordinate;
        this.isHalf = isHalf;
        this.number = number;

        this.speed = new Vector();
    }

    @Override
    public void run() {
        // Move balls

        // Detects collision with other balls
    }

    @Override
    public String toString() {
        return Integer.toString(number) + "-" + Integer.toString(coordinate.x) + "-" + Integer.toString(coordinate.y);
    }
}
