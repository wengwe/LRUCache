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
        Assert.assertEquals("1", lruCache.getDoubleLinkListAsStr());

        int val = lruCache.get(1);
        Assert.assertEquals("1", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(1, val);

        lruCache.set(2, 2);
        Assert.assertEquals("2", lruCache.getDoubleLinkListAsStr());
        val = lruCache.get(1);
        Assert.assertEquals("2", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals("2", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(2, val);

        lruCache.set(3, 3);
        Assert.assertEquals("3", lruCache.getDoubleLinkListAsStr());
        val = lruCache.get(2);
        Assert.assertEquals("3", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(3);
        Assert.assertEquals("3", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(3, val);


    }


    @Test
    public void testCache() {
        LRUCache lruCache = new LRUCache(3);
        lruCache.set(1, 1);
        Assert.assertEquals("1", lruCache.getDoubleLinkListAsStr());
        lruCache.set(2, 2);
        Assert.assertEquals("21", lruCache.getDoubleLinkListAsStr());
        lruCache.set(3, 3);
        Assert.assertEquals("321", lruCache.getDoubleLinkListAsStr());
        lruCache.set(4, 4);
        Assert.assertEquals("432", lruCache.getDoubleLinkListAsStr());

        int val4 = lruCache.get(4);
        Assert.assertEquals("432", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(4, val4);

        int val3 = lruCache.get(3);
        Assert.assertEquals("342", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(3, val3);

        int val2 = lruCache.get(2);
        Assert.assertEquals("234", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(2, val2);

        int val1 = lruCache.get(1);
        Assert.assertEquals("234", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(-1, val1);

        lruCache.set(5, 5);
        Assert.assertEquals("523", lruCache.getDoubleLinkListAsStr());

        int val = lruCache.get(1);
        Assert.assertEquals("523", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(2);
        Assert.assertEquals("253", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(2, val);

        val = lruCache.get(3);
        Assert.assertEquals("325", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(3, val);

        val = lruCache.get(4);
        Assert.assertEquals("325", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(-1, val);

        val = lruCache.get(5);
        Assert.assertEquals("532", lruCache.getDoubleLinkListAsStr());
        Assert.assertEquals(5, val);
    }
}
