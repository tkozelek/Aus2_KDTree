package kozelek.data.objekt;

import kozelek.data.GPS;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity {
    private GPS gps;
    private Entity other;

    private UUID uuid;

    public static final double EPSILON = 0.000001d;
    public static final String CSV_DELIMITER = ";";

    public Entity(GPS gps) {
        this.gps = gps;
        this.uuid = UUID.randomUUID();
    }

    public GPS getGPS() {
        return this.gps;
    }

    public void setGPS(GPS gps) {
        this.gps = gps;
    }

    public void setOther(Entity other) {
        this.other = other;
    }

    public Entity getOther() {
        return other;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
