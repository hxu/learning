package piwords;

public class TryMath {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] a = PiGenerator.computePiInHex(5);
        for (int i = 0; i < 5; ++i) {
            System.out.println("i is " + i);
            System.out.println(a[i]);
        }
    }

}
