/**
 * Represents a vector in a two-dimensional space.
 */
public class Vector {
    public double angle;
    public double speed; //magnitude

    /**
     * Constructs a Vector object with the specified angle and speed.
     * 
     * @param angle the angle of the vector in radians
     * @param speed the magnitude (speed) of the vector
     */
    public Vector(double angle, double speed) {
        this.angle = angle;
        this.speed = speed;
    }

    /**
     * Constructs a Vector object with default angle and speed values of 0.
     */
    Vector(){
        angle = 0;
        speed = 0;
    }

    /**
     * Calculates the x and y coordinates of the vector.
     * 
     * @return a Coord object representing the x and y coordinates of the vector
     */
    public Coord getCoord(){
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);

        return new Coord(dx, dy);
    }
}