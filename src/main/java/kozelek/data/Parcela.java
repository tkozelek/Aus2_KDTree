package kozelek.data;

import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;

import java.util.ArrayList;

public class Parcela extends Entity {

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
        return "Nehnutelnost " +
                "id=" + this.id +
                ", description='" + this.description + '\'' +
                " x = " + this.getGPS();
    }

    public ArrayList<Nehnutelnost> getNehnutelnosti() {
        return this.nehnutelnosti;
    }
}
