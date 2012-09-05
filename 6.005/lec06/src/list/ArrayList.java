package list;

/** ArrayList represents a mutable list of elements using an array. */
public class ArrayList<E> implements List<E> {
    private E[] elts;
    private int n;
    
    // Rep invariant:
    //     elts != null
    //     0 <= n < elts.length
    // Abstraction function:
    //     represents the length-n sequence elts[0],...,elts[n-1]
    
    /** see List.get for spec */
    public E get(int i) throws IndexOutOfBoundsException { throw new UnsupportedOperationException(); }

    /** see List.size for spec */
    public int size() { throw new UnsupportedOperationException(); }
    
    /** see List.add for spec */
    public void add(E e) { throw new UnsupportedOperationException(); }
    
    /** see List.remove for spec */
    public boolean remove(E e) { throw new UnsupportedOperationException(); }

    /** see List.subList for spec */
    public List<E> subList (int from, int to) { throw new UnsupportedOperationException(); }
}
