package piwords;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class WordFinderTest {
    @Test
    public void basicGetSubstringsTest() {
        String haystack = "abcde";
        String[] needles = {"ab", "abc", "de", "fg"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        expectedOutput.put("ab", 0);
        expectedOutput.put("abc", 0);
        expectedOutput.put("de", 3);

        assertEquals(expectedOutput, WordFinder.getSubstrings(haystack,
                                                              needles));
    }
    
    @Test
    public void notFoundGetSubstringsTest() {
        // Should return an empty HashMap
        String haystack = "abcde";
        String[] needles = {"fg"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        
        assertEquals(expectedOutput, WordFinder.getSubstrings(haystack, needles));
    }
    
    @Test
    public void multipleGetSubStringsTest() {
        // Should return only the first index occurrence
        String haystack = "ababababab";
        String[] needles = {"ab", "bab"};

        Map<String, Integer> expectedOutput = new HashMap<String, Integer>();
        expectedOutput.put("ab", 0);
        expectedOutput.put("bab", 1);
        
        assertEquals(expectedOutput, WordFinder.getSubstrings(haystack, needles));
    }
}
