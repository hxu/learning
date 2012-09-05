package beforeclass.hogwarts;

import java.util.HashSet;
import java.util.Set;

public class Wizard {
    private final String name;
    private final Set<Wizard> friends;
    // rep invariant:
    //    name, friends != null
    //    friend links are bidirectional: for all f in friends, f.friends contains this
    
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
    
    public boolean isFriendsWith(Wizard that) {
        return this.friends.contains(that);
    }
    
    public void friend(Wizard that) {
        if (this.friends.add(that)) {
            that.friend(this);
        }
        checkRep();
    }
    
    public void defriend(Wizard that) {
        if (this.friends.remove(that)) {
            that.defriend(this);
        }
        checkRep();
    }
    
    public String toString() {
        return name;
    }
}
