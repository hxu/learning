package music;


/**
 * Concat represents two pieces of music played one after the other.
 */
public class Concat implements Music {
    private final Music first;
    private final Music second;
    // Rep invariant: m2 eventually plays if it's nontrivial, i.e.
    //      m1.duration() == INFINITY => m2.duration() == 0
    //      && m1, m2 != null
    
    private void checkRep() {
        assert first != null;
        assert second != null;
        assert first.duration() < Double.POSITIVE_INFINITY || second.duration() == 0;
    }
    
    /**
     * Make a Music sequence that plays m1 followed by m2.
     * Requires that f m1 plays forever, then m2 must be empty, i.e.
     *    m1.duration() == INFINITY => m2.duration() == 0.
     * @param m1 music to play first
     * @param m2 music to play second
     */
    public Concat(Music m1, Music m2) {
        this.first = m1;
        this.second = m2;
        if (m1.duration() == Double.POSITIVE_INFINITY && m2.duration() > 0) {
            throw new IllegalArgumentException("first part runs forever, so second will never play");
        }
        checkRep();
    }
    
    /**
     * @return first piece in this concatenation
     */
    public Music first() {
        return first;
    }
    
    /**
     * @return second piece in this concatenation
     */
    public Music second() {
        return second;
    }
    
    /**
     * @return duration of this concatenation
     */
    public double duration() {
        return first.duration() + second.duration();
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
        result = prime * result + first.hashCode();
        result = prime * result + second.hashCode();
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
        final Concat other = (Concat) obj;
        if (!first.equals(other.first))
            return false;
        if (!second.equals(other.second))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return first + " " + second;
    }
}
