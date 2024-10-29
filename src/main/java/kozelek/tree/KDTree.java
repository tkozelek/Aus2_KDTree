package kozelek.tree;

import kozelek.data.GPS;
import kozelek.data.objekt.Editable;
import kozelek.data.objekt.Entity;
import kozelek.data.objekt.IComparator;
import kozelek.data.objekt.IObject;

import java.util.LinkedList;
import java.util.Queue;

public class KDTree<T extends IComparator<T>> {
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
        Node<T> found = this.findNode(data);
        if (found != null) {
            return found.getBoundObject();
        }
        return null;
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

        if (nodeToDelete == null)
            return null; // neexistuje

        T boundObject = nodeToDelete.getBoundObject();

        if (nodeToDelete.isLeaf()) { // je listom
            nodeToDelete.removeFromParent();
            return boundObject;
        }

        // level % 2 = 0 -> x
        // level % 2 = 1 -> y
        // hladame v pravom podstrome najmensiu danu suradnicu
        Node<T> replacement = null;
        while (!nodeToDelete.isLeaf()) {
            if (nodeToDelete.getRightChild() != null)
                replacement = this.findMinNodeInSubtree(nodeToDelete.getRightChild(), nodeToDelete.getLevel());
            else
                replacement = this.findBigNodeInSubtree(nodeToDelete.getLeftChild(), nodeToDelete.getLevel());
//            if (!replacement.isLeaf()) {
//                System.out.println("nie je");
//            }
            if (nodeToDelete.equals(this.root)) {
                this.root = replacement;
            }
            nodeToDelete.swapNodes(replacement);
        }
        nodeToDelete.removeFromParent();
        nodeToDelete = null;
        return boundObject;
    }

    public Node<T> findMinNodeInSubtree(Node<T> startNode, int level) {
        Node<T> minNode = startNode;
        Queue<Node<T>> nodesToCheck = new LinkedList<>();
        nodesToCheck.add(startNode);

        while (!nodesToCheck.isEmpty()) {
            Node<T> currentNode = nodesToCheck.poll();

            if (currentNode.getBoundObject().compare(minNode.getBoundObject(), level) <= 0) {
                minNode = currentNode;
            }

            if (level % startNode.getBoundObject().getDimension() ==
                    currentNode.getLevel() % currentNode.getBoundObject().getDimension()) {
                if (currentNode.getLeftChild() != null) nodesToCheck.add(currentNode.getLeftChild());
            } else {
                if (currentNode.getLeftChild() != null) nodesToCheck.add(currentNode.getLeftChild());
                if (currentNode.getRightChild() != null) nodesToCheck.add(currentNode.getRightChild());
            }
        }

        return minNode;
    }

    public Node<T> findBigNodeInSubtree(Node<T> startNode, int level) {
        Node<T> minNode = startNode;
        Queue<Node<T>> nodesToCheck = new LinkedList<>();
        nodesToCheck.add(startNode);

        while (!nodesToCheck.isEmpty()) {
            Node<T> currentNode = nodesToCheck.poll();

            if (currentNode.getBoundObject().compare(minNode.getBoundObject(), level) >= 0) {
                minNode = currentNode;
            }

            if (level % startNode.getBoundObject().getDimension() ==
                    currentNode.getLevel() % currentNode.getBoundObject().getDimension()) {
                if (currentNode.getRightChild() != null) nodesToCheck.add(currentNode.getRightChild());
            } else {
                if (currentNode.getLeftChild() != null) nodesToCheck.add(currentNode.getLeftChild());
                if (currentNode.getRightChild() != null) nodesToCheck.add(currentNode.getRightChild());
            }

        }

        return minNode;
    }

    public void edit(T data, T dataNew) {
        Node<T> currentNode = this.findNode(data);
        if (data.equal(dataNew)) {
            currentNode.editBoundObjectData(dataNew);
        } else {
            this.delete(data);
            this.insert(dataNew);
        }
    }

    public Node<T> getRoot() {
        return root;
    }
}
