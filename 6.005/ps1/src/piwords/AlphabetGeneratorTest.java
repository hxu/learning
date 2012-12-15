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
	}

}
