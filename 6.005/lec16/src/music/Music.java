package music;


/**
 * Music represents a piece of music played by multiple instruments.
 */
public interface Music {
    /**
     * @return total duration of this piece
     */
    double duration();
    
    /**
     * @param v visitor to apply to this object
     */
    <T> T accept(Visitor<T> v);
    
    /**
     * Visitor represents a function over the Music datatype, 
     *    Visitor<T> v   ==>   v: Music -> T
     * @param <T> return type of the function
     */
    public static interface Visitor<T> {
        T on(Rest m);
        T on(Note m);
        T on(Together m);
        T on(Concat m);
        T on(Forever m);
    }
}
