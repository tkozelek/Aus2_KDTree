package kozelek.data.objekt;

import kozelek.data.GPS;
import kozelek.data.objekt.interfaces.IHasOther;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements IHasOther {
    private GPS gps;
    private IHasOther other;
    private int id;
    private String description;

    private UUID uuid;

    public static final double EPSILON = 0.000001d;
    public static final String CSV_DELIMITER = ";";

    public Entity(GPS gps, int id, String description) {
        this.gps = gps;
        this.id = id;
        this.description = description;
        this.uuid = UUID.randomUUID();
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public GPS getGPS() {
        return this.gps;
    }

    public void setGPS(GPS gps) {
        this.gps = gps;
    }

    @Override
    public void setOther(IHasOther other) {
        this.other = other;
    }

    @Override
    public IHasOther getOther() {
        return other;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
