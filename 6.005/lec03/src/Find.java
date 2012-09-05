

public class Find {
    
    public static int findA (int x, int[] a) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] == x) {
                return i;
            }
        }
        return a.length;
    }
    
    public static int findB (int x, int[] a) {
        for (int i = a.length-1; i >= 0; --i) {
            if (a[i] == x) {
                return i;
            }
        }
        return -1;
    }
    
}
