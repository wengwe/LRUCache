import util.ArrayDoubleLinkedList;

import java.util.HashMap;
import java.util.Map;

import static util.ArrayDoubleLinkedList.Node;

/**
 * User: Jason Weng
 */
public class LRUCacheV2 {

    private final Map<Integer, Node> keyMap;
    private final ArrayDoubleLinkedList arrayDoubleLinkedList;

    private final int capacity;
    private int actualSize;


    public LRUCacheV2(int capacity) {
        this.capacity = capacity;
        actualSize = 0;
        keyMap = new HashMap<Integer, ArrayDoubleLinkedList.Node>();
        arrayDoubleLinkedList = null;
    }


    public int get(int key) {
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            arrayDoubleLinkedList.moveNodeToHead(node);
            return node.getVal();
        } else return -1;

    }


    public void set(int key, int value) {
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            arrayDoubleLinkedList.moveNodeToHead(node);
            node.setVal(value);
            return;
        }

        if (actualSize == capacity) {
            arrayDoubleLinkedList.removeTailNode();
            actualSize--;
        }
        Node node = new Node(key, value);
        arrayDoubleLinkedList.insertNodeAtFront(node);
        keyMap.put(key, node);
        actualSize++;
    }


}
