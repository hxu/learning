package markup;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShuffleTest {

    @Test
    public void testSimpleShuffle() {
        String original = "abcdefghijklmnopqrstuvwxyz";
        String shuffled = Gallileo.shuffle(original);
        assertEquals(original.length(), shuffled.length());
        assertSubsetOf(original, shuffled);
        assertSubsetOf(shuffled, original);
    }
    
    // succeeds if every character in a is also found in b
    private static void assertSubsetOf(String a, String b) {
        for (int i = 0; i < a.length(); ++i) {
            if (b.indexOf(a.charAt(i)) == -1) {
                fail();
            }
        }
    }

}
