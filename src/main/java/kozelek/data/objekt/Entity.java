package kozelek.data.objekt;

import kozelek.data.GPS;

public class Entity implements HasBounds, IComparator<Entity> {
    private GPS topLeft;
    private GPS bottomRight;

    public Entity(GPS topLeft, GPS bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public boolean equalsGPS(GPS gps) {
        return (this.topLeft.getX() == gps.getX() &&
                this.topLeft.getY() == gps.getY()) ||
                (this.bottomRight.getX() == gps.getX() &&
                this.bottomRight.getY() == gps.getY());
    }

    @Override
    public GPS getTopLeft() {
        return this.topLeft;
    }

    @Override
    public GPS getBottomRight() {
        return this.bottomRight;
    }

    @Override
    public void setTopLeft(GPS topLeft) {
        this.topLeft = topLeft;
    }

    @Override
    public void setBottomRight(GPS bottomRight) {
        this.bottomRight = bottomRight;
    }

    @Override
    public boolean isTouching(GPS point, GPS point2) {
        return false;
    }

    @Override
    public int compare(Entity other, int level, boolean isLeft) {
        if (isLeft) {
            return switch (level % 2) {
                case 0 -> Double.compare(this.topLeft.getX(), other.getTopLeft().getX());
                case 1 -> Double.compare(this.topLeft.getY(), other.getTopLeft().getY());
                default -> 0;
            };
        } else {
            return switch (level % 2) {
                case 0 -> Double.compare(this.bottomRight.getX(), other.getBottomRight().getX());
                case 1 -> Double.compare(this.bottomRight.getY(), other.getBottomRight().getY());
                default -> 0;
            };
        }
    }

    @Override
    public int compare(GPS gps, int level, boolean isLeft) {
        if (isLeft) {
            return switch (level % 2) {
                case 0 -> Double.compare(this.topLeft.getX(), gps.getX());
                case 1 -> Double.compare(this.topLeft.getY(), gps.getY());
                default -> 0;
            };
        } else {
            return switch (level % 2) {
                case 0 -> Double.compare(this.bottomRight.getX(), gps.getX());
                case 1 -> Double.compare(this.bottomRight.getY(), gps.getY());
                default -> 0;
            };
        }
    }
}
