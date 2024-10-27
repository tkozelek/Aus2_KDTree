package kozelek.data;

import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;

import java.util.ArrayList;

public class Nehnutelnost extends Entity {

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


}
