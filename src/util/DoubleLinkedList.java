package util;

import java.util.List;

/**
 * User: Jason Weng
 */
public interface DoubleLinkedList<T> {

    public T removeHead();

    public T removeTail();

    public Node addNodeAtFront(T element);

    public Node addNodeAtEnd(T element);

    public Node insertNodeBehind(Node node, T element);

    public int getSize();

    public List<T> getElementsByOrder();


    public class Node<T> {

    }

    ;

}
