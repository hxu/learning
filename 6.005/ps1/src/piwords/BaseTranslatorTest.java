package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseTranslatorTest {
    @Test
    public void basicBaseTranslatorTest() {
        // Expect that .01 in base-2 is .25 in base-10
        // (0 * 1/2^1 + 1 * 1/2^2 = .25)
        int[] input = {0, 1};
        int[] expectedOutput = {2, 5};
        assertArrayEquals(expectedOutput,
                          BaseTranslator.convertBase(input, 2, 10, 2));
    }
    
    @Test
    public void longerBaseTranslatorTest() {
        // Expect .00101 in base-2 to be .15625 in base-10
        int[] input = {0,0,1,0,1};
        int[] expectedOutput = {1,5,6,2,5};
        assertArrayEquals(expectedOutput, BaseTranslator.convertBase(input, 2, 10, 5));
    }
    
    @Test
    public void negdigitBaseTranslatorTest() {
        // If any digits are negative, expect a null to be returned
        int[] input2 = {1, 0, 1, -1};
        assertNull(BaseTranslator.convertBase(input2, 2, 10, 4));
    }
    
    @Test
    public void baseviolationBaseTranslatorTest() {
        // If any digits >= baseA, expect a null
        int[] input = {1, 2};
        assertNull(BaseTranslator.convertBase(input, 2, 10, 2));
    }
    
    @Test
    public void minbaseBaseTranslatorTest() {
        // Check baseA >= 2, baseB >= 2, precisionB >= 1
        int[] input = {0, 1};
        assertNull(BaseTranslator.convertBase(input, 1, 10, 2));
        assertNull(BaseTranslator.convertBase(input, 2, 1, 2));
        assertNull(BaseTranslator.convertBase(input,  2, 10, 0));
    }
    
    @Test
    public void roundingBaseTranslatorTest() {
        // Check that rounding is correctly implemented if precision leaves off digits
        // So converting 0.01 in binary to decimal with precision 1 should give 0.3
        int[] input = {0, 1};
        int[] expectedoutput = {3};
        assertArrayEquals(expectedoutput, BaseTranslator.convertBase(input, 2, 10, 1));
    }

}
