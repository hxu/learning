package beforeclass.editor;

/** SimpleBuffer is a threadsafe EditBuffer with a simple rep. */
public class SimpleBuffer implements EditBuffer {
    private String text;
    private final Object lock; // demonstrates use of a private lock object
    // Rep invariant: 
    //   text, lock != null
    // Abstraction function: 
    //   represents the sequence text[0],...,text[text.length()-1]
    // Rep is guarded by lock -- don't touch the rep without synchronizing on lock.

    private void checkRep() {
        assert text != null;
    }
    
    /** Make a SimpleBuffer. */
    public SimpleBuffer() {
        text = "";
        lock = new Object();
        checkRep();
    }
    
    /** @see EditBuffer#insert */
    public void insert(int pos, String s) {
        synchronized (lock) {
            text = text.substring(0, pos) + s + text.substring(pos);
            checkRep();
        }
    }

    /** @see EditBuffer#delete */
    public void delete(int pos, int len) {
        synchronized (lock) {
            text = text.substring(0, pos) + text.substring(pos+len);
            checkRep();
        }
    }

    /** @see EditBuffer#length */
    public int length() {
        synchronized (lock) {
            return text.length();
        }
    }

    /** @see EditBuffer#toString */
    public synchronized String toString() {
        synchronized (lock) {
            return text;
        }
    }

    /** @see EditBuffer#getLock */
    public synchronized Object getLock() {
        return lock;
    }
}
