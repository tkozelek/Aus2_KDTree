package kozelek.data.objekt;

import kozelek.data.Nehnutelnost;

import java.util.ArrayList;

public interface IObject {
    int getID();
    String getDescription();
    //ArrayList<IObject> getObjects();
    String toString();
}
