/**
 * The Coord class represents a coordinate point in a two-dimensional space.
 */
public class Coord {
    public double x;
    public double y;

    /**
     * Constructs a new Coord object with the specified x and y coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of this Coord object.
     *
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the x coordinate of this Coord object to the specified value.
     *
     * @param x the new x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of this Coord object.
     *
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the y coordinate of this Coord object to the specified value.
     *
     * @param y the new y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calculates the distance between this Coord object and the specified Coord object.
     *
     * @param c the other Coord object
     * @return the distance between the two Coord objects
     */
    public double distance(Coord c) {
        return Math.sqrt(Math.pow(x - c.x, 2) + Math.pow(y - c.y, 2));
    }
}
