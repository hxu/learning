package string;

/** String represents an immutable sequence of characters. */
public class String_ {

    //////////////////////////////////////////////
    // examples of creator methods
    
    /** Make an empty String. */
    public String_ ()                                                   { throw new UnsupportedOperationException(); }

    /** Make a String representing an integer, e.g. valueOf(35) -> "35". */
    public static String_ valueOf(int x)                                { throw new UnsupportedOperationException(); }
    
    //////////////////////////////////////////////
    // examples of observer methods
    
    /** Get length of the string.
     * @return the number of characters in this string */
    public int length()                                                 { throw new UnsupportedOperationException(); }

    /** Get the character at an index.
     * @param i index into the string, starting from 0
     * @return character at the ith position in the string
     * @throws IndexOutOfBoundsException if i not in [0,length) */
    public char charAt(int i) throws IndexOutOfBoundsException          { throw new UnsupportedOperationException(); }
    
    //////////////////////////////////////////////
    // no mutator methods
    //     (because this is an immutable type)
    


    //////////////////////////////////////////////
    // examples of producer methods

    /** Concatenate two strings.
     * @param s string to append to this
     * @return string consisting of this followed by s
     */
    public String_ concat (String s)                                    { throw new UnsupportedOperationException(); }
    

    /** Get the substring between start and end, exclusive.
     * @param start starting index
     * @param end ending index; requires start <= end
     * @return string consisting of charAt(start)...charAt(end-1)
     */
    public String_ substring (int start, int end)                       { throw new UnsupportedOperationException(); }
    
}
