package beforeclass.hogwarts.model;

import java.util.HashMap;
import java.util.Map;

public class Castle {
    private final Map<String, Wizard> wizardsByName = new HashMap<String, Wizard>();
    // rep invariant:
    //    wizardsByName != null
    //    for all values w in wizardsByName,
    //        w.getCastle() == this
    //    for all key,value pairs (n,w) in wizardsByName,
    //        n.equals(w.getName())
    
    
    /** Make a Castle. */
    public Castle() {
        
    }
    
    /** Add a wizard to the castle.
     * @param w wizard to add.  Wizard.getCastle() == this
     * @throws SameNameException if another wizard in the castle has the same name
     */
    public synchronized void add(Wizard w) throws SameNameException {
        Wizard w2 = wizardsByName.get(w.getName());
        if (w2 != null) {
            if (w.equals(w2)) return; // w is already in the castle
            throw new SameNameException(w.getName() + " already in this castle");
        }
        
        wizardsByName.put(w.getName(), w);
    }
    
    /** Look up a wizard by name.
     * @param name name of wizard to look up
     * @return wizard by that name, or null if no such wizard.
     * @param name
     * @return
     */
    public synchronized Wizard lookup(String name) {
        return wizardsByName.get(name);
    }
    
    @SuppressWarnings("serial")
    public static class SameNameException extends Exception {
        public SameNameException(String msg) {
            super(msg);
        }
    }
}
