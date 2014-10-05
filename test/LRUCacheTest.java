import org.junit.Test;

/**
 * User: Jason Weng
 */
public class LRUCacheTest {


    @Test
    public void testCache() {
        LRUCache lruCache = new LRUCache(3);
        lruCache.set(1, 1);
        lruCache.set(2, 2);
        lruCache.set(3, 3);
        lruCache.set(4, 4);

        lruCache.get(4);
        lruCache.get(3);
        lruCache.get(2);
        lruCache.get(1);

        lruCache.set(5, 5);

        lruCache.get(1);
        lruCache.get(2);
        lruCache.get(3);
        lruCache.get(4);
        lruCache.get(5);

    }
}
