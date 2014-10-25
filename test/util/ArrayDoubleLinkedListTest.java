package util;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

import static util.ArrayDoubleLinkedList.Node;

/**
 * User: Jason Weng
 */
public class ArrayDoubleLinkedListTest {


    @Test
    public void testDoubleLinkListOperation() {
        ArrayDoubleLinkedList arrayDoubleLinkedList = new ArrayDoubleLinkedList(10);

        Node node = arrayDoubleLinkedList.addNodeAtEnd(1, 1);
        List<Node> actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        List<Node> expectedList = Lists.newArrayList(new Node(1, 1));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        arrayDoubleLinkedList.removeNode(node);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList();
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        Node node2_2 = arrayDoubleLinkedList.addNodeAtFront(2, 2);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        Node node3_3 = arrayDoubleLinkedList.insertNodeBehind(node2_2, 3, 3);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2), new Node(3, 3));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        Node node4_4 = arrayDoubleLinkedList.insertNodeBehind(node3_3, 4, 4);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2), new Node(3, 3), new Node(4, 4));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        Node node5_5 = arrayDoubleLinkedList.insertNodeBehind(node2_2, 5, 5);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2), new Node(5, 5), new Node(3, 3), new Node(4, 4));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        int key = arrayDoubleLinkedList.removeTail();
        Assert.assertEquals(4, key);

        int key2 = arrayDoubleLinkedList.removeTail();
        Assert.assertEquals(3, key2);

        arrayDoubleLinkedList.removeNode(node5_5);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        //node4_4 already be removed, should have no impact to the content.
        arrayDoubleLinkedList.removeNode(node4_4);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

        arrayDoubleLinkedList.removeNode(node5_5);
        actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        expectedList = Lists.newArrayList(new Node(2, 2));
        Assert.assertTrue(checkContentSame(expectedList, actualList));

    }

    @Test
    public void testDoubleLinkListRemoveNode() {
        ArrayDoubleLinkedList arrayDoubleLinkedList = new ArrayDoubleLinkedList(2);

        arrayDoubleLinkedList.addNodeAtEnd(1, 1);

        Node node_22 = arrayDoubleLinkedList.addNodeAtEnd(2, 2);

        arrayDoubleLinkedList.removeTail();

        arrayDoubleLinkedList.removeTail();

        //the slot of node2_2 is overwritten by this operation
        Node node_33 = arrayDoubleLinkedList.addNodeAtEnd(3, 3);

        arrayDoubleLinkedList.removeNode(node_22);

        List<Node> actualList = arrayDoubleLinkedList.getCurrentNodeByOrder();
        List<Node> expectedList = Lists.newArrayList();
        // List<Node> expectedList = Lists.newArrayList(new Node(3,3));
        Assert.assertTrue(checkContentSame(expectedList, actualList));


    }

    @Test
    public void testDoubleLinkListSizeGrow() {
        ArrayDoubleLinkedList arrayDoubleLinkedList = new ArrayDoubleLinkedList(2);

        for (int i = 0; i < 10000; i++) {
            arrayDoubleLinkedList.addNodeAtEnd(i, i);
        }

        int key = arrayDoubleLinkedList.removeTail();
        Assert.assertEquals(9999, key);

    }


    private boolean checkContentSame(List<Node> expectedList, List<Node> actualList) {
        int expSize = expectedList.size();
        int actualSize = actualList.size();
        if (expSize != actualSize) return false;

        for (int i = 0; i < actualSize; i++) {
            Node actual = actualList.get(i);
            Node expected = expectedList.get(i);
            if ((actual.getKey() != expected.getKey()) || (actual.getVal() != expected.getVal())) return false;
        }
        return true;
    }


}
