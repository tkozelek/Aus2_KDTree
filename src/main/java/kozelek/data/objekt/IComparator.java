package kozelek.data.objekt;

import kozelek.data.GPS;

public interface IComparator<T> {
    int compare(T other, int level);
    boolean equalKey(T other);
    boolean equalUUID(T other);
    int getDimension();
}
