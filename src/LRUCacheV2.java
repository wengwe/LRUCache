import util.ArrayDoubleLinkedList;

import java.util.HashMap;
import java.util.List;
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
        this.actualSize = 0;
        this.capacity = capacity;
        keyMap = new HashMap<Integer, ArrayDoubleLinkedList.Node>();
        arrayDoubleLinkedList = new ArrayDoubleLinkedList(capacity);
    }


    public int get(int key) {
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            int val = node.getVal();
            arrayDoubleLinkedList.removeNode(node);
            Node newNode = arrayDoubleLinkedList.addNodeAtFront(key, val);
            keyMap.put(key, newNode);
            return val;
        } else return -1;

    }


    public void set(int key, int value) {
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            arrayDoubleLinkedList.removeNode(node);
            Node newNode = arrayDoubleLinkedList.addNodeAtFront(key, value);
            keyMap.put(key, newNode);
            return;
        }

        if (actualSize == capacity) {
            int keyToRemove = arrayDoubleLinkedList.removeTail();
            keyMap.remove(keyToRemove);
            actualSize--;
        }

        Node node = arrayDoubleLinkedList.addNodeAtFront(key, value);
        keyMap.put(key, node);
        actualSize++;
    }


    protected String getKeyByOrderAsStr() {
        List<Node> nodeList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        StringBuffer sb = new StringBuffer();
        for (Node node : nodeList) {
            sb.append(node.getKey()).append(" ");
        }
        return sb.toString().trim();
    }

}
