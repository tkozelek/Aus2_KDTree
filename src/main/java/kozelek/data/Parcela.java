package kozelek.data;

import kozelek.data.objekt.*;

import java.util.ArrayList;

public class Parcela extends Entity implements IObject, ISave {

    private ArrayList<Nehnutelnost> nehnutelnosti;
    private int id;
    private String description;

    public Parcela(int id, String description, GPS gps) {
        super(gps);
        this.nehnutelnosti = new ArrayList<>();
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Parcela " +
                "id=" + this.id +
                ", description='" + this.description + '\'' +
                " x = " + this.getGPS();
    }

    public ArrayList<Nehnutelnost> getNehnutelnosti() {
        return this.nehnutelnosti;
    }

    public void addNehnutelnost(Nehnutelnost nehnutelnost) {
        this.nehnutelnosti.add(nehnutelnost);
    }

    @Override
    public boolean equalKey(IObject other) {
        if (!(other instanceof Parcela otherParcela)) {
            throw new ClassCastException("Incompatible types.");
        }
        return Math.abs(this.getGPS().getX() - otherParcela.getGPS().getX()) < Entity.EPSILON &&
                Math.abs(this.getGPS().getY() - otherParcela.getGPS().getY()) < Entity.EPSILON;
    }

    @Override
    public boolean equalUUID(IObject other) {
        if (!(other instanceof Parcela otherParcela)) {
            throw new ClassCastException("Incompatible types.");
        }
        return this.getUuid().compareTo(otherParcela.getUuid()) == 0;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public int compare(IObject other, int level) {
        if (!(other instanceof Parcela otherParcela)) {
            throw new ClassCastException("Incompatible types.");
        }
        return switch (level % 2) {
            case 0 -> Math.abs(this.getGPS().getX() - otherParcela.getGPS().getX()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getX(), otherParcela.getGPS().getX()); // d1 > d2 = +
            case 1 -> Math.abs(this.getGPS().getY() - otherParcela.getGPS().getY()) < Entity.EPSILON ? 0 : Double.compare(this.getGPS().getY(), otherParcela.getGPS().getY()); // d1 < d2 = -
            default -> 0;
        };
    }

    // starting char, id, popis, x, y, uuid, (other)x, y, uuid

    @Override
    public String toCsvString() {
        Parcela other = (Parcela) this.getOther();
        return  this.id + Entity.CSV_DELIMITER +
                this.description + Entity.CSV_DELIMITER +  // .replace(",", ";") ak by sme do popisu vkladali čiarky :)
                this.getGPS().toCsvString() + Entity.CSV_DELIMITER +
                this.getUuid().toString() + Entity.CSV_DELIMITER +
                other.getGPS().toCsvString() + Entity.CSV_DELIMITER +
                other.getUuid().toString() +
                "\n";
    }
}
