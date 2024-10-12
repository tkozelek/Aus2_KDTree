package kozelek.tree;

import kozelek.data.objekt.Entity;

import java.util.ArrayList;

public class Node<T extends Entity> {
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Node<T> parent;

    private T boundObject;
    private ArrayList<T> duplicateList;
    private final int level;

    public Node(T boundObject, Node<T> parent, int level) {
        this.level = level;
        this.boundObject = boundObject;

        this.leftChild = null;
        this.rightChild = null;
        this.parent = parent;
    }

    public Node(Node<T> parent, int level) {
        this.level = level;
        this.boundObject = null;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = parent;

        this.duplicateList = new ArrayList<>();
    }

    public Node<T> getChildBasedOnCompare(T other) {
        if (this.boundObject == null) {
            this.boundObject = other;
            return null;
        }

        if (this.boundObject.compare(other, this.level) > 0) {
            if (this.leftChild == null) {
                this.leftChild = new Node<T>(other, this, level + 1);
                return null;
            } else
                return this.leftChild;
        } else if (this.boundObject.compare(other, this.level) <= 0) {
            if (this.rightChild == null) {
                this.rightChild = new Node<T>(other, this, level + 1);
                return null;
            } else {
                return this.rightChild;
            }
        } else {
            this.duplicateList.add(other);
            return null;
        }
    }

    public boolean equals (T other) {
        if (this.boundObject == null) return false;

        return this.boundObject.equals(other);
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public Node<T> getParent() {
        return parent;
    }

    public int getLevel() {
        return level;
    }

    public T getBoundObject() {
        return boundObject;
    }
}
