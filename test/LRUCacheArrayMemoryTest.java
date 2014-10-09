import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Jason Weng
 */
public class LRUCacheArrayMemoryTest {

    @Test
    public void testCacheWithSize1() {
        LRUCacheArrayMemory lruCache = new LRUCacheArrayMemory(1);
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
        LRUCacheArrayMemory lruCache = new LRUCacheArrayMemory(2);
        lruCache.set(2, 1);
        Assert.assertEquals("2", lruCache.getKeyByOrderAsStr());

        lruCache.set(2, 2);
        int val = lruCache.get(2);
        Assert.assertEquals(2, val);

    }


    @Test
    public void testCache() {
        LRUCacheArrayMemory lruCache = new LRUCacheArrayMemory(3);
        lruCache.set(1, 1);
        Assert.assertEquals("1", lruCache.getKeyByOrderAsStr());
        lruCache.set(2, 2);
        Assert.assertEquals("21", lruCache.getKeyByOrderAsStr());
        lruCache.set(3, 3);
        Assert.assertEquals("321", lruCache.getKeyByOrderAsStr());
        lruCache.set(4, 4);
        Assert.assertEquals("432", lruCache.getKeyByOrderAsStr());

        int val4 = lruCache.get(4);
        Assert.assertEquals("432", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(4, val4);

        int val3 = lruCache.get(3);
        Assert.assertEquals("342", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val3);

        int val2 = lruCache.get(2);
        Assert.assertEquals("234", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val2);

        int val1 = lruCache.get(1);
        Assert.assertEquals("234", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val1);

        lruCache.set(5, 5);
        Assert.assertEquals("523", lruCache.getKeyByOrderAsStr());

        int val = lruCache.get(1);
        Assert.assertEquals("523", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals("253", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val);

        val = lruCache.get(3);
        Assert.assertEquals("325", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val);

        val = lruCache.get(4);
        Assert.assertEquals("325", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(5);
        Assert.assertEquals("532", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(5, val);

        val = lruCache.get(3);
        Assert.assertEquals("352", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(3, val);

        val = lruCache.get(2);
        Assert.assertEquals("235", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(2, val);

        val = lruCache.get(5);
        Assert.assertEquals("523", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(5, val);

        lruCache.set(3, 7);
        Assert.assertEquals("352", lruCache.getKeyByOrderAsStr());

        val = lruCache.get(3);
        Assert.assertEquals("352", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(7, val);

        lruCache.set(4, 6);
        Assert.assertEquals("435", lruCache.getKeyByOrderAsStr());

        val = lruCache.get(4);
        Assert.assertEquals("435", lruCache.getKeyByOrderAsStr());
        Assert.assertEquals(6, val);
    }

}
