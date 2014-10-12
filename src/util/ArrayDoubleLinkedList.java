package util;

/**
 * User: Jason Weng
 */
public class ArrayDoubleLinkedList {

    private int availablePosition;

    private final int capacity;
    private final Node[] nodeArray;

    private int LIST_HEAD_NODE_INDEX;
    private int actualSize;
    private int LIST_TAIL_NODE_INDEX;


    public ArrayDoubleLinkedList(int capacity) {
        this.capacity = capacity;
        this.nodeArray = new Node[capacity];
    }

    public void removeTailNode() {
        removeNodeAt(LIST_TAIL_NODE_INDEX);
        actualSize--;

    }

    public void moveNodeToHead(Node node) {
        removeNodeAt(node.index);
    }


    public void insertNodeAtFront(Node node) {
        if (actualSize == capacity) {
            availablePosition = LIST_TAIL_NODE_INDEX;
        } else {
            availablePosition = actualSize;
        }
        insertNodeBehind(-1, availablePosition, node.getKey(), node.getVal());
        actualSize++;
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

        public int getPrevNodeIndex() {
            return prevNodeIndex;
        }

        public int getNextNodeIndex() {
            return nextNodeIndex;
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
