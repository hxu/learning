package list;

/** List represents a mutable list of elements. */
public interface List<E> {

    //////////////////////////////////////////////
    // no creator methods
    //   (Java unfortunately doesn't allow interfaces
    //   to have constructors)
    
    //////////////////////////////////////////////
    // examples of observer methods

    /** Get size of the list.
     * @return the number of elements in this list */
    public int size();

    /** Get an element at an index.
     * @param i index into the list, starting from 0
     * @return element at the ith position in the list
     * @throws IndexOutOfBoundsException if i not in [0,size) */
    public E get(int i) throws IndexOutOfBoundsException;
    
    //////////////////////////////////////////////
    // examples of mutator methods
    
    /** Modifies this list by adding e to the end of the sequence.
     * @param e element to add; requires e != null. */
    public void add(E e);

    /** Modifies this list by remove the first occurrence of e, if found.
     * Does nothing if e is not found in the list.
     * @param e element to remove; requires e != null.
     * @return true if e was in the list; false if not */
    public boolean remove(E e);
    
    //////////////////////////////////////////////
    // examples of producer methods

    /** Get the part of this list between from and to, inclusive.
     * The returned list is a "view" on this list, which means that
     * modifications to the sublist will change the larger list too.
     * @param from starting index
     * @param to ending index; requires from <= to
     * @return sublist containing get(from)...get(to)
     */
    public List<E> subList (int from, int to);
    
}
