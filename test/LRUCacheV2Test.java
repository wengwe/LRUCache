import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Jason Weng
 */
public class LRUCacheV2Test {

    @Test
    public void testCacheWithSize1() {
        LRUCacheV2 lruCache = new LRUCacheV2(1);
        lruCache.set(1, 1);
        Assert.assertEquals("1", lruCache.getKeyByOrderAsStr());

        int val = lruCache.get(1);
        Assert.assertEquals("1", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(1, val);

        lruCache.set(2, 2);
        Assert.assertEquals("2", lruCache.getKeyByOrderAsStr());
        val = lruCache.get(1);
        Assert.assertEquals("2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals("2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val);

        lruCache.set(3, 3);
        Assert.assertEquals("3", lruCache.getKeyByOrderAsStr());
        val = lruCache.get(2);
        Assert.assertEquals("3", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(3);
        Assert.assertEquals("3", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val);


    }


    @Test
    public void testCacheOverWriteKey() {
        LRUCacheV2 lruCache = new LRUCacheV2(2);
        lruCache.set(2, 1);
        Assert.assertEquals("2", lruCache.getKeyByOrderAsStr());

        lruCache.set(2, 2);
        int val = lruCache.get(2);
        Assert.assertEquals(2, val);

    }


    @Test
    public void testCacheWriteThenRetrieveReverseOrder() {
        LRUCacheV2 lruCache = new LRUCacheV2(3);
        lruCache.set(1, 1);
        Assert.assertEquals("1", lruCache.getKeyByOrderAsStr());
        lruCache.set(2, 2);
        Assert.assertEquals("2 1", lruCache.getKeyByOrderAsStr());
        lruCache.set(3, 3);
        Assert.assertEquals("3 2 1", lruCache.getKeyByOrderAsStr());
        lruCache.set(4, 4);
        Assert.assertEquals("4 3 2", lruCache.getKeyByOrderAsStr());

        int val4 = lruCache.get(4);
        Assert.assertEquals("4 3 2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(4, val4);

        int val3 = lruCache.get(3);
        Assert.assertEquals("3 4 2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val3);

        int val2 = lruCache.get(2);
        Assert.assertEquals("2 3 4", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val2);

        int val1 = lruCache.get(1);
        Assert.assertEquals("2 3 4", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val1);

        lruCache.set(5, 5);
        Assert.assertEquals("5 2 3", lruCache.getKeyByOrderAsStr());

        int val = lruCache.get(1);
        Assert.assertEquals("5 2 3", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals("2 5 3", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val);

        val = lruCache.get(3);
        Assert.assertEquals("3 2 5", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val);

        val = lruCache.get(4);
        Assert.assertEquals("3 2 5", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(5);
        Assert.assertEquals("5 3 2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(5, val);

        val = lruCache.get(3);
        Assert.assertEquals("3 5 2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val);

        val = lruCache.get(2);
        Assert.assertEquals("2 3 5", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val);

        val = lruCache.get(5);
        Assert.assertEquals("5 2 3", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(5, val);

        lruCache.set(3, 7);
        Assert.assertEquals("3 5 2", lruCache.getKeyByOrderAsStr());

        val = lruCache.get(3);
        Assert.assertEquals("3 5 2", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(7, val);

        lruCache.set(4, 6);
        Assert.assertEquals("4 3 5", lruCache.getKeyByOrderAsStr());

        val = lruCache.get(4);
        Assert.assertEquals("4 3 5", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(6, val);
    }


}
