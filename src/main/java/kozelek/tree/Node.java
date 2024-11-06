package kozelek.tree;

import kozelek.data.GPS;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;

import java.util.ArrayList;

public class Node<T extends IObject> {
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Node<T> parent;


    private T boundObject;
    private int level;

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
    }

    public Node<T> getOrCreateChildBasedOnCompare(T other) {
        if (this.boundObject == null) {
            this.boundObject = other;
            return null;
        }

        if (this.boundObject.compare(other, this.level) >= 0) {
            if (this.leftChild == null) {
                this.leftChild = new Node<T>(other, this, level + 1);
                return null;
            } else
                return this.leftChild;
        } else if (this.boundObject.compare(other, this.level) < 0) {
            if (this.rightChild == null) {
                this.rightChild = new Node<T>(other, this, level + 1);
                return null;
            } else {
                return this.rightChild;
            }
        }
        return null;
    }

    public Node<T> getChildOnCompare(T data) { // traverse throught the tree
        if (this.boundObject == null)
            return null;
        if (this.boundObject.compare(data, this.level) >= 0) {
            return this.leftChild;
        } else if (this.boundObject.compare(data, this.level) < 0) {
            return this.rightChild;
        }
        return null;
    }

    public boolean equals (T other) {
        if (this.boundObject == null) return false;

        return this.boundObject.equalKey(other);
    }

    public boolean equalsId(T other) {
        if (this.boundObject == null) return false;

        return this.boundObject.equalUUID(other);
    }

    public boolean isLeaf() {
        return this.rightChild == null && this.leftChild == null;
    }

    public void removeFromParent() {
        if (this.parent != null) {
            if (this == this.parent.leftChild) {
                this.parent.leftChild = null;
            } else {
                this.parent.rightChild = null;
            }
        }
    }

    public void swapNodes(Node<T> other) {
        // swap parent references
        Node<T> tempParent = this.parent;
        this.parent = other.parent;
        other.parent = tempParent;

        // swap parent references
        if (this.parent != null) {
            if (this.parent.leftChild == other) {
                this.parent.leftChild = this;
            } else if (this.parent.rightChild == other) {
                this.parent.rightChild = this;
            }
        }
        if (other.parent != null) {
            if (other.parent.leftChild == this) {
                other.parent.leftChild = other;
            } else if (other.parent.rightChild == this) {
                other.parent.rightChild = other;
            }
        }

        // swap children
        Node<T> tempLeftChild = this.leftChild;
        Node<T> tempRightChild = this.rightChild;
        this.leftChild = other.leftChild;
        this.rightChild = other.rightChild;
        other.leftChild = tempLeftChild;
        other.rightChild = tempRightChild;

        // update children parent references
        if (this.leftChild != null) this.leftChild.parent = this;
        if (this.rightChild != null) this.rightChild.parent = this;
        if (other.leftChild != null) other.leftChild.parent = other;
        if (other.rightChild != null) other.rightChild.parent = other;

        // swap levels
        int tempLevel = this.level;
        this.level = other.level;
        other.level = tempLevel;
    }

    public T getBoundObject() {
        return boundObject;
    }

    public int getLevel() {
        return level;
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

    public void editBoundObjectData(T dataNew) { // only if key is the same
        this.boundObject = dataNew;
    }

    @Override
    public String toString() {
        return "NboundObject=" + boundObject;
    }
}
