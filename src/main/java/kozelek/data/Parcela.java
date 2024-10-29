package kozelek.data;

import kozelek.data.objekt.*;

import java.util.ArrayList;

public class Parcela extends Entity implements IComparator<Parcela>, Editable<Parcela> {

    private ArrayList<Nehnutelnost> nehnutelnosti;
    private int id;
    private String description;

    public Parcela(int id, String description, GPS gps) {
        super(gps);
        this.nehnutelnosti = new ArrayList<>();
        this.id = id;
        this.description = description;
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

    @Override
    public boolean equal(Parcela other) {
        return Double.compare(this.getGPS().getX(), other.getGPS().getX()) == 0 &&
                Double.compare(this.getGPS().getY(), other.getGPS().getY()) == 0 &&
                this.id == other.id && this.description.equals(other.description);
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public int compare(Parcela other, int level) {
        return switch (level % 2) {
            case 0 -> Double.compare(this.getGPS().getX(), other.getGPS().getX()); // d1 > d2 = +
            case 1 -> Double.compare(this.getGPS().getY(), other.getGPS().getY()); // d1 < d2 = -
            default -> 0;
        };
    }

    @Override
    public void edit(Parcela obj) {
        this.id = obj.id;
        this.description = obj.description;
        this.setGPS(obj.getGPS());
    }
}
