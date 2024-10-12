package kozelek.tree;

import kozelek.data.objekt.Entity;

public class KDTree<T extends Entity> {
    private Node<T> root;

    public KDTree() {
        this.root = new Node<>(null, 0);
    }

    public void insert(T data) {
        Node<T> currentNode = root;
        while (true) {
            currentNode = currentNode.getChildBasedOnCompare(data);
            if (currentNode == null) {
                break;
            }
        }
    }
}
