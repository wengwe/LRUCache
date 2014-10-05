import java.util.HashMap;
import java.util.Map;

/**
 * User: Jason Weng
 */
public class LRUCache {

    private final Map<Integer, DoubleLinkListNode> keyMap;
    private final DoubleLinkListNode LIST_HEAD;
    private final int capacity;

    private int actualSize;
    private DoubleLinkListNode currentListTail;

    public LRUCache(int capacity) {
        keyMap = new HashMap<Integer, DoubleLinkListNode>();
        this.capacity = capacity;
        this.actualSize = 0;

        LIST_HEAD = new DoubleLinkListNode(null, null, -1, -1);
        currentListTail = LIST_HEAD;
    }


    public int get(int key) {
        DoubleLinkListNode node = keyMap.get(key);
        if (node == null) return -1;
        else {
            //move this node to front if it is accessed
            moveNodeBehindHead(node);
            return node.val;
        }

    }

    public void set(int key, int value) {

        if (keyMap.containsKey(key)) {
            DoubleLinkListNode node = keyMap.get(key);
            node.val = value;
            //move this node to front if it is accessed
            moveNodeBehindHead(node);
            return;
        }

        if (actualSize == capacity) {
            //remove the node in the tail if reach the capacity
            int keyToBeDelete = currentListTail.key;
            currentListTail = currentListTail.head;
            currentListTail.tail = null;
            keyMap.remove(keyToBeDelete);
            actualSize--;
        }

        DoubleLinkListNode newNode = new DoubleLinkListNode(LIST_HEAD, LIST_HEAD.tail, key, value);
        if (LIST_HEAD.tail != null) LIST_HEAD.tail.head = newNode;
        LIST_HEAD.tail = newNode;
        if (currentListTail == LIST_HEAD) currentListTail = newNode;
        keyMap.put(key, newNode);
        actualSize += 1;
    }

    private void moveNodeBehindHead(DoubleLinkListNode nodeToMove) {
        if (nodeToMove == currentListTail) currentListTail = nodeToMove.head;
        nodeToMove.head.tail = nodeToMove.tail;
        if (nodeToMove.tail != null) nodeToMove.tail.head = nodeToMove.head;

        nodeToMove.head = LIST_HEAD;
        nodeToMove.tail = LIST_HEAD.tail;

        if (LIST_HEAD.tail != null) LIST_HEAD.tail.head = nodeToMove;
        LIST_HEAD.tail = nodeToMove;
        if (currentListTail == LIST_HEAD) currentListTail = nodeToMove;
    }


    private class DoubleLinkListNode {
        DoubleLinkListNode head;
        DoubleLinkListNode tail;
        int key;
        int val;

        private DoubleLinkListNode(DoubleLinkListNode head, DoubleLinkListNode tail, int key, int val) {
            this.head = head;
            this.tail = tail;
            this.key = key;
            this.val = val;
        }
    }

}
