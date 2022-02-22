package FirstLW;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cezar2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Message: ");
        String message = "";
        int key = 0;
        try {
            message = scanner.nextLine();
            System.out.print("Key: ");
            key = scanner.nextInt();
        } catch (Exception e){
            System.err.println("Very bad :<(, key can be int");
            return;
        }


        StringBuilder result = new StringBuilder();
        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - 'a';
                int newAlphabetPosition = (originalAlphabetPosition * key) % 26;
                char newCharacter = (char) ('a' + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }
        System.out.println(result);
    }
}

