package music;


/**
 * VoidVisitor represents a function evaluated over the Music datatype
 * that visits every node but does nothing and returns null.
 * Useful mainly for subclassing, to make visitors that are evaluated only
 * for their side-effects (like playing the music).
 */
public class VoidVisitor implements Music.Visitor<Void> {
    public Void on(Concat m) {
        m.first().accept(this);
        m.second().accept(this);
        return null;
    }
    
    public Void on(Forever m) {
        m.loop().accept(this);
        return null;
    }

    public Void on(Note m) {
        return null;
    }

    public Void on(Rest m) {
        return null;
    }

    public Void on(final Together m) {
        m.top().accept(this);
        m.bottom().accept(this);
        return null;
    }
}