package FirstLW;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Keyword {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("E-encrypt, D-decrypt: ");

        String code = scanner.nextLine();
        if (code.equals("E") || code.equals("e")){
            System.out.print("Enter message: ");
            String msg = scanner.nextLine();
            System.out.print("Enter key: ");
            String key = scanner.nextLine();
            encrypt(key, msg);
        } else if (code.equals("D") || code.equals("d")){
            System.out.print("Enter message: ");
            String msg = scanner.nextLine();
            System.out.print("Enter key: ");
            String key = scanner.nextLine();
            decrypt(key, msg);
        } else {
            System.err.println("Bad choice :(");
        }
    }

    public static void encrypt(String key, String str){

        int index = 0;
        int bias = 0;
        Map<Integer, Integer> numKey = new TreeMap<>();
        for (int i = 0; i < key.length(); i++) {
            int position = key.charAt(i);
            int asciiWithBias = numKey.containsValue(position + bias)
                    ? position + ++bias : position + bias;
            numKey.put(i, asciiWithBias);
            System.out.println(i+" "+(char)((int)numKey.get(i)));
        }

        ArrayList<Character> result = new ArrayList<>();
        for (int i = 0; i < str.length() / key.length() + str.length() % key.length(); i++) {
            Map<Integer, Character> charPos = new TreeMap<>();
            for (Map.Entry<Integer, Integer> asciiByIndex : numKey.entrySet()) {
                if (index == str.length()) {
                    break;
                }
                charPos.put(asciiByIndex.getValue(), str.charAt(index++));
            }
            charPos.forEach((keyVal, value) -> result.add(value));
        }

        for (Character a: result){
            System.out.print(a);
        }
        System.out.println();
    }

    public static void decrypt(String keys, String str){
        char[] output = new char[str.length()];

        int bias = 0;
        Map<Integer, Integer> numKey = new TreeMap<>();
        for (int i = 0; i < keys.length(); i++) {
            int position = keys.charAt(i);
            int asciiWithBias = numKey.containsValue(position + bias)
                    ? position + ++bias : position + bias;
            numKey.put(i, asciiWithBias);
        }

        Map<Integer, Integer> indexesByAscii = new TreeMap<>();
        numKey.forEach((key, value) -> indexesByAscii.put(value, key));

        int messageIndex = 0;
        int step = 0;

        for (int i = 0; i < str.length() / keys.length() + str.length() % keys.length(); i++) {
            for (Map.Entry<Integer, Integer> indexByAscii : indexesByAscii.entrySet()) {
                if (messageIndex > str.length() - 1) {
                    break;
                }
                if (indexByAscii.getValue() + step >= str.length()) {
                    continue;
                }
                output[indexByAscii.getValue() + step] = str.charAt(messageIndex++);
            }
            step += keys.length();
        }
        System.out.println(new String(output));
    }
}
