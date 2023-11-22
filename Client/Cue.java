public class Cue {
    private double angle;
    private int power;

    public Cue() {
        this.angle = 0;
        this.power = 1;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}