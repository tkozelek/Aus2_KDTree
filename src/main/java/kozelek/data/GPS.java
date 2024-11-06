package kozelek.data;

import kozelek.data.objekt.Entity;

public class GPS {
    private double x,y;
    private char pozX, pozY;
    public GPS(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public double getX() {
        return this.pozX == 'E' ? this.x : -this.x;
    }

    public void setX(double x) {
        this.pozX = x > 0.0 ? 'E' : 'W';
        this.x = Math.abs(x);
    }

    public double getY() {
        return this.pozY == 'N' ? this.y : -this.y;
    }

    public void setY(double y) {
        this.pozY = y > 0.0 ? 'N' : 'S';
        this.y = Math.abs(y);
    }

    @Override
    public String toString() {
        return this.x + ", " + this.y;
    }


    public String toCsvString() {
        return this.getX() +
                Entity.CSV_DELIMITER + this.getY();
    }
}

