package kozelek.data;

import kozelek.data.objekt.*;

import java.util.ArrayList;

public class Nehnutelnost extends Entity implements IComparator<Nehnutelnost>, Editable<Nehnutelnost> {

    private ArrayList<Parcela> parcely;
    private int id;
    private String description;

    public Nehnutelnost(int id, String description, GPS gps) {
        super(gps);
        this.id = id;
        this.description = description;
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

    @Override
    public boolean equal(Nehnutelnost other) {
        return Double.compare(this.getGPS().getX(), other.getGPS().getX()) == 0 &&
                Double.compare(this.getGPS().getY(), other.getGPS().getY()) == 0;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public int compare(Nehnutelnost other, int level) {
        return switch (level % 2) {
            case 0 -> Double.compare(this.getGPS().getX(), other.getGPS().getX()); // d1 > d2 = +
            case 1 -> Double.compare(this.getGPS().getY(), other.getGPS().getY()); // d1 < d2 = -
            default -> 0;
        };
    }

    @Override
    public void edit(Nehnutelnost obj) {
        this.id = obj.id;
        this.description = obj.description;
        this.setGPS(obj.getGPS());
    }
}
