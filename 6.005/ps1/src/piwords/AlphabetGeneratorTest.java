package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class AlphabetGeneratorTest {
    @Test
    public void generateFrequencyAlphabetTest() {
        // Expect in the training data that Pr(a) = 2/5, Pr(b) = 2/5,
        // Pr(c) = 1/5.
        String[] trainingData = {"aa", "bbc"};
        // In the output for base 10, they should be in the same proportion.
        char[] expectedOutput = {'a', 'a', 'a', 'a',
                                 'b', 'b', 'b', 'b',
                                 'c', 'c'};
        assertArrayEquals(expectedOutput,
                AlphabetGenerator.generateFrequencyAlphabet(
                        10, trainingData));
    }
    
    @Test
    public void zeroBaseFrequencyAlphabetTest() {
    	String[] trainingData = {"aa", "bbc"};
    	char[] result = {};
    	assertArrayEquals(result, AlphabetGenerator.generateFrequencyAlphabet(0, trainingData));
    }
    
    @Test
    public void negativeBaseFrequencyAlphabetTest() {
    	String[] trainingData = {"aa", "bb"};
    	assertNull(AlphabetGenerator.generateFrequencyAlphabet(-1, trainingData));
    }
    
    @Test
    public void ignoredCharFrequencyAlphabetTest() {
        // Test to make sure that non a-z chars are ignored
    	String[] trainingData= {"128abc", "cx-!!@#$%&^*&*"};
    	char[] expectedOutput = {'a', 'a', 'b', 'b',
    							 'c', 'c', 'c', 'c',
    							 'x', 'x'};
    	assertArrayEquals(expectedOutput, AlphabetGenerator.generateFrequencyAlphabet(10, trainingData));
    }
    
    @Test
    public void charOrderFrequencyAlphabetTest() {
    	// The returned output should be sorted
    	
    	String[] trainingData = {"zaz", "xy"};
    	char[] expectedOutput = {'a', 'a', 'x', 'x',
    							 'y', 'y', 'z', 'z',
    							 'z', 'z'};
    	assertArrayEquals(expectedOutput, AlphabetGenerator.generateFrequencyAlphabet(10, trainingData));
    }

	@Test
	public void fractionalFrequencyAlphabetTest() {
		// How to handle bases that don't divide evenly into the number of letters?
		// Truncate if too long
		String[] trainingData = {"abc"};
		char[] expectedOutput = {'a', 'a', 'b', 'c', 'c'};
		assertArrayEquals(expectedOutput, AlphabetGenerator.generateFrequencyAlphabet(5, trainingData));
		
		// If too short, pad it
		String[] trainingData1 = {"abcd"};
		char[] expectedOutput1 = {'a', 'b', 'b', 'c', 'd'};
		assertArrayEquals(expectedOutput1, AlphabetGenerator.generateFrequencyAlphabet(5, trainingData1));
		}
	
	@Test
	public void baseLessThanFrequencyAlphabetTest() {
		// Truncate then calculate frequencies if the the base is less than the number of characters
		
		String[] trainingData = {"aaabcd"};
		char[] expectedOutput = {'a', 'a', 'c'};
		
		assertArrayEquals(expectedOutput, AlphabetGenerator.generateFrequencyAlphabet(3, trainingData));
	}
}
