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
}
