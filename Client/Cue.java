public class Cue {
    private double angle;
    private double power;

    public Cue() {
        this.angle = Math.PI;
        this.power = 1;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }
}