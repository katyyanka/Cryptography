package FirstLW;

public class k {

    private static final short NUMBER_OF_ENGLISH_LETTERS = 26;

    public static void main(String[] args) {

        int i = 0;
        while (true) {
            if ((3 * i) % NUMBER_OF_ENGLISH_LETTERS == 1) {
                System.out.println(i);
                break;
            }
            if (i > 1_000_000) {
                System.out.println("число больше миллиона");
                break;
            }
            i++;
        }

    }
}
