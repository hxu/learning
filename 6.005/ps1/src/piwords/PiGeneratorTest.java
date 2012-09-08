package piwords;

import static org.junit.Assert.*;

import org.junit.Test;

public class PiGeneratorTest {
    @Test
    public void basicPowerModTest() {
        // 5^7 mod 23 = 17
        assertEquals(17, PiGenerator.powerMod(5, 7, 23));
        
    }
    
    @Test
    public void negativePowerModTest() {
        // check for negative numbers
        assertEquals(-1, PiGenerator.powerMod(-1, 7, 23));
        assertEquals(-1, PiGenerator.powerMod(5, -1, 23));
        assertEquals(-1, PiGenerator.powerMod(5, 7, -1));
    }
    
    @Test
    public void zeroPowerModTest() {
        // check for 0s
        assertEquals(0, PiGenerator.powerMod(0, 7, 23));
        assertEquals(1, PiGenerator.powerMod(5, 0, 23));
    }
    
    @Test(expected=ArithmeticException.class)
    public void divbyzeroPowerModTest() {
        PiGenerator.powerMod(5, 7, 0);
    }

    // TODO: Write more tests (Problem 1.a, 1.c)
}
