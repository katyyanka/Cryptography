package FirstLW;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Cezar {

    private static final short NUMBER_OF_ENGLISH_LETTERS = 26;
    private static final short NUMBER_OF_RUSSIAN_LETTERS = 32;

    public static void main(String[] args) {
        System.out.println("Ш - шифрование \nР - расшифровка \n    Ваш выбор:");
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        if (s1.equalsIgnoreCase("Ш")) {
            System.out.println("Пожалуйста, введите простой текст:");
            String s = scanner.nextLine();
            System.out.println("Ключ: ");
            int key;
            try {
                key = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Ключ должен быть целым числом!");
                return;
            };
            Encryption(s, key);// Вызываем метод шифрования
        } else if (s1.equalsIgnoreCase("Р")) {
            System.out.println("Пожалуйста, введите зашифрованный текст:");
            String s = scanner.nextLine();
            System.out.println("Ключ:");
            int key;
            try {
                key = scanner.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Ключ должен быть целым числом!");
                return;
            };
            Decrypt(s, key);// Вызываем метод шифрования
        } else
            System.out.println("До Свидания!");
    }

    public static void Decrypt(String str, int n) {
        int k = -n;
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt >= 'a' && charAt <= 'z')
            {
                charAt += k % NUMBER_OF_ENGLISH_LETTERS;
                if (charAt < 'a')
                    charAt += NUMBER_OF_ENGLISH_LETTERS;
                if (charAt > 'z')
                    charAt -= NUMBER_OF_ENGLISH_LETTERS;
            } else if (charAt >= 'A' && charAt <= 'Z')
            {
                charAt += k % NUMBER_OF_ENGLISH_LETTERS;
                if (charAt < 'A')
                    charAt += NUMBER_OF_ENGLISH_LETTERS;
                if (charAt > 'Z')
                    charAt -= NUMBER_OF_ENGLISH_LETTERS;
            }
            else if (charAt >= 'а' && charAt <= 'я')
            {
                charAt += k % NUMBER_OF_RUSSIAN_LETTERS;
                if (charAt < 'а')
                    charAt += NUMBER_OF_RUSSIAN_LETTERS;
                if (charAt > 'я')
                    charAt -= NUMBER_OF_RUSSIAN_LETTERS;
            } else if (charAt >= 'А' && charAt <= 'Я')
            {
                charAt += k % NUMBER_OF_RUSSIAN_LETTERS;// мобильный ключ% 26 бит
                if (charAt < 'А')
                    charAt += NUMBER_OF_RUSSIAN_LETTERS;// слева налево
                if (charAt > 'Я')
                    charAt -= NUMBER_OF_RUSSIAN_LETTERS;// направо
            }
            string.append(charAt);// Объединяем расшифрованные символы в строку
        }
        System.out.println(str + " После расшифровки: " + string);
    }

    public static void Encryption(String str, int k) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt >= 'a' && charAt <= 'z')
            {
                charAt += k % NUMBER_OF_ENGLISH_LETTERS;
                if (charAt < 'a')
                    charAt += NUMBER_OF_ENGLISH_LETTERS;
                if (charAt > 'z')
                    charAt -= NUMBER_OF_ENGLISH_LETTERS;
            } else if (charAt >= 'A' && charAt <= 'Z')
            {
                charAt += k % NUMBER_OF_ENGLISH_LETTERS;
                if (charAt < 'A')
                    charAt += NUMBER_OF_ENGLISH_LETTERS;
                if (charAt > 'Z')
                    charAt -= NUMBER_OF_ENGLISH_LETTERS;
            } else if (charAt >= 'а' && charAt <= 'я')
            {
                charAt += k % NUMBER_OF_RUSSIAN_LETTERS;
                if (charAt < 'а')
                    charAt += NUMBER_OF_RUSSIAN_LETTERS;
                if (charAt > 'я')
                    charAt -= NUMBER_OF_RUSSIAN_LETTERS;
            } else if (charAt >= 'А' && charAt <= 'Я')
            {
                charAt += k % NUMBER_OF_RUSSIAN_LETTERS;
                if (charAt < 'А')
                    charAt += NUMBER_OF_RUSSIAN_LETTERS;
                if (charAt > 'Я')
                    charAt -= NUMBER_OF_RUSSIAN_LETTERS;
            }
            string.append(charAt);
        }
        System.out.println("\n"+str + " После шифрования: " + string);
    }
}
