package beforeclass;

// ImList, Empty, and Cons are nested inside one class
// for ease of presentation in lecture.  A real program would 
// put them in separate files. Also, specs omitted to save space -- 
// see the lecture notes for specs.
public class Im {
    // datatype definition:
    //    ImList = Empty + Cons(e:E, rest:ImList)
    
    public static interface ImList<E> {
        public ImList<E> add(E e);
        public E first();
        public ImList<E> rest();
    }

    public static class Empty<E> implements ImList<E> {
        public Empty() { }
        public ImList<E> add(E e) { return new Cons<E>(e, this); }
        public E first() { throw new UnsupportedOperationException(); }
        public ImList<E> rest() { throw new UnsupportedOperationException(); }
    }

    public static class Cons<E> implements ImList<E> {
        private final E e;
        private final ImList<E> rest;

        public Cons(E e, ImList<E> rest) { this.e = e; this.rest = rest; }
        public ImList<E> add(E e) { return new Cons<E> (e, this); }
        public E first() { return e; }
        public ImList<E> rest() { return rest; }
    }   
    
}
