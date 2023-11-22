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
        isMoving = !isPotted;
        while (!isPotted && !stop) {

            // Move balls
            if (isMoving) {
                coordinate.setX(coordinate.getX() + (this.getDX()));
                coordinate.setY(coordinate.getY() + (this.getDY()));

                double scale = Math.pow(10, 2);
                if ((Math.round(velocity.speed * scale) / scale) != 0.00) {
                    if (velocity.speed < 0)
                        velocity.speed += Constants.tableFriction;
                    else
                        velocity.speed -= Constants.tableFriction;
                } else {
                    isMoving = false;
                    velocity.speed = 0;
                    velocity.angle = 0;
                }

                checkPot();
                wallCollision();
            }

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    private void checkPot() {
        // TODO check if ball center is inside the pot circle
        // Serach for point inside a circle

        for (Coord pot : Constants.potsPositions) {
            if (this.coordinate.distance(pot) < Constants.potDiameter / 2) {
                pot();

                System.out.println("Potted Ball " + Integer.toString(this.number));

                break;
            }
        }
    }

    private void wallCollision() {

        double ballDX = this.getDX();
        double ballDY = this.getDY();
        boolean isMod = false;

        if (this.getX() - Constants.getRadius() <= 0 || this.getX() + Constants.getRadius() >= Constants.tableWidth) {

            if (this.getY() - Constants.getRadius() < (Constants.tableHeight - Constants.potDiameter / 2)
                    && this.getY() + Constants.getRadius() > Constants.potDiameter / 2) {
                ballDX *= -1;
                isMod = true;
            }
        }

        if (this.getY() - Constants.getRadius() <= 0 || this.getY() + Constants.getRadius() >= Constants.tableHeight) {
            if ((this.getX() + Constants.getRadius() > Constants.potDiameter / 2 && this.getX()
                    - Constants.getRadius() < (Constants.tableWidth / 2) - (Constants.potDiameter / 2)) ||
                    (this.getX() + Constants.getRadius() > (Constants.tableWidth / 2) + (Constants.potDiameter / 2)
                            && this.getX()
                                    - Constants.getRadius() < Constants.tableWidth
                                            - (Constants.potDiameter / 2))) {
                ballDY *= -1;
                isMod = true;
            }
        }

        if (isMod) {
            this.setAngle(ballDX, ballDY);
            this.setMagnitude(ballDX, ballDY);
        }

        if (this.getY() + Constants.getRadius() > Constants.tableHeight)
            this.coordinate.setY(Constants.tableHeight - Constants.getRadius());
        else if (this.getY() - Constants.getRadius() < 0)
            this.coordinate.setY(Constants.getRadius());

        if (this.getX() + Constants.getRadius() > Constants.tableWidth)
            this.coordinate.setX(Constants.tableWidth - Constants.getRadius());
        else if (this.getX() - Constants.getRadius() < 0)
            this.coordinate.setX(Constants.getRadius());
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