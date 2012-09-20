package piwords;

public class TryMath {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] input = {1, 0, 1, 0 ,1};
        int[] a = BaseTranslator.convertBase(input, 3, 10, 6);
        for (int i = 0; i < a.length ; ++i) {
            System.out.println(a[i]);
        }
    }

}
