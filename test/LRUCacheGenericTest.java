import junit.framework.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * User: Jason Weng
 */
public class LRUCacheGenericTest {


    @Test
    public void testCacheWithSize1() {
        LRUCacheGeneric lruCache = new LRUCacheGeneric<Integer, Integer>(1);
        lruCache.set(1, 1);

        Optional val = lruCache.get(1);
        Assert.assertEquals(Optional.of(1), val);

        lruCache.set(2, 2);

        val = lruCache.get(1);
        Assert.assertEquals(Optional.empty(), val);

        val = lruCache.get(2);
        Assert.assertEquals(Optional.of(2), val);

        lruCache.set(3, 3);
        val = lruCache.get(2);
        Assert.assertEquals(Optional.empty(), val);

        val = lruCache.get(3);
        Assert.assertEquals(Optional.of(3), val);


    }

}
