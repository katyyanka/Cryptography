package FirstLW;

import java.util.*;

public class RailwayFence {


    public static void main(String[] args) {

        System.out.println("Ш - шифрование \nР - расшифровка \n    Ваш выбор:");
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        if (s1.equalsIgnoreCase("Ш")) {
            System.out.println("Пожалуйста, введите простой текст:");
            String s = scanner.nextLine();
            System.out.println("Пожалуйста, введите ключ:");
            int key;
            try {
                key = scanner.nextInt();
                System.out.println(encrypt(s, key));
            } catch (InputMismatchException e) {
                System.out.println("Ключ должен быть целым числом!");
            }
        } else if (s1.equalsIgnoreCase("Р")) {
            System.out.println("Пожалуйста, введите зашифрованный текст:");
            String s = scanner.nextLine();
            int key;
            System.out.println("Пожалуйста, введите ключ:");
            try {
                key = scanner.nextInt();
                System.out.println(decrypt(s, key));
            } catch (InputMismatchException e) {
                System.out.println("Ключ должен быть целым числом!");
            }
        } else
            System.out.println("До Свидания!");
    }

    static String encrypt(String text, int n) {
        int period = 2 * (n - 1);
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = "";
        }
        for (int j = 0; j < text.length(); j++) {
            strings[n - 1 - Math.abs(n - 1 - j % period)] += text.charAt(j);
        }
        for (String s : strings) {
            System.out.println(s);
        }
        StringBuilder result = new StringBuilder();
        for (String s : strings) {
            result.append(s);
        }
        return result.toString();
    }

    static String decrypt(String text, int n) {
        int period = 2 * (n - 1);
        if (n == 1 || text.length()<n) return text;

        List<Character> chars = new LinkedList<>();
        char[] strings2 = new char[text.length()];

        for (int i = 0; i < text.length(); i++) {
            strings2[i] = '♥';
            chars.add(text.charAt(i));
        }

        int j = 0;
        for (int i = 0; j < chars.size() && i < text.length(); i += period, j++) {
            strings2[i] = text.charAt(j);
            chars.remove(0);
        }

        int k = 2;
        period -= 2;
        if (period == 0) period = 2;
        int o;
        for (int i = 0; i < n - 1; i++, k += 2, period -= 2) {
            for (o = i + 1;
                 o < text.length() && chars.size()!=0;
                 o += k + period) {

                strings2[o] = chars.get(0);
                chars.remove(0);
                if (chars.size() != 0 && (o + period) < text.length() && period!=0) {
                    strings2[o + period] = chars.get(0);
                    chars.remove(0);
                }
            }
        }
        System.out.println(Arrays.toString(strings2));
        System.out.println(chars);
        return Arrays.toString(strings2);
    }

}
