import util.ArrayDoubleLinkedListGeneric;
import util.DoubleLinkedList;
import util.KeyValPair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.DoubleLinkedList.Node;

/**
 * User: Jason Weng
 */
public class LRUCacheV2 {

    private final Map<Integer, DoubleLinkedList.Node<KeyValPair>> keyMap;
    private final DoubleLinkedList<KeyValPair> arrayDoubleLinkedList;

    private final int capacity;
    private int actualSize;


    public LRUCacheV2(int capacity) {
        this.actualSize = 0;
        this.capacity = capacity;
        keyMap = new HashMap<Integer, DoubleLinkedList.Node<KeyValPair>>();
        arrayDoubleLinkedList = new ArrayDoubleLinkedListGeneric<KeyValPair>(capacity);
    }


    public int get(int key) {
        if (keyMap.containsKey(key)) {
            Node<KeyValPair> node = keyMap.get(key);
            KeyValPair keyValPair = node.getElement();
            arrayDoubleLinkedList.removeNode(node);
            Node<KeyValPair> newNode = arrayDoubleLinkedList.addNodeAtFront(keyValPair);
            keyMap.put(key, newNode);
            return keyValPair.getVal();
        } else return -1;

    }


    public void set(int key, int value) {
        if (keyMap.containsKey(key)) {
            Node<KeyValPair> node = keyMap.get(key);
            arrayDoubleLinkedList.removeNode(node);
            Node<KeyValPair> newNode = arrayDoubleLinkedList.addNodeAtFront(new KeyValPair(key, value));
            keyMap.put(key, newNode);
            return;
        }

        if (actualSize == capacity) {
            int keyToRemove = arrayDoubleLinkedList.removeTail().getKey();
            keyMap.remove(keyToRemove);
            actualSize--;
        }

        Node<KeyValPair> node = arrayDoubleLinkedList.addNodeAtFront(new KeyValPair(key, value));
        keyMap.put(key, node);
        actualSize++;
    }


    protected String getKeyByOrderAsStr() {
        List<KeyValPair> keyValList = arrayDoubleLinkedList.getElementsByOrder();
        StringBuffer sb = new StringBuffer();
        for (KeyValPair keyValPair : keyValList) {
            sb.append(keyValPair.getKey()).append(" ");
        }
        return sb.toString().trim();
    }


}
