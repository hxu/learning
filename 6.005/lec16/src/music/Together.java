package music;


/**
 * Together represents two pieces of music playing at the same time.
 * The pieces start at the same instant, but may end at different times.
 */
public class Together implements Music {
    private Music top;
    private Music m2;

    private void checkRep() {
        assert top != null;
        assert m2 != null;
    }
    
    /**
     * Make a Together of two pieces of music.
     * @requires m1, m2 != null
     */
    public Together(Music m1, Music m2) {
        this.top = m1;
        this.m2 = m2;
        checkRep();
    }
    
    /**
     * @return one of the pieces of music in this together
     */
    public Music top() {
        return top;
    }
    
    /**
     * @return the other piece of music in this together
     */
    public Music bottom() {
        return m2;
    }
    
    /**
     * @return duration of this piece of music (the minimum time it takes
     * for both of them to finish playing)
     */
    public double duration() {
        return Math.max(top.duration(), m2.duration());
    }

    /**
     * @requires v != null
     */
    public <T> T accept(Visitor<T> v) {
        return v.on(this);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + top.hashCode();
        result = prime * result + m2.hashCode();
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
        final Together other = (Together) obj;
        if (!top.equals(other.top))
            return false;
        if (!m2.equals(other.m2))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "together(" + top + " |||| " + m2 + ")";
    }
}
