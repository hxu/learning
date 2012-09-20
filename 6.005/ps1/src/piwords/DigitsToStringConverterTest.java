package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class DigitsToStringConverterTest {
    @Test
    public void basicNumberSerializerTest() {
        // Input is a 4 digit number, 0.123 represented in base 4
        int[] input = {0, 1, 2, 3};

        // Want to map 0 -> "d", 1 -> "c", 2 -> "b", 3 -> "a"
        char[] alphabet = {'d', 'c', 'b', 'a'};

        String expectedOutput = "dcba";
        assertEquals(expectedOutput,
                     DigitsToStringConverter.convertDigitsToString(
                             input, 4, alphabet));
    }
    
    @Test
    public void nulltestsNumberSerializerTest() {
        // Check the null cases
        
        // one of input[i] >= base
        int[] input = {0, 1, 2, 4};
        char[] alphabet = {'d', 'c', 'b', 'a'};
        assertNull(DigitsToStringConverter.convertDigitsToString(input, 4, alphabet));
        // one of input[i] < 0
        int[] input1 = {0, -1, 2, 3};
        assertNull(DigitsToStringConverter.convertDigitsToString(input1, 4, alphabet));
        
        // alphabet.length != base
        int[] input2 = {0, 1, 2, 3};
        assertNull(DigitsToStringConverter.convertDigitsToString(input2, 5, alphabet));
    }
    
}
