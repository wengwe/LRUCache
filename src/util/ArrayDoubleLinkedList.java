package util;

import java.util.*;

/**
 * User: Jason Weng
 */
public class ArrayDoubleLinkedList {

    private final int capacity;
    private Node[] nodeArray;

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


    public ArrayDoubleLinkedList(int capacity) {
        this.capacity = capacity > 2 ? capacity : 2;
        this.nodeArray = new Node[this.capacity];
        LIST_TAIL_NODE_INDEX = -1;
        LIST_HEAD_NODE_INDEX = -1;
        availabe_position = new ArrayDeque<Integer>();
        //initialize memory at the beginning
        for (int i = 0; i < capacity; i++) {
            nodeArray[i] = new Node(-1, -1, -1, -1);
            availabe_position.add(i);
        }

    }

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
        return true;
    }

    public int removeTail() {
        if (LIST_TAIL_NODE_INDEX == -1) return -1;
        availabe_position.add(LIST_TAIL_NODE_INDEX);
        //TODO  if no more position availabe ,shoule double size and copy over.

        int key = nodeArray[LIST_TAIL_NODE_INDEX].key;
        removeNodeAt(LIST_TAIL_NODE_INDEX);
        return key;
    }

    public Node addNodeAtFront(int key, int value) {
        int index = availabe_position.poll();
        insertNodeBehind(-1, index, key, value);
        return nodeArray[index];

    }

    public Node addNodeAtEnd(int key, int value) {
        int index = getAvailablePosition();
        insertNodeBehind(LIST_TAIL_NODE_INDEX, index, key, value);
        return nodeArray[index];
    }

    public Node insertNodeBehind(Node node, int key, int value) {
        int index = getAvailablePosition();

        insertNodeBehind(node.index, index, key, value);
        return nodeArray[index];
    }


    public List<Node> getCurrentNodeByOrder() {
        List<Node> result = new ArrayList<Node>();
        int nextIndex = LIST_HEAD_NODE_INDEX;
        while (nextIndex != -1) {
            Node node = nodeArray[nextIndex];
            nextIndex = node.nextNodeIndex;
            //defensive copy.
            result.add(new Node(node.key, node.val));

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
            nodeArray[i] = new Node(-1, -1, -1, -1);
            availabe_position.add(i);
        }
    }


    public static class Node {
        int prevNodeIndex;
        int nextNodeIndex;
        int index;
        int key;
        int val;

        private Node(int prev, int next, int key, int val) {
            this.prevNodeIndex = prev;
            this.nextNodeIndex = next;
            this.key = key;
            this.val = val;
        }

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }


        public int getKey() {
            return key;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }


    private void removeNodeAt(int index) {
        Node node = nodeArray[index];
        if (node.prevNodeIndex != -1) {
            Node prevNode = nodeArray[node.prevNodeIndex];
            prevNode.nextNodeIndex = node.nextNodeIndex;
        } else {
            LIST_HEAD_NODE_INDEX = node.nextNodeIndex;
            if (LIST_HEAD_NODE_INDEX != -1) {
                Node nextNode = nodeArray[node.nextNodeIndex];
                nextNode.prevNodeIndex = -1;
            }
        }
        if (node.nextNodeIndex != -1) {
            Node nextNode = nodeArray[node.nextNodeIndex];
            nextNode.prevNodeIndex = node.prevNodeIndex;
        } else {
            LIST_TAIL_NODE_INDEX = node.prevNodeIndex;
            if (LIST_TAIL_NODE_INDEX != -1) {
                Node prevNode = nodeArray[node.prevNodeIndex];
                prevNode.nextNodeIndex = -1;
            }
        }

    }

    //nodeIndex : the index of the node you need to insert behind. nodeIndex = -1. indicates this node is inserted at head positon.
    //index:  the index you store the new node in the array.
    private void insertNodeBehind(int nodeIndex, int index, int key, int val) {
        int prev;
        int next;

        if (nodeIndex == -1) {
            prev = -1;
            next = -1;
            if (LIST_HEAD_NODE_INDEX != -1) {
                next = LIST_HEAD_NODE_INDEX;
                Node currentHeadNode = nodeArray[LIST_HEAD_NODE_INDEX];
                currentHeadNode.prevNodeIndex = index;
            } else {
                LIST_TAIL_NODE_INDEX = index;
            }
            LIST_HEAD_NODE_INDEX = index;
        } else {
            Node node = nodeArray[nodeIndex];

            if (nodeIndex != LIST_TAIL_NODE_INDEX) {
                Node nextNode = nodeArray[node.nextNodeIndex];
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
        nodeArray[index].prevNodeIndex = prev;
        nodeArray[index].nextNodeIndex = next;
        nodeArray[index].key = key;
        nodeArray[index].val = val;
        nodeArray[index].index = index;

    }


}
