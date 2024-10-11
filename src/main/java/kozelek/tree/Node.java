package kozelek.tree;

public class Node<T> {
    private Node<T> leftChild;
    private Node<T> rightChild;
    private Node<T> parent;

    private final int level;

    public Node(int level) {
        this.level = level;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }
}
