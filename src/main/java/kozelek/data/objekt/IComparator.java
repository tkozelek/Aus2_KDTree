package kozelek.data.objekt;

import kozelek.data.GPS;

public interface IComparator<T> {
    int compare(T other, int level, boolean isLeft);
    int compare(GPS gps, int level, boolean isLeft);
}
