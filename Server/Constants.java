/**
 * The Constants class contains static variables and methods that define the constants used in the 8Ball game.
 */
public class Constants {
	// Constants for ball and pot dimensions
	public static int ballDiameter = 20;
	public static int potDiameter = 50;

	// Constant for table friction
	public static double tableFriction = 0.01;

	// Constant for table dimensions
	public static Coord table = new Coord(50, 50);
	public static int tableWidth = 800;
	public static int tableHeight = tableWidth / 2;

	// Array of pot positions
	static public Coord[] potsPositions = {
			new Coord(0, 0),
			new Coord(tableWidth / 2, 0),
			new Coord(tableWidth, 0),
			new Coord(0, tableHeight),
			new Coord(tableWidth / 2, tableHeight),
			new Coord(tableWidth, tableHeight)
	};

	/**
	 * Returns an array of initial positions for the balls on the table.
	 *
	 * @return an array of Coord objects representing the initial positions of the balls
	 */
	static public Coord[] getBallsInitialPositions() {
		Coord[] balls = new Coord[16];

		// Set the initial positions of the balls
		balls[0] = new Coord(tableWidth / 4, tableHeight / 2);
		balls[6] = new Coord(tableWidth * 3 / 4, tableHeight / 2);
		balls[14] = new Coord(balls[6].x + ballDiameter, balls[6].y - (ballDiameter / 2));
		balls[2] = new Coord(balls[6].x + ballDiameter, balls[6].y + (ballDiameter / 2));
		balls[8] = new Coord(balls[6].x + (ballDiameter * 2), balls[6].y);
		balls[1] = new Coord(balls[8].x, balls[8].y - ballDiameter);
		balls[13] = new Coord(balls[8].x, balls[8].y + ballDiameter);
		balls[15] = new Coord(balls[14].x + (ballDiameter * 2), balls[14].y);
		balls[3] = new Coord(balls[15].x, balls[15].y - ballDiameter);
		balls[5] = new Coord(balls[15].x, balls[2].y);
		balls[4] = new Coord(balls[15].x, balls[2].y + ballDiameter);
		balls[10] = new Coord(balls[8].x + (ballDiameter * 2), balls[8].y);
		balls[9] = new Coord(balls[10].x, balls[13].y);
		balls[12] = new Coord(balls[10].x, balls[9].y + ballDiameter);
		balls[11] = new Coord(balls[10].x, balls[1].y);
		balls[7] = new Coord(balls[10].x, balls[11].y - ballDiameter);

		return balls;
	}

	/**
	 * Returns the radius of the balls.
	 *
	 * @return the radius of the balls
	 */
	static public int getRadius() {
		return ballDiameter / 2;
	}
}