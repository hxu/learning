import java.util.ArrayList;
import java.util.List;

public class Hailstone {

    /**
     * Compute the hailstone sequence.
     * See http://en.wikipedia.org/wiki/Collatz_conjecture#Statement_of_the_problem
     * @param n starting number of sequence
     * @return the hailstone sequence starting at n and ending with 1.
     * For example, hailstone(3)=[3,10,5,16,8,4,2,1].
     */
    public static List<Integer> hailstone(int n) {
        List<Integer> l = new ArrayList<Integer>();
        
        while (n != 1) {
            l.add(n);
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
        }        
        
        l.add(n); // don't forget the final 1
        return l;
    }

	/**
	 * Find the largest element in a list.
	 * @param l list of elements.  Requires l to be nonempty 
	 * and all elements to be nonnegative.
	 * @return the largest element in l
	 */
	public static int max(List<Integer> l) {
		int m = 0;
		for (int x : l) {
			if (x > m) {
				m = x;
			}
		}
		return m;
	}
	
	/**
	 * Main program.  Print the peak of the hailstone
	 * sequence for a range of starting n's.
	 */
	public static void main(String[] args) {
		for (int n = 1; n < 1000000; n++) {
		    List<Integer> l = hailstone(n);
		    System.out.println(n + " >> " + max(l));
		}
	}
}
