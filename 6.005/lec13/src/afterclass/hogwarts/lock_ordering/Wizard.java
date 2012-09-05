package afterclass.hogwarts.lock_ordering;

import java.util.HashSet;
import java.util.Set;

public class Wizard {
    private final String name;
    private final Set<Wizard> friends;
    // rep invariant:
    //    name, friends != null
    //    friend links are bidirectional: for all f in friends, f.friends contains this
    
    // concurrency argument: 
    //    This type is threadsafe because the rep is guarded by this object's lock.
    
    private void checkRep() {
        assert name != null;
        assert friends != null;
        assert bidirectionalFriendships();
    }
    
    private boolean bidirectionalFriendships() {
        for (Wizard p : friends) {
            assert p.isFriendsWith(this);
        }
        return true;
    }
    
    public Wizard(String name) {
        this.name = name;
        this.friends = new HashSet<Wizard>();
        checkRep();
    }
    
    public synchronized boolean isFriendsWith(Wizard p) {
        return friends.contains(p);
    }
    
    public void friend(Wizard that) {
        Object first, second;
        if (this.name.compareTo(that.name) < 0) {
            first = this; second = that;
        } else {
            first = that; second = this;            
        }

        synchronized (first) {
            synchronized (second) {
                if (friends.add(that)) {
                    that.friend(this);
                }
                checkRep();                
            }
        }
    }
    
    public void defriend(Wizard that) {
        Object first, second;
        if (this.name.compareTo(that.name) < 0) {
            first = this; second = that;
        } else {
            first = that; second = this;            
        }

        synchronized (first) {
            synchronized (second) {
                if (friends.remove(that)) {
                    that.defriend(this);
                }
                checkRep();
            }
        }
    }
    
    public synchronized String toString() {
        synchronized (this) {
            return name;
        }
    }
}
