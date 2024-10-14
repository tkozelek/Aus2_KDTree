package kozelek.tree;

import kozelek.data.GPS;
import kozelek.data.objekt.Entity;

public class TreeWrapper<T extends Entity> {
    private KDTree<T> topLeftTree;
    private KDTree<T> bottomRightTree;

    public TreeWrapper() {
        this.topLeftTree = new KDTree<>();
        this.bottomRightTree = new KDTree<>();
    }

    public void insert(T data) {
        this.topLeftTree.insert(data, true);
        this.bottomRightTree.insert(data, false);
    }

    public T find(GPS gps, boolean isLeft) {
        return isLeft ? this.topLeftTree.findByCord(gps, true) : this.bottomRightTree.findByCord(gps, false);
    }
}
