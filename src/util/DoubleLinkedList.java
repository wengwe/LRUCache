package util;

import java.util.List;

/**
 * User: Jason Weng
 */
public interface DoubleLinkedList<T> {

    public T removeHead();

    public T removeTail();

    public boolean removeNode(Node node);

    public Node<T> addNodeAtFront(T element);

    public Node<T> addNodeAtEnd(T element);

    public Node<T> insertNodeBehind(Node node, T element);

    public int getSize();

    public List<T> getElementsByOrder();


    public static class Node<T> {
        int prevNodeIndex;
        int nextNodeIndex;
        int index;
        T element;

        public Node(int prev, int next, T element) {
            this.prevNodeIndex = prev;
            this.nextNodeIndex = next;
            this.element = element;
        }

        public Node(T element) {
            this.element = element;

        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }
    }

    ;

}
