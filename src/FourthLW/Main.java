package FourthLW;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
            StringBuilder builder = new StringBuilder();

            try (FileReader reader = new FileReader("src/FourthLW/origin.txt")) {
                while (reader.ready()) {
                    builder.append(reader.read());
                }
            } catch (Exception ignore) {}
        System.out.println("+-+-+-+-+-+-+-+RESULT+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-"+"\n");
        System.out.println(Long.toHexString(new FNV1A().FNV1AHash(builder.toString()))+"\n\n");
        System.out.println("+-+-+-+-+-+-+-+RESULT OF BSUIR+-+-+-+-+-+-+-+-+-+-+-+"+"\n");
        System.out.println(Long.toHexString(new FNV1A().FNV1AHash("BSUIR")));
    }
}
