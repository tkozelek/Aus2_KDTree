package kozelek.data.objekt;

import kozelek.data.GPS;
import kozelek.data.Nehnutelnost;
import kozelek.data.Parcela;

import java.util.UUID;

public class GPSObjekt implements IObject {
    private Nehnutelnost nehnutelnost;
    private Parcela parcela;
    private GPSObjekt other;

    public GPSObjekt(Nehnutelnost nehnutelnost, Parcela parcela) {
        this.nehnutelnost = nehnutelnost;
        this.parcela = parcela;
    }


    public Nehnutelnost getNehnutelnost() {
        return nehnutelnost;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setOther(GPSObjekt other) {
        this.other = other;
    }

    public GPSObjekt getOther() {
        return other;
    }

    public GPS getGPS() {
        if (this.parcela != null) {
            return this.parcela.getGPS();
        } else if (this.nehnutelnost != null) {
            return this.nehnutelnost.getGPS();
        }
        return null;
    }

    public void setGPS(GPS gps) {
        if (this.parcela != null) {
            this.parcela.setGPS(gps);
        } else if (this.nehnutelnost != null) {
            this.nehnutelnost.setGPS(gps);
        }
    }

    public UUID getUUID() {
        if (this.parcela != null) {
            return this.parcela.getUuid();
        } else if (this.nehnutelnost != null) {
            return this.nehnutelnost.getUuid();
        }
        return null;
    }

    @Override
    public int compare(IObject other, int level) {
        if (other instanceof GPSObjekt gps) {
            if (this.parcela != null) {
                return switch (level % 2) {
                    case 0 -> Math.abs(this.getGPS().getX() - gps.getGPS().getX()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getX(), gps.getGPS().getX()); // d1 > d2 = +
                    case 1 -> Math.abs(this.getGPS().getY() - gps.getGPS().getY()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getY(), gps.getGPS().getY()); // d1 < d2 = -
                    default -> 0;
                };
            } else if (this.nehnutelnost != null) {
                return switch (level % 2) {
                    case 0 -> Math.abs(this.getGPS().getX() - gps.getGPS().getX()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getX(), gps.getGPS().getX()); // d1 > d2 = +
                    case 1 -> Math.abs(this.getGPS().getY() - gps.getGPS().getY()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getY(), gps.getGPS().getY()); // d1 < d2 = -
                    default -> 0;
                };
            }
        }
        return 0;
    }

    @Override
    public boolean equalKey(IObject other) {
        if (other instanceof GPSObjekt gps) {
            return Math.abs(this.getGPS().getX() - gps.getGPS().getX()) < Entity.EPSILON &&
                    Math.abs(this.getGPS().getY() - gps.getGPS().getY()) < Entity.EPSILON;
        }
        return false;
    }

    @Override
    public boolean equalUUID(IObject other) {
        if (other instanceof GPSObjekt gps) {
            return this.getUUID().compareTo(gps.getUUID()) == 0;
        }
        return false;
    }

    @Override
    public int getDimension() {
        return 2;
    }
}
