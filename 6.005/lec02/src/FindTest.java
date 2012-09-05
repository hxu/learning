import org.junit.Test;
import static org.junit.Assert.*;

public class FindTest {
    
    // Testing strategy for i = search(x, a): 
    //     partition the space of (x, a, i) as follows
    //
    // x: neg, 0, pos
    // a.length: 0, 1, 2+
    // a.vals: neg, 0, pos; all same, increasing; 
    // i: 0, middle, n-1, -1

    @Test
    public void testEmpty() {
        // x=2, a=[-1, 1, 3], i=-1
        assertEquals(-1, Find.find(1, new int[] {}));
    }
    
    @Test
    public void testSingleton() {
        // x=0, a=[0], i=0
        assertEquals(0, Find.find(0, new int[] {0}));
    }
    
    @Test
    public void testMiddle() {
        // x=1, a=[-1, 1, 3], i=1
        assertEquals(1, Find.find(1, new int[] {-1, 1, 3}));
    }
    
    @Test
    public void testStart() {
        // x=-1, a=[-1, 1, 3], i=0
        assertEquals(0, Find.find(-1, new int[] {-1, 1, 3}));
    }
    
    @Test
    public void testEnd() {
        // x=3, a=[-1, 1, 3], i=n-1
        assertEquals(2, Find.find(3, new int[] {-1, 1, 3}));
    }
    
    @Test
    public void testNotFound() {
        // x=2, a=[-1, 1, 3], i=-1
        assertEquals(-1, Find.find(2, new int[] {-1, 1, 3}));
    }
    
    @Test
    public void testAllSame() {
        // x=1, a=[1, 1, 1], i=0
        assertEquals(0, Find.find(1, new int[] {1, 1, 1}));
    }
}
