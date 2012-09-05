package beforeclass.hogwarts.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wizard {
    private final Castle castle;
    private final String name;
    private final URL photo;
    private final Set<Wizard> friends;
    // rep invariant:
    //    all fields != null
    //    friend links are bidirectional: for all f in friends, f.friends contains this
    
    // concurrency argument:
    //    all Wizard objects in the same castle are guarded by the castle's lock.  Don't touch
    //    the rep of any Wizard without synchronizing on the castle.
    
    private void checkRep() {
        synchronized (castle) {
            assert name != null;
            assert photo != null;
            assert friends != null;
            assert bidirectionalFriendships();
        }
    }
    
    private boolean bidirectionalFriendships() {
        synchronized (castle) {
            for (Wizard p : friends) {
                assert p.isFriendsWith(this);
            }
            return true;
        }
    }
    
    public Wizard(Castle castle, String name, URL photo) {
        this.castle = castle;
        this.name = name;
        this.photo = photo;
        this.friends = new HashSet<Wizard>();
        checkRep();
    }
    
    public String getName() {
        synchronized (castle) {
            return name;
        }
    }
    
    public URL getPhoto() {
        synchronized (castle) {
            return photo;
        }
    }
    
    public Castle getCastle() {
        synchronized (castle) {
            return castle;
        }
    }
    
    public List<Wizard> getFriends() {
        synchronized (castle) {
            return new ArrayList<Wizard>(friends);
        }
    }
    
    public boolean isFriendsWith(Wizard that) {
        synchronized (castle) {
            return this.friends.contains(that);
        }
    }
    
    public void friend(Wizard that) {
        synchronized (castle) {
            if (this.friends.add(that)) {
                that.friend(this);
            }
            checkRep();
        }
    }
    
    public void defriend(Wizard that) {
        synchronized (castle) {
            if (this.friends.remove(that)) {
                that.defriend(this);
            }
            checkRep();
        }
    }
    
    public String toString() {
        synchronized (castle) {
            return name;
        }
    }
}
