package kozelek.data.objekt;

public interface IComparator<T> {
    int compare(Entity other, int level);
}
