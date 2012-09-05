package inclass;

/**
 * A FixedStack is a mutable object representing a last-in-first-out
 * stack of elements.
 * A FixedStack can hold at most a fixed number of elements, specified
 * when it is created.
 */
public class FixedStack<E> {
    
    private final E[] elems;
    private int n;
    // elems[] contains the elements in the stack.
    // elems[0] is the oldest item pushed;
    // elems[n-1] is the latest item pushed, and the 
    //           next to be popped.
    // If n == 0, then the stack is empty.
    // If n == elems.length, then the stack is full.
    
    /**
     * Make a FixedStack, initially empty.
     * @param max maximum size of stack
     */
    public FixedStack(int max) {
        elems = (E[]) new Object[max]; // can't say "new E[n]" because of a subtle Java problem
        this.n = 0;
    }
    
    /**
     * Modifies this stack by pushing an element onto it.
     * Requires: stack is not full, i.e. size() < max.
     * @param e element to push on top
     */
    public void push(E e) {
        elems[n] = e;
        ++n;
    }
    
    /**
     * Modifies this stack by popping off the top element.
     * Requires: stack is not empty, i.e. size() > 0.
     * @return element on top of stack
     */
    public E pop() {
        final E e = elems[n];
        --n;
        return e;
    }
    
    /**
     * @return number of elements in the stack
     */
    public int size() {
        return n;
    }
}
