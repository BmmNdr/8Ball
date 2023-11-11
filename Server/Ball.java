public class Ball {
    public Coord coordinate;
    public Vector velocity; // angle and intesity
    public boolean isHalf;
    public boolean isPotted;
    public int number;
    // Mass is equal for every ball

    public Ball(Coord coordinate, boolean isHalf, int number) {
        this.coordinate = coordinate;
        this.isHalf = isHalf;
        this.number = number;

        this.velocity = new Vector();
    }

    public void move() {
        // Move balls

        // Detects collision with other balls
    }

    @Override
    public String toString() {
        return Integer.toString(number) + "-" + Long.toString(Math.round(coordinate.x)) + "-" + Long.toString(Math.round(coordinate.y));
    }
}
