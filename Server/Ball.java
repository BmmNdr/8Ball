public class Ball extends Thread {
    public Coord coordinate;
    public Vector velocity; // angle and intesity
    public boolean isHalf;
    public boolean isPotted;
    public int number;
    public boolean isMoving;
    public boolean stop;
    // Mass is equal for every ball

    public Ball(Coord coordinate, boolean isHalf, int number) {
        this.coordinate = coordinate;
        this.isHalf = isHalf;
        this.number = number;

        this.velocity = new Vector();

        isMoving = false;
    }

    @Override
    public void run() {

        stop = false;
        isMoving = !isPotted;
        while (!stop && !isPotted) {

            // Move balls
            if (isMoving) {
                coordinate.setX(coordinate.getX() + (this.getDX()));
                coordinate.setY(coordinate.getY() + (this.getDY()));
            }

            // double scale = Math.pow(10, 2);
            // if ((Math.round(velocity.speed * scale) / scale) != 0.00) {
            // if (velocity.speed < 0)
            // velocity.speed += 0.01;
            // else
            // velocity.speed -= 0.01;
            // }
            // else {
            // isMoving = false;
            // velocity.speed = 0;
            // velocity.angle = 0;
            // }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public String toString() {
        String tmp = Integer.toString(number) + "_";

        if (isPotted)
            tmp += "-1_-1";
        else
            tmp += Long.toString(Math.round(coordinate.x)) + "_" + Long.toString(Math.round(coordinate.y));

        return tmp;
    }

    public double getDX() {
        return this.velocity.speed * Math.cos(this.velocity.angle);
    }

    public double getDY() {
        return this.velocity.speed * Math.sin(this.velocity.angle);
    }

    public void setMagnitude(double DX, double DY) {
        this.velocity.speed = Math.sqrt(DX * DX + DY * DY);
    }

    public void setAngle(double DX, double DY) {
        this.velocity.angle = Math.atan2(DY, DX);
    }

    public double getX() {
        return coordinate.getX();
    }

    public double getY() {
        return coordinate.getY();
    }

    public void setX(double x) {
        coordinate.setX(x);
    }

    public void setY(double y) {
        coordinate.setY(y);
    }

    public double getAngle() {
        return velocity.angle;
    }

    public double getSpeed() {
        return velocity.speed;
    }

    public void pot() {
        isPotted = true;
        coordinate.x = -Integer.MAX_VALUE;
        coordinate.y = -Integer.MAX_VALUE;
        isMoving = false;
        velocity.speed = 0;
        velocity.angle = 0;
        stop = true;
    }
}