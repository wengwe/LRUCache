import util.ArrayDoubleLinkedList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static util.ArrayDoubleLinkedList.Node;

/**
 * User: Jason Weng
 */
public class LRUCacheV2 {

    private final Map<Integer, Node> keyMap;
    private final ArrayDoubleLinkedList arrayDoubleLinkedList;

    // private final int capacity;
    // private int actualSize;


    public LRUCacheV2(int capacity) {
        keyMap = new HashMap<Integer, ArrayDoubleLinkedList.Node>();
        arrayDoubleLinkedList = new ArrayDoubleLinkedList(capacity);
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

        Node node = new Node(key, value);
        Optional<Node> nodeToRemove = arrayDoubleLinkedList.insertNodeAtFront(node);
        if (nodeToRemove.isPresent()) keyMap.remove(nodeToRemove.get().getKey());
        keyMap.put(key, node);

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
