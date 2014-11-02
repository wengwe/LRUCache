package util;

import com.google.common.collect.Lists;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * User: Jason Weng
 */
public class ArrayDoubleLinkedListGenericTest {


    @Test
    public void testDoubleLinkListOperation() {
        DoubleLinkedList<KeyValPair> arrayDoubleLinkedList = new ArrayDoubleLinkedListGeneric<KeyValPair>(10);

        DoubleLinkedList.Node<KeyValPair> node = arrayDoubleLinkedList.addNodeAtEnd(new KeyValPair(1, 1));
        List<KeyValPair> actualList = arrayDoubleLinkedList.getElementsByOrder();
        List<KeyValPair> expectedList = Lists.newArrayList(new KeyValPair(1, 1));

        Assert.assertEquals(expectedList, actualList);

        arrayDoubleLinkedList.removeNode(node);
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList();
        Assert.assertEquals(expectedList, actualList);

        DoubleLinkedList.Node<KeyValPair> node2_2 = arrayDoubleLinkedList.addNodeAtFront(new KeyValPair(2, 2));
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2));
        Assert.assertEquals(expectedList, actualList);

        DoubleLinkedList.Node<KeyValPair> node3_3 = arrayDoubleLinkedList.insertNodeBehind(node2_2, new KeyValPair(3, 3));
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2), new KeyValPair(3, 3));
        Assert.assertEquals(expectedList, actualList);

        DoubleLinkedList.Node<KeyValPair> node4_4 = arrayDoubleLinkedList.insertNodeBehind(node3_3, new KeyValPair(4, 4));
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2), new KeyValPair(3, 3), new KeyValPair(4, 4));
        Assert.assertEquals(expectedList, actualList);


        DoubleLinkedList.Node<KeyValPair> node5_5 = arrayDoubleLinkedList.insertNodeBehind(node2_2, new KeyValPair(5, 5));
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2), new KeyValPair(5, 5), new KeyValPair(3, 3), new KeyValPair(4, 4));
        Assert.assertEquals(expectedList, actualList);

        KeyValPair key = arrayDoubleLinkedList.removeTail();
        Assert.assertEquals(new KeyValPair(4, 4), key);

        KeyValPair key3_3 = arrayDoubleLinkedList.removeTail();
        Assert.assertEquals(new KeyValPair(3, 3), key3_3);

        arrayDoubleLinkedList.removeNode(node5_5);
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2));
        Assert.assertEquals(expectedList, actualList);


        //node4_4 already be removed, should have no impact to the content.
        arrayDoubleLinkedList.removeNode(node4_4);
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2));
        Assert.assertEquals(expectedList, actualList);

        arrayDoubleLinkedList.removeNode(node5_5);
        actualList = arrayDoubleLinkedList.getElementsByOrder();
        expectedList = Lists.newArrayList(new KeyValPair(2, 2));
        Assert.assertEquals(expectedList, actualList);

    }

    @Test
    public void testDoubleLinkListRemoveNode() {
        DoubleLinkedList<KeyValPair> arrayDoubleLinkedList = new ArrayDoubleLinkedListGeneric<KeyValPair>(2);

        arrayDoubleLinkedList.addNodeAtEnd(new KeyValPair(1, 1));

        DoubleLinkedList.Node<KeyValPair> node_22 = arrayDoubleLinkedList.addNodeAtEnd(new KeyValPair(2, 2));

        arrayDoubleLinkedList.removeTail();

        arrayDoubleLinkedList.removeTail();

        //the slot of node2_2 is overwritten by this operation
        DoubleLinkedList.Node<KeyValPair> node_33 = arrayDoubleLinkedList.addNodeAtEnd(new KeyValPair(3, 3));

        arrayDoubleLinkedList.removeNode(node_33);

        List<KeyValPair> actualList = arrayDoubleLinkedList.getElementsByOrder();
        List<ArrayDoubleLinkedList.Node> expectedList = Lists.newArrayList();
        // List<Node> expectedList = Lists.newArrayList(new Node(3,3));
        Assert.assertEquals(expectedList, actualList);


    }

    @Test
    public void testDoubleLinkListSizeGrow() {
        DoubleLinkedList<KeyValPair> arrayDoubleLinkedList = new ArrayDoubleLinkedListGeneric<KeyValPair>(2);

        DoubleLinkedList.Node<KeyValPair> node = arrayDoubleLinkedList.addNodeAtEnd(new KeyValPair(10000, 10000));
        for (int i = 0; i < 10000; i++) {
            KeyValPair keyValPair = new KeyValPair(i, i);
            if (i % 3 == 0) arrayDoubleLinkedList.addNodeAtEnd(keyValPair);
            if (i % 3 == 1) arrayDoubleLinkedList.addNodeAtFront(keyValPair);
            if (i % 3 == 2) arrayDoubleLinkedList.insertNodeBehind(node, keyValPair);
        }

        int size = arrayDoubleLinkedList.getSize();
        Assert.assertEquals(10001, size);

    }


}
