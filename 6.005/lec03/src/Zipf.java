import java.util.List;
import java.util.Map;


public class Zipf {

    /**
     * Find the most common word in a string of text.
     * @param s string of words, where a word is 
     *   a string of characters separated by spaces
     *   or punctuation.
     * @return word that occurs most often in s (at least as much
     *      as any other word); alphabetic case is ignored.
     * @throws NoWordException when s has no words
     */
    public static String mostCommonWord(String s) 
            throws NoWordsException {
        throw new RuntimeException("not implemented");
    }
    
    // To implement mostCommonWord, we'll use three helper methods:
    //   - splitIntoWords splits the string into words
    //   - countOccurrences counts how many times each word appears
    //   - findMax finds the word with the highest count
    
    // Let's write the specs for these helper methods.
    
    // Split s into words.
    // @param s string to split into words
    // @return list of words found in s, in order of their occurrence
    //  (as defined by the spec for mostCommonWords), and converted
    //  to lowercase.  e.g.
    //   splitIntoWords("a B Cc b") returns ["a", "b", "cc", "b"].
    private static List<String> splitIntoWords(String s) {
        throw new RuntimeException("not implemented");
    }
    
    // Count the number of occurrences of each element in a list.
    // @param l list of strings
    // @return map m such that m[s] == k if s occurs k times in l, while
    //      m[s] == null if s never occurs in l.
    private static Map<String, Integer> countOccurrences(List<String> l) {
        throw new RuntimeException("not implemented");
    }
    
    // Find a key with maximum value.
    // @param m frequency counts for strings
    // @return s such that m[s] >= m[t] for all other keys t in the map,
    //     or null if no such s exists
    private static String findMax(Map<String,Integer> m) {
        throw new RuntimeException("not implemented");        
    }
    
    
    /**
     * Exception thrown by mostCommonWord() when it can't find a word.
     */
    public static class NoWordsException extends Exception {
        
    }
}
