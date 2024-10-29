package kozelek.data.objekt;

import kozelek.data.GPS;

import java.io.Serializable;

public abstract class Entity implements HasBounds {
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
}
