import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * User: Jason Weng
 */
public class LRUCacheGeneric<K, V> {

    private final Map<K, DoubleLinkListNode> keyMap;
    private final DoubleLinkListNode LIST_HEAD;
    private final int capacity;

    private int actualSize;
    private DoubleLinkListNode currentListTail;

    public LRUCacheGeneric(int capacity) {
        keyMap = new HashMap<K, DoubleLinkListNode>();
        this.capacity = capacity;
        this.actualSize = 0;

        LIST_HEAD = new DoubleLinkListNode(null, null, null, null);
        currentListTail = LIST_HEAD;
    }

    public Optional<V> get(K key) {
        DoubleLinkListNode node = keyMap.get(key);
        if (node == null) return Optional.empty();
        else {
            //move this node to front if it is accessed
            moveNodeBehindHead(node);
            return Optional.of(node.val);
        }

    }

    public void set(K key, V value) {

        if (keyMap.containsKey(key)) {
            DoubleLinkListNode node = keyMap.get(key);
            node.val = value;
            //move this node to front if it is accessed
            moveNodeBehindHead(node);
            return;
        }

        if (actualSize == capacity) {
            //remove the node in the tail if reach the capacity
            K keyToBeDelete = currentListTail.key;
            currentListTail = currentListTail.head;
            //set the reference to null. so should be ready for GC.
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
        K key;
        V val;

        private DoubleLinkListNode(DoubleLinkListNode head, DoubleLinkListNode tail, K key, V val) {
            this.head = head;
            this.tail = tail;
            this.key = key;
            this.val = val;
        }
    }

}
