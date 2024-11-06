package kozelek.data;

import kozelek.data.objekt.*;

import java.util.ArrayList;

public class Nehnutelnost extends Entity implements IObject, ISave {

    private ArrayList<Parcela> parcely;
    private int id;
    private String description;
    public static final String startingString = "Neh";

    public Nehnutelnost(int id, String description, GPS gps) {
        super(gps);
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Nehnutelnost " +
                "id=" + this.id +
                ", description='" + this.description + '\'' +
                " x = " + this.getGPS();
    }

    public ArrayList<Parcela> getParcely() {
        return this.parcely;
    }

    public void addParcela(Parcela parcela) {
        this.parcely.add(parcela);
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
        return  this.id + Entity.CSV_DELIMITER +
                this.description + Entity.CSV_DELIMITER +  // .replace(",", ";") ak by sme do popisu vkladali čiarky :)
                this.getGPS().toCsvString() + Entity.CSV_DELIMITER +
                this.getUuid().toString() + Entity.CSV_DELIMITER +
                other.getGPS().toCsvString() + Entity.CSV_DELIMITER +
                other.getUuid().toString() +
                "\n";
    }

}
