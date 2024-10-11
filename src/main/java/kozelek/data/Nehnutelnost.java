package kozelek.data;

import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IObject;

import java.util.ArrayList;

public class Nehnutelnost extends Entity implements IObject {
    private int id;
    private String description;
    private ArrayList<Parcela> parcely;
    public Nehnutelnost(int id, String description, GPS topLeft, GPS bottomRight) {
        super(topLeft, bottomRight);
        this.id = id;
        this.description = description;
    }


    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public ArrayList<Parcela> getParcely() {
        return this.parcely;
    }
}
