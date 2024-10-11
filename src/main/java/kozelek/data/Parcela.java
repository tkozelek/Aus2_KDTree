package kozelek.data;

import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IObject;

import java.util.ArrayList;


public class Parcela extends Entity implements IObject {
    private int id;
    private String description;
    private ArrayList<Nehnutelnost> nehnutenosti;
    public Parcela(int id, String description, GPS topLeft, GPS bottomRight) {
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

    public ArrayList<Nehnutelnost> getNehnutenosti() {
        return this.nehnutenosti;
    }
}
