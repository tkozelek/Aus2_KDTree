package kozelek.tree;

import kozelek.data.GPS;
import kozelek.data.objekt.Entity;

public class KDTree<T extends Entity> {
    private Node<T> root;

    public KDTree() {
        this.root = new Node<>(null, 0);
    }

    public void insert(T data, boolean isLeft) {
        Node<T> currentNode = root;
        while (true) {
            currentNode = currentNode.getOrCreateChildBasedOnCompare(data, isLeft);
            if (currentNode == null) {
                break;
            }
        }
    }

    public T find(T data, boolean isLeft) {
        Node<T> currentNode = root;
        while (true) {
            Node<T> lastNode = currentNode;
            currentNode = currentNode.getChildOnCompare(data, isLeft);
            if (currentNode == null) {
                return lastNode.getBoundObject();
            }
        }
    }

    public T findByCord(GPS gps, boolean isLeft) {
        Node<T> currentNode = root;
        while (true) {
            if (currentNode != null && currentNode.equals(gps)) {
                return currentNode.getBoundObject();
            }
            if (currentNode == null) {
                return null;
            }
            currentNode = currentNode.getChildOnCompare(gps, isLeft);
        }
    }
}
