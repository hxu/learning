package string;

/** String represents an immutable sequence of characters. */
public class String_rep2 {
    private char[] a;
    private int offset;
    private int count;
    // Rep invariant:
    //     a != null
    //     0 <= offset <= count <= a.length
    // Abstraction function: 
    //     this represents the string of characters 
    //     a[offset]...a[offset+count-1]

    /** Get the substring between start and end, exclusive.
     * @param start starting index
     * @param end ending index; requires start <= end
     * @return string consisting of charAt(start)...charAt(end-1)
     */
    public String_rep2 substring (int start, int end) {
        String_rep2 s = new String_rep2();
        s.a = this.a;
        s.offset = this.offset + start;
        s.count = end - start;
        return s;
    }
    
    // Private constructor for the use of producer methods like substring()
    // Requires the caller to establish the rep invariant!
    private String_rep2() {}

}
