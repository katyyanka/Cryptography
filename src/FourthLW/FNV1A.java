package FourthLW;

public class FNV1A {
    private final int FNV_prime = 0x1000193;
    private final int FNV_offset_basic = 0x811C9DC5;

    //Функция проста в реализации. Её основа — умножение на простое число и сложение по модулю 2 с входным текстом.
    public int FNV1AHash(String input){

        int hash = FNV_offset_basic;
        char[] charsOfInput = input.toCharArray();
        for (var item: charsOfInput) {
            hash ^= item;
            hash *= FNV_prime;
        }
        return hash;
    }

    public static void main(String[] args) {
        System.out.println(Integer
                .toHexString((new FNV1A())
                        .FNV1AHash("BSUIR")));
        //должно получится на выходе ff7cbdf2 - получилось
    }
}
