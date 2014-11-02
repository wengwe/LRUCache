package util;

/**
 * User: Jason Weng
 */
public class KeyValPair {

    private final int key;
    private final int val;

    public KeyValPair(int key, int val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyValPair)) return false;

        KeyValPair that = (KeyValPair) o;

        if (key != that.key) return false;
        if (val != that.val) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key;
        result = 31 * result + val;
        return result;
    }


    public int getKey() {
        return key;
    }

    public int getVal() {
        return val;
    }
}
