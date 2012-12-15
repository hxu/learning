package piwords;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Testing {
	public static void main(String[] args) {
		String[] trainingData = {"test"};
		char[] res = AlphabetGenerator.generateFrequencyAlphabet(3, trainingData);
		System.out.println(Arrays.toString(res));
	}

}
