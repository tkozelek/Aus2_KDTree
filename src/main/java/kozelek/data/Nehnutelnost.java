package kozelek.data;

import kozelek.data.objekt.*;
import kozelek.data.objekt.interfaces.IObject;
import kozelek.data.objekt.interfaces.ISave;

import java.util.ArrayList;

public class Nehnutelnost extends Entity implements IObject, ISave {

    private ArrayList<Parcela> parcely;
    public static final String startingString = "Neh";

    public Nehnutelnost(int id, String description, GPS gps) {
        super(gps, id, description);
        this.parcely = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Nehnutelnost " +
                "id=" + this.getId() +
                ", description='" + this.getDescription() + '\'' +
                " x = " + this.getGPS();
    }

    public ArrayList<Parcela> getParcely() {
        return this.parcely;
    }

    public void addParcela(Parcela parcela) {
        this.parcely.add(parcela);
    }

    public void removeParcela(Parcela parcela) {
        this.parcely.remove(parcela);
    }

    @Override
    public boolean equalKey(IObject other) {
        if (!(other instanceof Nehnutelnost otherNehnutelnost)) {
            throw new ClassCastException("Incompatible types.");
        }
        return Math.abs(this.getGPS().getX() - otherNehnutelnost.getGPS().getX()) < Entity.EPSILON &&
                Math.abs(this.getGPS().getY() - otherNehnutelnost.getGPS().getY()) < Entity.EPSILON;
    }

    @Override
    public boolean equalUUID(IObject other) {
        if (!(other instanceof Nehnutelnost otherNehnutelnost)) {
            throw new ClassCastException("Incompatible types.");
        }
        return this.getUuid().compareTo(otherNehnutelnost.getUuid()) == 0;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public int compare(IObject other, int level) {
        if (!(other instanceof Nehnutelnost otherNehnutelnost)) {
            throw new ClassCastException("Incompatible types.");
        }
        return switch (level % 2) {
            case 0 -> Math.abs(this.getGPS().getX() - otherNehnutelnost.getGPS().getX()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getX(), otherNehnutelnost.getGPS().getX()); // d1 > d2 = +
            case 1 -> Math.abs(this.getGPS().getY() - otherNehnutelnost.getGPS().getY()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getY(), otherNehnutelnost.getGPS().getY()); // d1 < d2 = -
            default -> 0;
        };
    }

    @Override
    public String toCsvString() {
        Nehnutelnost other = (Nehnutelnost) this.getOther();
        return  this.getId() + Entity.CSV_DELIMITER +
                this.getDescription() + Entity.CSV_DELIMITER +  // .replace(",", ";") ak by sme do popisu vkladali čiarky :)
                this.getGPS().toCsvString() + Entity.CSV_DELIMITER +
                this.getUuid().toString() + Entity.CSV_DELIMITER +
                other.getGPS().toCsvString() + Entity.CSV_DELIMITER +
                other.getUuid().toString() +
                "\n";
    }

}
