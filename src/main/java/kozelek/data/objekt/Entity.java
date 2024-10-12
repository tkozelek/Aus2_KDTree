package kozelek.data.objekt;

import kozelek.data.GPS;

public class Entity implements HasBounds, IComparator<IObject> {
    private GPS topLeft;
    private GPS bottomRight;

    public Entity(GPS topLeft, GPS bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public boolean equalsGPS(Entity other) {
        if (this.topLeft.getX() != other.getTopLeft().getX() ||
                this.topLeft.getY() != other.getTopLeft().getY() ||
                this.bottomRight.getX() != other.getBottomRight().getX() ||
                this.bottomRight.getY() != other.getBottomRight().getY()) {
            return false;
        }
        return true;
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
    public int compare(Entity other, int level) {
        if (level % 2 == 0) {
            return Double.compare(this.topLeft.getX(), other.getTopLeft().getX());
        } else {
            return Double.compare(this.topLeft.getY(), other.getTopLeft().getY());
        }
    }
}
