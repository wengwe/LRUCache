import junit.framework.Assert;
import org.junit.Test;

/**
 * User: Jason Weng
 */
public class LRUCacheTest {


    @Test
    public void testCacheWithSize1() {
        LRUCache lruCache = new LRUCache(1);
        lruCache.set(1, 1);

        int val = lruCache.get(1);
        Assert.assertEquals(1, val);

        lruCache.set(2, 2);

        val = lruCache.get(1);
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals(2, val);

        lruCache.set(3, 3);
        val = lruCache.get(2);
        Assert.assertEquals(-1, val);

        val = lruCache.get(3);
        Assert.assertEquals(3, val);


    }


    @Test
    public void testCache() {
        LRUCache lruCache = new LRUCache(3);
        lruCache.set(1, 1);
        lruCache.set(2, 2);
        lruCache.set(3, 3);
        lruCache.set(4, 4);

        int val4 = lruCache.get(4);
        Assert.assertEquals(4, val4);

        int val3 = lruCache.get(3);
        Assert.assertEquals(3, val3);

        int val2 = lruCache.get(2);
        Assert.assertEquals(2, val2);

        int val1 = lruCache.get(1);
        Assert.assertEquals(-1, val1);

        lruCache.set(5, 5);

        int val = lruCache.get(1);
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals(2, val);

        val = lruCache.get(3);
        Assert.assertEquals(3, val);

        val = lruCache.get(4);
        Assert.assertEquals(-1, val);

        val = lruCache.get(5);
        Assert.assertEquals(5, val);
    }
}
