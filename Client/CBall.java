// Ball.java
/**
 * Represents a ball in the 8 Ball game.
 */
public class CBall {
    public int number;
    public int x;
    public int y;

    /**
     * Constructs a CBall object with the specified number, x-coordinate, and y-coordinate.
     * 
     * @param number the number of the ball
     * @param x the x-coordinate of the ball
     * @param y the y-coordinate of the ball
     */
    public CBall(int number, int x, int y) {
        this.number = number;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the number of the ball.
     * 
     * @return the number of the ball
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the x-coordinate of the ball.
     * 
     * @return the x-coordinate of the ball
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the ball.
     * 
     * @return the y-coordinate of the ball
     */
    public int getY() {
        return y;
    }
}