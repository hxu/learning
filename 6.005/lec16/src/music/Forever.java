package music;


/**
 * Forever represents a piece of music playing over and over in an 
 * infinite loop. 
 */
public class Forever implements Music {
    private final Music m;
    
    private void checkRep() {
        assert m != null;
    }
    
    /**
     * Make a Forever.
     * @param m music to loop forever
     */
    public Forever(Music m) {
        this.m = m;
        checkRep();
    }

    /**
     * @return piece of music that loops forever
     */
    public Music loop() {
        return m;
    }
    
    /**
     * @return duration of this forever, i.e. positive infinity
     */
    public double duration() {
        return Double.POSITIVE_INFINITY;
    }
        
    /**
     * @param v visitor to apply to this object
     */
    public <T> T accept(Visitor<T> v) {
        return v.on(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + m.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Forever other = (Forever) obj;
        if (!m.equals(other.m))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "forever(" + m + ")";
    }
}
