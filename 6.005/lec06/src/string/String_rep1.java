package string;

/** String represents an immutable sequence of characters. */
public class String_rep1 {
    private char[] a;
    // Rep invariant: 
    //     a != null
    // Abstraction function: 
    //     this represents the string of characters 
    //     a[0]...a[a.length-1]


    /** Get the substring between start and end, exclusive.
     * @param start starting index
     * @param end ending index; requires start <= end
     * @return string consisting of charAt(start)...charAt(end-1)
     */
    public String_rep1 substring (int start, int end) {
        String_rep1 s = new String_rep1();
        s.a = new char[end - start];
        System.arraycopy(this.a, start, s.a, 0, end - start);
        return s;
    }
    
    // Private constructor for the use of producer methods like substring()
    // Requires the caller to establish the rep invariant!
    private String_rep1() {}

}
