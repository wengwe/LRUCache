import java.util.HashMap;
import java.util.Map;

/**
 * User: Jason Weng
 */
public class LRUCache {

    private final Map<Integer, Integer> keyMap;

    private final int capacity;
    private final DoubleLinkListNode[] nodeArray;

    private int LIST_HEAD_NODE_INDEX;
    private int actualSize;
    private int LIST_TAIL_NODE_INDEX;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        actualSize = 0;
        keyMap = new HashMap<Integer, Integer>();
        nodeArray = new DoubleLinkListNode[capacity];
        //Initialize to -1, which means no node in the list,
        LIST_TAIL_NODE_INDEX = -1;
        LIST_HEAD_NODE_INDEX = -1;
        //initialize memory at the beginning
        for (int i = 0; i < capacity; i++) {
            nodeArray[i] = new DoubleLinkListNode(-1, -1, -1, -1);
        }

    }


    public int get(int key) {
        if (keyMap.containsKey(key)) {
            int nodeIndex = keyMap.get(key);
            DoubleLinkListNode node = nodeArray[nodeIndex];
            if (nodeIndex != LIST_HEAD_NODE_INDEX) {
                removeNodeAt(nodeIndex);
                insertNodeBehind(-1, nodeIndex, key, node.val);
            }
            return node.val;
        } else return -1;

    }


    public void set(int key, int value) {
        if (keyMap.containsKey(key)) {
            int nodeIndex = keyMap.get(key);
            if (nodeIndex != LIST_HEAD_NODE_INDEX) {
                removeNodeAt(nodeIndex);
                insertNodeBehind(-1, nodeIndex, key, value);
            }
            nodeArray[nodeIndex].val = value;
            return;
        }

        int arrayPos;

        if (actualSize == capacity) {
            arrayPos = LIST_TAIL_NODE_INDEX;
            DoubleLinkListNode tailNode = nodeArray[LIST_TAIL_NODE_INDEX];
            keyMap.remove(tailNode.key);
            removeNodeAt(LIST_TAIL_NODE_INDEX);
            actualSize--;
        } else {
            arrayPos = actualSize;
        }

        insertNodeBehind(-1, arrayPos, key, value);
        keyMap.put(key, arrayPos);
        actualSize++;
    }

    protected String getKeyByOrderAsStr() {
        StringBuffer sb = new StringBuffer();
        int nextIndex = LIST_HEAD_NODE_INDEX;
        while (nextIndex != -1) {
            DoubleLinkListNode node = nodeArray[nextIndex];
            sb.append(node.key).append(" ");
            nextIndex = node.nextNodeIndex;
        }
        return sb.toString().trim();
    }

    private void removeNodeAt(int index) {
        DoubleLinkListNode node = nodeArray[index];
        if (node.prevNodeIndex != -1) {
            DoubleLinkListNode prevNode = nodeArray[node.prevNodeIndex];
            prevNode.nextNodeIndex = node.nextNodeIndex;
        } else {
            LIST_HEAD_NODE_INDEX = node.nextNodeIndex;
            if (LIST_HEAD_NODE_INDEX != -1) {
                DoubleLinkListNode nextNode = nodeArray[node.nextNodeIndex];
                nextNode.prevNodeIndex = -1;
            }
        }
        if (node.nextNodeIndex != -1) {
            DoubleLinkListNode nextNode = nodeArray[node.nextNodeIndex];
            nextNode.prevNodeIndex = node.prevNodeIndex;
        } else {
            LIST_TAIL_NODE_INDEX = node.prevNodeIndex;
            if (LIST_TAIL_NODE_INDEX != -1) {
                DoubleLinkListNode prevNode = nodeArray[node.prevNodeIndex];
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
                DoubleLinkListNode currentHeadNode = nodeArray[LIST_HEAD_NODE_INDEX];
                currentHeadNode.prevNodeIndex = index;
            } else {
                LIST_TAIL_NODE_INDEX = index;
            }
            LIST_HEAD_NODE_INDEX = index;
        } else {
            DoubleLinkListNode node = nodeArray[nodeIndex];

            if (nodeIndex != LIST_TAIL_NODE_INDEX) {
                DoubleLinkListNode nextNode = nodeArray[node.nextNodeIndex];
                nextNode.prevNodeIndex = index;
                next = node.nextNodeIndex;
            } else {
                LIST_TAIL_NODE_INDEX = index;
                next = -1;
            }

            node.nextNodeIndex = index;
            prev = nodeIndex;
        }
        //DoubleLinkListNode newNode = new DoubleLinkListNode(prev, next, key, val);
        nodeArray[index].prevNodeIndex = prev;
        nodeArray[index].nextNodeIndex = next;
        nodeArray[index].key = key;
        nodeArray[index].val = val;

    }


    private static class DoubleLinkListNode {
        int prevNodeIndex;
        int nextNodeIndex;
        int key;
        int val;

        private DoubleLinkListNode(int prev, int next, int key, int val) {
            this.prevNodeIndex = prev;
            this.nextNodeIndex = next;
            this.key = key;
            this.val = val;
        }


    }
}
