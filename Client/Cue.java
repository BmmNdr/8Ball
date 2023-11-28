/**
 * The Cue class represents a cue used in a billiards game.
 * It stores the angle and power of the cue for shooting the ball.
 */
public class Cue {
    private double angle;
    private double power;

    /**
     * Constructs a Cue object with default angle and power values.
     * The default angle is set to Math.PI (180 degrees) and the default power is set to 1.
     */
    public Cue() {
        this.angle = Math.PI;
        this.power = 1;
    }

    /**
     * Returns the angle of the cue.
     * @return the angle of the cue
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the cue.
     * @param angle the angle of the cue
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Returns the power of the cue.
     * @return the power of the cue
     */
    public double getPower() {
        return power;
    }

    /**
     * Sets the power of the cue.
     * @param power the power of the cue
     */
    public void setPower(double power) {
        this.power = power;
    }
}