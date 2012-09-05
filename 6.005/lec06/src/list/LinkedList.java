package list;

/** LinkedList represents a mutable list of elements using a linked list. */
public class LinkedList<E> implements List<E> {
    private Node first;
    private class Node {
        E elt;
        Node next;
    }
    
    // Rep invariant:
    //     true
    // Abstraction function:
    //     represents the sequence first.elt, first.next.elt, ..., first.next*.elt
    //                           until (first.next*) is null
    
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
