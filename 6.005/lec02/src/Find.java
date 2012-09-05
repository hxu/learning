
public class Find {
    
    /**
     * Find the first occurrence of x in sorted array a.
     * @param x value to find
     * @param a array sorted in increasing order (a[0] <= a[1] <= ... <= a[n-1])
     * @return lowest i such that a[i]==x, or -1 if x not found in a. 
     */
    public static int find(int x, int[] a) {
        return binarySearchInRange(x, a, 0, a.length);
    }

    /*
     * Find the first occurrence of x in sorted array a[first..max-1].
     * @param x value to find
     * @param a array sorted in increasing order 
     *          (a[0] <= a[1] <= ... <= a[n-1])
     * @param first low end of range. 
     *              Requires 0 <= first <= a.length-1.
     * @param max high end of range.  
     *              Requires 0 <= max < a.length, and max >= first.
     * @return lowest i such that first<=i<max and a[i]==x, 
     *         or -1 if there's no such i. 
     */
    private static int binarySearchInRange(int x, int[] a, int first, int max) {
        if (first >= max) {
            return -1; // range has dwindled to nothingness
        }
        
       int mid = (first + max) / 2;
        if (x < a[mid]) {
            return binarySearchInRange(x, a, first, mid);
        } else if (x > a[mid]) {
            return binarySearchInRange(x, a, mid+1, max);
        } else {
            // x == a[mid]... we found it, but check if it's the first
            if (mid > 0 && x == a[mid-1]) {
                // not the first! search lower half
                return binarySearchInRange(x, a, first, mid);
            } else {
                return mid; // it's the first
            }
        }        
    }
    
    /*
     * Same spec as find().  Uses linear search.
     */
    private static int linearSearch(int x, int[] a) {
        for (int i = 0; i < a.length; ++i) {
            if (x == a[i]) {
                return i;
            }
        }
        return -1;
    }
}
