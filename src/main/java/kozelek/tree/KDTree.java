package kozelek.tree;

import kozelek.data.GPS;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;

public class KDTree<T extends Entity> {
    private Node<T> root;

    public KDTree() {
        this.root = new Node<>(null, 0);
    }

    public void insert(T data) {
        Node<T> currentNode = root;
        while (true) {
            currentNode = currentNode.getOrCreateChildBasedOnCompare(data);
            if (currentNode == null) {
                break;
            }
        }
    }

    public T find(T data) {
        return this.findNode(data).getBoundObject();
    }

    public Node<T> findNode(T data) {
        Node<T> currentNode = root;
        if (currentNode.equals(data)) {
            return currentNode;
        }
        while (true) {
            Node<T> lastNode = currentNode;
            currentNode = currentNode.getChildOnCompare(data);
            if (currentNode == null) {
                return lastNode.equals(data) ? lastNode : null;
            } else {
                if (currentNode.equals(data)) {
                    return currentNode;
                }
            }
        }
    }

    public T delete(T data) {
        Node<T> nodeToDelete = this.findNode(data);

        if (nodeToDelete != null && nodeToDelete.isLeaft()) { // je listom
            T boundObject = nodeToDelete.getBoundObject();
            nodeToDelete = null;
            return boundObject;
        }

        // level % 2 = 0 -> x
        // level % 2 = 1 -> y
        // hladame v pravom podstrome najmensiu danu suradnicu


        return null;
    }
}
