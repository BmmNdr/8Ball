import java.util.List;

/**
 * The CollisionCheck class is responsible for detecting and resolving collisions between balls in a game.
 * It extends the Thread class to run collision detection and resolution in a separate thread.
 */
public class CollisionCheck extends Thread {
    private List<Ball> balls;

    public Ball firstCueHit;
    public Ball firstPot;

    public CollisionCheck(List<Ball> balls) {
        this.balls = balls;
        this.firstCueHit = null;
        this.firstPot = null;
    }

    /**
     * Executes the collision check algorithm.
     * Resets the firstCueHit and firstPot variables.
     * Loops through the balls and checks for collisions.
     * Resolves collisions between balls if they occur.
     * Sets the firstCueHit and firstPot variables if necessary.
     */
    @Override
    public void run() {

        this.firstCueHit = null;
        this.firstPot = null;

        while (ballsMoving()) {

            for (int i = 0; i < balls.size() - 1; i++) {

                if (!balls.get(i).isPotted) {

                    for (int j = i + 1; j < balls.size(); j++) {
                        if (!balls.get(j).isPotted && collideWith(balls.get(j), balls.get(i))) {
                            resolveCollision(balls.get(j), balls.get(i));

                            if (i == 0 && this.firstCueHit == null) {
                                firstCueHit = balls.get(j);
                            }
                        }
                    }

                } else if (firstPot == null) {
                    firstPot = balls.get(i);
                }
            }
        }
    }

    /**
     * Checks if any of the balls are currently moving.
     * 
     * @return true if at least one ball is moving, false otherwise.
     */
    private boolean ballsMoving() {
        for (Ball ball : balls) {
            if (ball.isMoving)
                return true;
        }

        return false;
    }

    /**
     * Checks if two balls collide with each other.
     * 
     * @param ball the first ball
     * @param ball2 the second ball
     * @return true if the balls collide, false otherwise
     */
    private boolean collideWith(Ball ball, Ball ball2) {
        return ball2.coordinate.distance(ball.coordinate) < Constants.getRadius() + Constants.getRadius();
    }

    /**
     * Resolves the collision between two balls.
     * Uses the elastic collision formula to calculate the new velocities and angles of the balls after collision.
     * Updates the velocities and angles of the balls accordingly.
     * Also calls the resolveOverlap method to resolve any overlap between the balls.
     *
     * @param ball  the first ball involved in the collision
     * @param ball2 the second ball involved in the collision
     */
    private void resolveCollision(Ball ball, Ball ball2) {
        // https://en.wikipedia.org/wiki/Elastic_collision#Two-dimensional_collision_with_two_moving_objects

        ball.isMoving = true;
        ball2.isMoving = true;

        // Angoli di movimento
        double thisAngle = ball2.getAngle();
        double otherAngle = ball.getAngle();

        // Angolo di contatto
        double distanceAngle = Math.atan2(ball.getY() - ball2.getY(), ball.getX() - ball2.getX());

        // "Massa" dei due oggetti
        double thisMass = Math.pow(Constants.getRadius(), 3);
        double otherMass = Math.pow(Constants.getRadius(), 3);

        // Misura Scalare delle velocità iniziali
        double thisV = ball2.getSpeed();
        double otherV = ball.getSpeed();

        // Elastic Collision Formula:
        double ball2DX = ((thisV * Math.cos(thisAngle - distanceAngle) * (thisMass - otherMass)
                + 2 * otherMass * otherV * Math.cos(otherAngle - distanceAngle)) / (thisMass + otherMass)
                * Math.cos(distanceAngle)
                + thisV * Math.sin(thisAngle - distanceAngle) * Math.cos(distanceAngle + Math.PI / 2));

        double ball2DY = ((thisV * Math.cos(thisAngle - distanceAngle) * (thisMass - otherMass)
                + 2 * otherMass * otherV * Math.cos(otherAngle - distanceAngle)) / (thisMass + otherMass)
                * Math.sin(distanceAngle)
                + thisV * Math.sin(thisAngle - distanceAngle) * Math.sin(distanceAngle + Math.PI / 2));

        double ball1DX = ((otherV * Math.cos(otherAngle - distanceAngle) * (otherMass - thisMass)
                + 2 * thisMass * thisV * Math.cos(thisAngle - distanceAngle)) / (thisMass + otherMass)
                * Math.cos(distanceAngle)
                + otherV * Math.sin(otherAngle - distanceAngle) * Math.cos(distanceAngle + Math.PI / 2));

        double ball1DY = ((otherV * Math.cos(otherAngle - distanceAngle) * (otherMass - thisMass)
                + 2 * thisMass * thisV * Math.cos(thisAngle - distanceAngle)) / (thisMass + otherMass)
                * Math.sin(distanceAngle)
                + otherV * Math.sin(otherAngle - distanceAngle) * Math.sin(distanceAngle + Math.PI / 2));

        ball.setMagnitude(ball1DX, ball1DY);
        ball.setAngle(ball1DX, ball1DY);

        ball2.setMagnitude(ball2DX, ball2DY);
        ball2.setAngle(ball2DX, ball2DY);

        resolveOverlap(ball, ball2);
    }

    /**
     * Resolves overlap between two balls by moving them apart until they no longer overlap.
     * @param ball The first ball involved in the overlap.
     * @param ball2 The second ball involved in the overlap.
     */
    private void resolveOverlap(Ball ball, Ball ball2) {
        while (ball2.coordinate.distance(ball.coordinate) < Constants.getRadius() + Constants.getRadius()) {
            double d = ball2.coordinate.distance(ball.coordinate);
            double overlap = Constants.getRadius() + Constants.getRadius() - d;
            Ball smallerBall = Constants.getRadius() <= Constants.getRadius() ? ball2 : ball;
            Ball biggerBall = Constants.getRadius() > Constants.getRadius() ? ball2 : ball;

            double theta = Math.atan2(biggerBall.getY() - smallerBall.getY(), biggerBall.getX() - smallerBall.getX());

            smallerBall.coordinate.setX(smallerBall.getX() - overlap * Math.cos(theta));
            smallerBall.coordinate.setY(smallerBall.getY() - overlap * Math.sin(theta));
        }
    }
}