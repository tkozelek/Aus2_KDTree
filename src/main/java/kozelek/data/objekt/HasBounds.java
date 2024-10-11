package kozelek.data.objekt;

import kozelek.data.GPS;

public interface HasBounds {
    GPS getTopLeft();
    GPS getBottomRight();
    void setTopLeft(GPS topLeft);
    void setBottomRight(GPS bottomRight);
    boolean isTouching(GPS point, GPS point2);
}
