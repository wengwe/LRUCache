package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User: Jason Weng
 */
public class ArrayDoubleLinkedList {

    private final int capacity;
    private final Node[] nodeArray;

    private int LIST_HEAD_NODE_INDEX;
    private int actualSize;
    private int LIST_TAIL_NODE_INDEX;


    public ArrayDoubleLinkedList(int capacity) {
        this.capacity = capacity;
        this.nodeArray = new Node[capacity];
        actualSize = 0;
        LIST_TAIL_NODE_INDEX = -1;
        LIST_HEAD_NODE_INDEX = -1;
        //initialize memory at the beginning
        for (int i = 0; i < capacity; i++) {
            nodeArray[i] = new Node(-1, -1, -1, -1);
        }

    }

    public void moveNodeToHead(Node node) {
        if (node.index == LIST_HEAD_NODE_INDEX) return;
        removeNodeAt(node.index);
        insertNodeBehind(-1, node.index, node.getKey(), node.getVal());
    }


    public Optional<Node> insertNodeAtFront(Node node) {
        int pos;
        Node nodeToRemove = null;
        if (actualSize == capacity) {
            pos = LIST_TAIL_NODE_INDEX;
            nodeToRemove = new Node(nodeArray[LIST_TAIL_NODE_INDEX]);
            removeNodeAt(LIST_TAIL_NODE_INDEX);
            actualSize--;
        } else {
            pos = actualSize;
        }
        insertNodeBehind(-1, pos, node.getKey(), node.getVal());
        //this change the parameters passed in.
        node.index = pos;
        actualSize++;
        return Optional.ofNullable(nodeToRemove);
    }

    public List<Node> getCurrentNodeByOrder() {
        List<Node> result = new ArrayList<Node>();
        int nextIndex = LIST_HEAD_NODE_INDEX;
        while (nextIndex != -1) {
            Node node = nodeArray[nextIndex];
            nextIndex = node.nextNodeIndex;
            //defensive copy.
            result.add(new Node(node));

        }
        return result;
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

        public Node(Node node) {
            this.key = node.key;
            this.val = node.val;
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
