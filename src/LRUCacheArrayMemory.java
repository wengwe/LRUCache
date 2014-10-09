import java.util.HashMap;
import java.util.Map;

/**
 * User: Jason Weng
 */
public class LRUCacheArrayMemory {

    private final Map<Integer, Integer> keyMap;

    private final int capacity;
    private final DoubleLinkListNode[] nodeArray;
    private final int LIST_HEAD_NODE_INDEX = 0;

    private int actualSize;

    private int LIST_TAIL_NODE_INDEX;


    public LRUCacheArrayMemory(int capacity) {
        this.capacity = capacity;
        actualSize = 0;
        keyMap = new HashMap<Integer, Integer>();
        nodeArray = new DoubleLinkListNode[capacity + 1];
        nodeArray[0] = new DoubleLinkListNode(-1, 1, -1, -1);
        LIST_TAIL_NODE_INDEX = 0;

    }


    public int get(int key) {
        if (keyMap.containsKey(key)) {
            int nodeIndex = keyMap.get(key);
            DoubleLinkListNode node = nodeArray[nodeIndex];
            if (nodeIndex != nodeArray[LIST_HEAD_NODE_INDEX].nextNodeIndex) {
                removeNodeAt(nodeIndex);
                insertNodeBehind(LIST_HEAD_NODE_INDEX, nodeIndex, key, node.val);
            }
            return node.val;
        } else return -1;

    }


    public void set(int key, int value) {
        if (keyMap.containsKey(key)) {
            int nodeIndex = keyMap.get(key);
            if (nodeIndex != nodeArray[LIST_HEAD_NODE_INDEX].nextNodeIndex) {
                removeNodeAt(nodeIndex);
                insertNodeBehind(LIST_HEAD_NODE_INDEX, nodeIndex, key, value);
            }
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
            arrayPos = actualSize + 1;
        }

        insertNodeBehind(LIST_HEAD_NODE_INDEX, arrayPos, key, value);
        keyMap.put(key, arrayPos);
        actualSize++;
    }

    protected String getKeyByOrderAsStr() {
        StringBuffer sb = new StringBuffer();
        int nextIndex = nodeArray[LIST_HEAD_NODE_INDEX].nextNodeIndex;
        while (nextIndex != -1) {
            DoubleLinkListNode node = nodeArray[nextIndex];
            sb.append(node.key);
            nextIndex = node.nextNodeIndex;
        }
        return sb.toString();
    }

    private void removeNodeAt(int index) {
        DoubleLinkListNode node = nodeArray[index];
        if (index != LIST_HEAD_NODE_INDEX) {
            DoubleLinkListNode prevNode = nodeArray[node.prevNodeIndex];
            prevNode.nextNodeIndex = node.nextNodeIndex;
        }
        if (index != LIST_TAIL_NODE_INDEX) {
            DoubleLinkListNode nextNode = nodeArray[node.nextNodeIndex];
            nextNode.prevNodeIndex = node.prevNodeIndex;
        } else {
            LIST_TAIL_NODE_INDEX = node.prevNodeIndex;
            DoubleLinkListNode prevNode = nodeArray[node.prevNodeIndex];
            prevNode.nextNodeIndex = -1;
        }

    }

    //nodeIndex : the index of th node you need to insert behind.
    //index:  the index you store the new node in the array.
    private void insertNodeBehind(int nodeIndex, int index, int key, int val) {
        DoubleLinkListNode node = nodeArray[nodeIndex];

        int next = -1;

        if (nodeIndex != LIST_TAIL_NODE_INDEX) {
            DoubleLinkListNode nextNode = nodeArray[node.nextNodeIndex];
            nextNode.prevNodeIndex = index;
            next = node.nextNodeIndex;
        } else {
            LIST_TAIL_NODE_INDEX = index;
        }

        node.nextNodeIndex = index;
        int prev = nodeIndex;

        DoubleLinkListNode newNode = new DoubleLinkListNode(prev, next, key, val);
        nodeArray[index] = newNode;

    }


    private class DoubleLinkListNode {
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
