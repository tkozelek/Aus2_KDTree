package kozelek.tree;

import kozelek.data.objekt.IObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class KDTree<T extends IObject>  {
    private Node<T> root;

    public KDTree() {
        this.root = new Node<>(null, 0);
    }

    public void insert(T data) {
        Node<T> currentNode = root;
        if (currentNode == null) {
            this.root = new Node<>(null, 0);
            currentNode = this.root;
        }
        do {
            currentNode = currentNode.getOrCreateChildBasedOnCompare(data);
        } while (currentNode != null);
    }

    public ArrayList<T> find(T data) {
        ArrayList<Node<T>> found = this.findNodes(data);
        if (found != null) {
            ArrayList<T> result = new ArrayList<>();
            for (Node<T> node : found) {
                result.add(node.getBoundObject());
            }
            return result;
        }
        return null;
    }

    public ArrayList<Node<T>> findNodes(T data) {
        Node<T> currentNode = root;
        ArrayList<Node<T>> found = new ArrayList<>();
        if (currentNode.equals(data)) {
            found.add(currentNode);
        }
        while (true) {
            currentNode = currentNode.getChildOnCompare(data);
            if (currentNode != null) {
                if (currentNode.equals(data)) {
                    found.add(currentNode);
                }
            } else
                break;
        }
        return found;
    }

    public Node<T> findOneNode(T data) {
        Node<T> currentNode = root;
        if (currentNode.equalsId(data)) {
            return currentNode;
        }
        while (true) {
            Node<T> lastNode = currentNode;
            currentNode = currentNode.getChildOnCompare(data);
            if (currentNode == null) {
                return lastNode.equalsId(data) ? lastNode : null;
            } else {
                if (currentNode.equalsId(data)) {
                    return currentNode;
                }
            }
        }
    }

    public T delete(T data) {
        Node<T> nodeToDelete = this.findOneNode(data);

        if (nodeToDelete == null) {
            return null; // neexistuje
        }

        T boundObject = nodeToDelete.getBoundObject();

        ArrayList<Node<T>> replacements = null;
        Node<T> rep = null;
        // zmažeme node, všetky náhrady sa uložia tu
        replacements = this.deleteNode(nodeToDelete);
        ArrayList<Node<T>> duplicates = null;
        while (replacements != null && !replacements.isEmpty()) {
            // pre tieto náhrady musíme v pravom podstrome najsť duplikáty
            rep = replacements.removeLast(); // zoberieme replacement
            if (rep.getRightChild() != null)
                duplicates = this.findSameNodesInSubtree(rep, rep.getRightChild(), rep.getLevel());

            if (duplicates != null) { // ak sme našli duplikáty
                for (Node<T> duplicate : duplicates) { // prejdeme cez ne a zmažeme ich
                    ArrayList<Node<T>> reps = this.deleteNode(duplicate);
                    if (reps != null) {
                        replacements.addAll(reps); // do náhrad, pridáme náhrady zmazavaných duplikátov
                    }
                    this.insert(duplicate.getBoundObject());
                }
                duplicates = null;
            }
        }
        return boundObject;
    }

    public ArrayList<Node<T>> deleteNode(Node<T> nodeToDelete) {
        if (nodeToDelete.isLeaf()) { // je listom
            if (nodeToDelete.equals(this.root))
                this.root = null;
            nodeToDelete.removeFromParent();
            return null;
        }

        // level % 2 = 0 -> x
        // level % 2 = 1 -> y
        // hladame v pravom podstrome najmensiu danu suradnicu
        // resp v lavom najvacsiu
        ArrayList<Node<T>> replacements = new ArrayList<>();
        while (!nodeToDelete.isLeaf()) {
            Node<T> replacement = null;

            if (nodeToDelete.getRightChild() != null)
                replacement = this.findMinNodeInSubtree(nodeToDelete.getRightChild(), nodeToDelete.getLevel());
            else if (nodeToDelete.getLeftChild() != null)
                replacement = this.findBigNodeInSubtree(nodeToDelete.getLeftChild(), nodeToDelete.getLevel());

            if (nodeToDelete.equals(this.root)) {
                this.root = replacement;
            }

            if (replacement != null) {
                replacements.add(replacement); // prva nahrada, podla ktorej musime hladat
                nodeToDelete.swapNodes(replacement); // vymena nahrady za zmazavany node
            }

        }
        nodeToDelete.removeFromParent();
        return replacements;
    }

    public ArrayList<Node<T>> findSameNodesInSubtree(Node<T> comparingNode, Node<T> startNode, int level) {
        ArrayList<Node<T>> found = new ArrayList<>();
        Queue<Node<T>> nodesToCheck = new LinkedList<>();
        nodesToCheck.add(startNode);

        while (!nodesToCheck.isEmpty()) {
            Node<T> currentNode = nodesToCheck.poll();

            if (comparingNode.getBoundObject().compare(currentNode.getBoundObject(), level) == 0) {
                found.add(currentNode);
            }

            int currentDimension = currentNode.getLevel() % currentNode.getBoundObject().getDimension();
            int searchingDimension = level % comparingNode.getBoundObject().getDimension();

            // ak sme na rovnakej urovni (napr. x), pokračujeme len vľavo
            if (currentDimension == searchingDimension) {
                if (currentNode.getLeftChild() != null) nodesToCheck.add(currentNode.getLeftChild());
            } else { // musime pozriet obe
                if (currentNode.getLeftChild() != null) nodesToCheck.add(currentNode.getLeftChild());
                if (currentNode.getRightChild() != null) nodesToCheck.add(currentNode.getRightChild());
            }
        }

        return found;
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

            int currentDimension = currentNode.getLevel() % currentNode.getBoundObject().getDimension();
            int searchingDimension = level % startNode.getBoundObject().getDimension();

            if (currentDimension == searchingDimension) {
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

            int currentDimension = currentNode.getLevel() % currentNode.getBoundObject().getDimension();
            int searchingDimension = level % startNode.getBoundObject().getDimension();

            if (currentDimension == searchingDimension) {
                if (currentNode.getRightChild() != null) nodesToCheck.add(currentNode.getRightChild());
            } else {
                if (currentNode.getLeftChild() != null) nodesToCheck.add(currentNode.getLeftChild());
                if (currentNode.getRightChild() != null) nodesToCheck.add(currentNode.getRightChild());
            }
        }

        return minNode;
    }

    public ArrayList<Node<T>> inOrder(Node<T> startNode) {
        Stack<Node<T>> stack = new Stack<>();
        ArrayList<Node<T>> found = new ArrayList<>();
        Node<T> current = startNode;

        while (current != null || !stack.isEmpty()) {
            // čo najviac doľava
            while (current != null) {
                stack.push(current);
                current = current.getLeftChild();
            }

            // spracuj
            current = stack.pop();
            found.add(current);

            // pravy podstrom
            current = current.getRightChild();
        }

        return found;
    }

    public ArrayList<Node<T>> levelOrder(Node<T> startNode) {
        ArrayList<Node<T>> found = new ArrayList<>();
        Stack<Node<T>> stack = new Stack<>();
        if (startNode == null) {
            return found;
        }
        stack.push(startNode);

        while (!stack.isEmpty()) {
            ArrayList<Node<T>> currentNodes = new ArrayList<>();

            int numberOfChildren = stack.size();

            for (int i = 0; i < numberOfChildren; i++) {
                Node<T> currentNode = stack.pop();
                currentNodes.add(currentNode);

                if (currentNode.getRightChild() != null) { // napr root, ak ma praveho, pridame do stacku
                    stack.push(currentNode.getRightChild());
                }

                if (currentNode.getLeftChild() != null) { // napr root, ak ma laveho, pridame do stacku
                    stack.push(currentNode.getLeftChild());
                }
            }

            found.addAll(currentNodes);
        }

        return found;
    }

    public void edit(T data, T dataNew) {
        Node<T> currentNode = this.findOneNode(data);
        if (data.equalKey(dataNew)) {
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
