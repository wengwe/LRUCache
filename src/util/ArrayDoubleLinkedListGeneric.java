package util;

import java.util.*;

/**
 * User: Jason Weng
 */
public class ArrayDoubleLinkedListGeneric<T> implements DoubleLinkedList<T> {

    private final int capacity;
    private Object[] nodeArray;
    private int size;
    private int LIST_HEAD_NODE_INDEX;

    private int LIST_TAIL_NODE_INDEX;

    private Queue<Integer> availabe_position;

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;


    public ArrayDoubleLinkedListGeneric(int capacity) {
        this.capacity = capacity > 2 ? capacity : 2;
        size = 0;
        this.nodeArray = new Object[this.capacity];
        LIST_TAIL_NODE_INDEX = -1;
        LIST_HEAD_NODE_INDEX = -1;
        availabe_position = new ArrayDeque<Integer>();
        //initialize memory at the beginning
        for (int i = 0; i < capacity; i++) {
            nodeArray[i] = new Node<T>(-1, -1, null);
            availabe_position.add(i);
        }

    }

    @Override
    public boolean removeNode(Node node) {
        if (node == null) return false;
        int index = node.index;
        if (availabe_position.contains(index)) return false;
        //one issue is that when the node get removed, its value might be updated already.see test case testDoubleLinkListRemoveNode.
        //solution can be: add a defensive copy version when adding new Node. Check if content updated when remove. Overhead is new object created each time. Which makes the point of using array for efficiency invalid.
        //this is not an issue for LRUCache bcz of old reference hold externally always get updated.
        //So for LRUCache problem, use a perfect standalone doubleLinkList is not efficient.
        removeNodeAt(index);
        availabe_position.add(index);
        size--;
        return true;
    }

    @Override
    public T removeHead() {
        if (LIST_HEAD_NODE_INDEX == -1) return null;
        availabe_position.add(LIST_HEAD_NODE_INDEX);

        T element = ((Node<T>) nodeArray[LIST_HEAD_NODE_INDEX]).getElement();
        removeNodeAt(LIST_HEAD_NODE_INDEX);
        size--;
        return element;
    }

    @Override
    public T removeTail() {
        if (LIST_TAIL_NODE_INDEX == -1) return null;
        availabe_position.add(LIST_TAIL_NODE_INDEX);

        T element = ((Node<T>) nodeArray[LIST_TAIL_NODE_INDEX]).getElement();
        removeNodeAt(LIST_TAIL_NODE_INDEX);
        size--;
        return element;
    }

    @Override
    public Node<T> addNodeAtFront(T element) {
        int index = getAvailablePosition();
        insertNodeBehind(-1, index, element);
        size++;
        return (Node<T>) nodeArray[index];
    }

    @Override
    public Node<T> addNodeAtEnd(T element) {
        int index = getAvailablePosition();
        insertNodeBehind(LIST_TAIL_NODE_INDEX, index, element);
        size++;
        return (Node<T>) nodeArray[index];
    }

    @Override
    public Node<T> insertNodeBehind(Node node, T element) {
        int index = getAvailablePosition();

        insertNodeBehind(node.index, index, element);
        size++;
        return (Node<T>) nodeArray[index];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public List<T> getElementsByOrder() {
        List<T> result = new ArrayList<T>();
        int nextIndex = LIST_HEAD_NODE_INDEX;
        while (nextIndex != -1) {
            Node<T> node = (Node<T>) nodeArray[nextIndex];
            nextIndex = node.nextNodeIndex;
            //defensive copy.
            result.add(node.getElement());

        }
        return result;
    }

    private int getAvailablePosition() {
        Integer index = availabe_position.poll();
        if (index == null) {
            grow();
            return getAvailablePosition();
        } else return index;
    }

    private void grow() {
        // overflow-conscious code
        int oldCapacity = nodeArray.length;
        if (oldCapacity == MAX_ARRAY_SIZE) throw new OutOfMemoryError();

        int newCapacity = oldCapacity + (oldCapacity >> 1);

        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = MAX_ARRAY_SIZE;

        nodeArray = Arrays.copyOf(nodeArray, newCapacity);

        for (int i = oldCapacity; i < newCapacity; i++) {
            nodeArray[i] = new Node(-1, -1, null);
            availabe_position.add(i);
        }
    }


    private void removeNodeAt(int index) {
        Node<T> node = (Node<T>) nodeArray[index];
        if (node.prevNodeIndex != -1) {
            Node<T> prevNode = (Node<T>) nodeArray[node.prevNodeIndex];
            prevNode.nextNodeIndex = node.nextNodeIndex;
        } else {
            LIST_HEAD_NODE_INDEX = node.nextNodeIndex;
            if (LIST_HEAD_NODE_INDEX != -1) {
                Node<T> nextNode = (Node<T>) nodeArray[node.nextNodeIndex];
                nextNode.prevNodeIndex = -1;
            }
        }
        if (node.nextNodeIndex != -1) {
            Node<T> nextNode = (Node<T>) nodeArray[node.nextNodeIndex];
            nextNode.prevNodeIndex = node.prevNodeIndex;
        } else {
            LIST_TAIL_NODE_INDEX = node.prevNodeIndex;
            if (LIST_TAIL_NODE_INDEX != -1) {
                Node<T> prevNode = (Node<T>) nodeArray[node.prevNodeIndex];
                prevNode.nextNodeIndex = -1;
            }
        }

    }

    //nodeIndex : the index of the node you need to insert behind. nodeIndex = -1. indicates this node is inserted at head positon.
    //index:  the index you store the new node in the array.
    private void insertNodeBehind(int nodeIndex, int index, T element) {
        int prev;
        int next;

        if (nodeIndex == -1) {
            prev = -1;
            next = -1;
            if (LIST_HEAD_NODE_INDEX != -1) {
                next = LIST_HEAD_NODE_INDEX;
                Node<T> currentHeadNode = (Node<T>) nodeArray[LIST_HEAD_NODE_INDEX];
                currentHeadNode.prevNodeIndex = index;
            } else {
                LIST_TAIL_NODE_INDEX = index;
            }
            LIST_HEAD_NODE_INDEX = index;
        } else {
            Node node = (Node<T>) nodeArray[nodeIndex];

            if (nodeIndex != LIST_TAIL_NODE_INDEX) {
                Node<T> nextNode = (Node<T>) nodeArray[node.nextNodeIndex];
                nextNode.prevNodeIndex = index;
                next = node.nextNodeIndex;
            } else {
                LIST_TAIL_NODE_INDEX = index;
                next = -1;
            }

            node.nextNodeIndex = index;
            prev = nodeIndex;
        }
        //Node newNode = new Node(prev, next, key, val);
        Node<T> indexNode = (Node<T>) nodeArray[index];
        indexNode.prevNodeIndex = prev;
        indexNode.nextNodeIndex = next;
        indexNode.element = element;
        indexNode.index = index;

    }

}
