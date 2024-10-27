package kozelek.data.objekt;

import kozelek.data.GPS;

public abstract class Entity implements HasBounds, IComparator<Entity> {
    private GPS gps;

    public Entity(GPS gps) {
        this.gps = gps;
    }

    @Override
    public GPS getGPS() {
        return this.gps;
    }

    @Override
    public void setGPS(GPS gps) {
        this.gps = gps;
    }

    @Override
    public boolean equal(Entity other) {
        return Double.compare(this.gps.getX(), other.getGPS().getX()) == 0 &&
                Double.compare(this.gps.getY(), other.getGPS().getY()) == 0;
    }

    @Override
    public int compare(Entity other, int level) {
        return switch (level % 2) {
            case 0 -> Double.compare(this.getGPS().getX(), other.getGPS().getX());
            case 1 -> Double.compare(this.getGPS().getY(), other.getGPS().getY());
            default -> 0;
        };
    }
}
