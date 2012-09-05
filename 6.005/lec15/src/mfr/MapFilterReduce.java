package mfr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The map/filter/reduce operations, and the interfaces they rely on.
 */
public class MapFilterReduce {

    /**
     * Apply a function to every element of a list.
     * @param f function to apply
     * @param list list to iterate over
     * @return [f(list[0]), f(list[1]), ..., f(list[n-1])]
     */
    public static <T, U> List<U> map(Function<T, U> f, List<T> list) {
        List<U> result = new ArrayList<U>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }

    /**
     * Filter a list for elements satisfying a predicate.  Doesn't
     * modify the list.
     * @param p predicate to test
     * @param list list to iterate over
     * @return a new list containing all list[i] such that p(list[i]) == true, 
     * in the same order they appeared in the original list.
     */
    public static <T> List<T> filter(Predicate<T> p, List<T> list) {
        List<T> result = new ArrayList<T>();
        for (T t : list) {
            if (p.apply(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Combine the elements of a list from left to right using a binary operator.
     * @param op binary operator
     * @param list list to iterate over
     * @param init initial value
     * @return (((init op list[0]) op list[1]) ... ) op list[n-1]
     */
    public static <T, U> U reduce(List<T> list, BinOp<U, T, U> op, U init) {
        U result = init;
        for (T t: list) {
            result = op.apply(result, t);
        }
        return result;
    }

    /**
     * A Function<T,U> represents a unary function f:T->U.
     */
    public static interface Function<T, U> {
        /**
         * Apply this function.
         * 
         * @param t
         *            object to apply this function to
         * @return the result of applying this function to t.
         */
        public U apply(T t);
    }

    /**
     * Predicate<T> represents a boolean predicate over the type T, i.e. a
     * function p : T -> boolean. Alternatively, Predicate<T> represents the
     * subset of T for which the predicate returns true.
     */
    public static interface Predicate<T> {
        /**
         * Test this predicate on an object.
         * @param t
         *            object to test
         * @return true iff this predicate is true for t.
         */
        public boolean apply(T t);
    }

    /**
     * BinOp<T,U,V> represents a binary function r : T x U -> V.
     */
    public interface BinOp<T, U, V> {
        /**
         * Apply this binary operation. 
         * @param t
         *            T value to apply this function to
         * @param u
         *            U value to apply this function to
         * @return the result of applying this function to (t,u).
         */
        public V apply(T t, U u);
    }

    /**
     * Compose two functions.
     * @param f function A->B
     * @param g function B->C
     * @return new function A->C formed by composing f with g
     */
    public static <A,B,C> Function<A,C> compose(final Function<A,B> f, final Function<B,C> g) {
        return new Function<A,C>() {
            public C apply(A t) { return g.apply(f.apply(t)); }
        };
    }
    
    // is the following the same as the chain() we wrote in Python?
    /**
     * Compose a chain of functions.
     * @param list list of functions A->A to compose
     * @return function A->A made by composing list[0] ... list[n-1]
     */
    public static <A> Function<A,A> chain(List<Function<A,A>> list) {
        return reduce(
            list, 
            new BinOp<Function<A,A>, Function<A,A>, Function<A,A>>() {
                public Function<A, A> apply(Function<A, A> t, Function<A, A> u) {
                    return compose(t, u);
                }
            }, 
            new Function<A,A>() {
                public A apply(A t) { return t; }    
            }
        );
    }
    
    // Examples
    public static void main(String[] args) {
        // anonymous classes like the one below are effectively lambda expressions
        Function<String,String> toLowerCase = new Function<String,String>() {
            public String apply(String s) { return s.toLowerCase(); }
        };
        
        map(toLowerCase, Arrays.asList(new String[] {"A", "b", "c"}));
    }
}
