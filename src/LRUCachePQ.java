import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * User: Jason Weng
 */
public class LRUCachePQ {

    private final PriorityQueue<TimeEntry> timeEntryPriorityQueue;
    private int actualSize;
    private Map<Integer, TimeEntry> keyMpas;
    private final int capacity;

    public LRUCachePQ(int capacity) {
        actualSize = 0;
        timeEntryPriorityQueue = new PriorityQueue<TimeEntry>(capacity);
        keyMpas = new HashMap<Integer, TimeEntry>();
        this.capacity = capacity;
    }

    public int get(int key) {
        final Integer keyObject = key;
        if (keyMpas.containsKey(keyObject)) {
            TimeEntry entry = keyMpas.get(keyObject);
            long currentTime = System.currentTimeMillis();
            timeEntryPriorityQueue.remove(new TimeEntry(entry.timeStamp, key));
            timeEntryPriorityQueue.add(new TimeEntry(currentTime, key));
            keyMpas.put(keyObject, new TimeEntry(currentTime, entry.element));
            return entry.element;
        } else return -1;
    }

    public void set(int key, int value) {
        if (actualSize == capacity) {
            TimeEntry keyEntry = timeEntryPriorityQueue.poll();
            keyMpas.remove(keyEntry.element);
            actualSize--;
        }
        long currentTime = System.currentTimeMillis();
        timeEntryPriorityQueue.add(new TimeEntry(currentTime, key));
        keyMpas.put(key, new TimeEntry(currentTime, value));
        actualSize += 1;

    }


    class TimeEntry implements Comparable<TimeEntry> {
        long timeStamp;
        int element;


        TimeEntry(long timeStamp, int element) {
            this.timeStamp = timeStamp;
            this.element = element;
        }


        @Override
        public int compareTo(TimeEntry o) {
            long res = this.timeStamp - o.timeStamp;
            if (res > 0) return 1;
            else if (res < 0) return -1;
            else return this.element - o.element;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimeEntry entry = (TimeEntry) o;

            if (element != entry.element) return false;
            if (timeStamp != entry.timeStamp) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) (timeStamp ^ (timeStamp >>> 32));
            result = 31 * result + element;
            return result;
        }
    }


}
