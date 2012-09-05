package music;


/**
 * IdentityVisitor represents the identity function over the Music datatype,
 * computed by painstakingly visiting every subexpression of a Music expression.
 * Useful mainly for subclassing, to make visitors over the Music datatype
 * shorter to write.
 */
public class IdentityVisitor implements Music.Visitor<Music> {
    public Music apply(Music m) {
        return m.accept(this);
    }
    public Music on(Concat m) {
        Music m1 = m.first();
        Music m2 = m.second();
        Music newM1 = m1.accept(this);
        Music newM2 = m2.accept(this);
        // optimization for more sharing if this visitor didn't change children
        if (m1 == newM1 && m2 == newM2) return m;
        else return new Concat(newM1, newM2);
    }
    public Music on(Forever m) {
        Music loop = m.loop();
        Music newLoop = loop.accept(this);
        // optimization for more sharing if this visitor didn't change children
        if (loop == newLoop) return m;
        else return new Forever(newLoop);
    }    
    public Music on(Note m) {
        return m;
    }
    public Music on(Rest m) {
        return m;
    }
    public Music on(Together m) {
        Music m1 = m.top();
        Music m2 = m.bottom();
        Music newM1 = m1.accept(this);
        Music newM2 = m2.accept(this);
        // optimization for more sharing if this visitor didn't change children
        if (m1 == newM1 && m2 == newM2) return m;
        else return new Together(newM1, newM2);
    }
}