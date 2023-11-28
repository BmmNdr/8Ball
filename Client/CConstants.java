/**
 * The CConstants class contains constants related to the 8 Ball game client.
 */
public class CConstants {
    public static int ballDiameter = 20;

    public static int potDiameter = 50;

    public static int tableWidth = 800;
    public static int tableHeight = tableWidth / 2;

    public static int widthOffset = 300;
    public static int heightOffset = 300;

    public static int cueWidth = 300;
    public static int cueHeight = 10;

    public static int border = 50;

    /**
     * Returns the radius of the ball.
     *
     * @return the radius of the ball
     */
    static public int getRadius() {
        return ballDiameter / 2;
    }
}
