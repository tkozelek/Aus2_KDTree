package kozelek.tree;

import kozelek.data.objekt.Entity;

public class Node<T extends Entity> {
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Node<T> parent;

    private T boundObject;

    private final int level;

    public Node(int level) {
        this.level = level;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }
}
