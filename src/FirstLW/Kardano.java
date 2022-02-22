package FirstLW;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Kardano {

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

        public static void printPoints(Point[] points) {
            int i = 1;
            for (Point xy : points) {
                System.out.println(i++ + " - (" + xy.getX() + "," + xy.getY() + ")");
            }
        }

        @Override
        public boolean equals(Object obj) {
            return this.x == ((Point) obj).getX() && this.y == ((Point) obj).getY();
        }
    }

    static private final int[][] GRID = new int[][]{
            {1, 2, 3, 1},
            {3, 4, 4, 2},
            {2, 4, 4, 3},
            {1, 3, 2, 1},
    };

    static Point[][] points = {
            {new Point(0, 0), new Point(3, 0), new Point(3, 3), new Point(0, 3)},
            {new Point(0, 1), new Point(2, 0), new Point(3, 2), new Point(1, 3)},
            {new Point(0, 2), new Point(1, 0), new Point(3, 1), new Point(2, 3)},
            {new Point(1, 1), new Point(2, 1), new Point(2, 2), new Point(1, 2)}
    };

    public static void main(String[] args) {

        System.out.println("Ш - шифрование \nР - расшифровка \n    Ваш выбор:");
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        if (s1.equalsIgnoreCase("Ш")) {
            System.out.println("Пожалуйста, введите простой текст:");
            String s = scanner.nextLine();
            List<String> strings = splitEqually(s, 16);
            StringBuilder encrypted = new StringBuilder();
            try {
                Point[] keys = getKeys();
                for (String substring: strings) {

                    encrypted.append(Encryption(substring, keys));
                }
                System.out.println("Строка после шифрования: "+ encrypted);
            } catch (InputMismatchException e) {
                System.out.println("Ключ должен быть целым числом!");
            }
        } else if (s1.equalsIgnoreCase("Р")) {
            System.out.println("Пожалуйста, введите зашифрованный текст:");
            String s = scanner.nextLine();
            List<String> strings = splitEqually(s, 16);
            StringBuilder encrypted = new StringBuilder();
            try {
                Point[] keys = getKeys();
                for (String substring: strings) {
                    encrypted.append(Decrypt(substring,keys));
                }
                System.out.println("Строка после расшифрования: "+ encrypted);
            } catch (InputMismatchException e) {
                System.out.println("Ключ должен быть целым числом!");
                return;
            }
        } else
            System.out.println("До Свидания!");
    }



    public static String Decrypt(String str, Point[] keys) {

        StringBuilder partOfFinalDecryptedString = new StringBuilder();
        char[] arrayOfChars = str.toCharArray();
        char[][] gridOfChars = new char[4][4];
        for (int i = 0, k = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++, k++) {
                gridOfChars[i][j] = arrayOfChars[k];
            }
        }
        for (int i = 0; i < 4; i++) {
            /* 1 СИМВОЛ*/
            partOfFinalDecryptedString.append(gridOfChars[keys[0].x][keys[0].y]);
            keys[0] = getNextZeroPoint(keys[0]);
            /* 2 СИМВОЛ*/
            partOfFinalDecryptedString.append(gridOfChars[keys[1].x][keys[1].y]);
            keys[1] = getNextFirstPoint(keys[1]);
            /* 3 СИМВОЛ*/
            partOfFinalDecryptedString.append(gridOfChars[keys[2].x][keys[2].y]);
            keys[2] = getNextSecondPoint(keys[2]);
            /* 4 СИМВОЛ*/
            partOfFinalDecryptedString.append(gridOfChars[keys[3].x][keys[3].y]);
            keys[3] = getNextThirdPoint(keys[3]);
        }

        for (int i= partOfFinalDecryptedString.length()-1; i>=0; i--){
            if (partOfFinalDecryptedString.charAt(i)=='☺'){
               // partOfFinalDecryptedString = new StringBuilder((partOfFinalDecryptedString.substring(0, partOfFinalDecryptedString.length() - 1)));
                partOfFinalDecryptedString.deleteCharAt(i);//♥☻☺♦♣♠•◘○◙♂♀♪♫☼►◄↕‼¶§▬↨
            }
        }

        System.out.println(str + "После расшифровки:" + partOfFinalDecryptedString);
        return String.valueOf(partOfFinalDecryptedString);
    }

    public static StringBuilder Encryption(String str, Point[] keys) {
        StringBuilder partOfFinalEncryptedString = new StringBuilder();
        char[][] gridOfChars = new char[4][4];
        StringBuilder strBuilder = new StringBuilder(str);
        strBuilder.append("☺".repeat(Math.max(0, 16 - strBuilder.length())));
        str = strBuilder.toString();
        for (int i = 0; i < 4; i++) {
            /* 1 СИМВОЛ*/
            gridOfChars[keys[0].x][keys[0].y] = str.charAt(i * 4);
            keys[0] = getNextZeroPoint(keys[0]);
            /* 2 СИМВОЛ*/
            gridOfChars[keys[1].x][keys[1].y] = str.charAt(i * 4 + 1);
            keys[1] = getNextFirstPoint(keys[1]);
            /* 3 СИМВОЛ*/
            gridOfChars[keys[2].x][keys[2].y] = str.charAt(i * 4 + 2);
            keys[2] = getNextSecondPoint(keys[2]);
            /* 4 СИМВОЛ*/
            gridOfChars[keys[3].x][keys[3].y] = str.charAt(i * 4 + 3);
            keys[3] = getNextThirdPoint(keys[3]);
        }
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                System.out.print(gridOfChars[j][k]);
                partOfFinalEncryptedString.append(gridOfChars[j][k]);
            }
            System.out.println();
        }
        System.out.println("\n" + str + " После шифрования: " + partOfFinalEncryptedString);
        return partOfFinalEncryptedString;
    }

    private static Point[] getKeys() throws InputMismatchException {
        Point[] key = new Point[points.length];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < points.length; i++) {
            Point.printPoints(points[i]);
            System.out.println("Ключ ячейки [" + (i + 1) + "] : ");
            int numberOfKey = 0;
            numberOfKey = scanner.nextInt();
            if (numberOfKey < 1 || numberOfKey > 4) {
                i--;
                System.out.println("Повторите ввод:");
            } else key[i] = points[i][numberOfKey - 1];
        }
        System.out.println("__________________");
        for (Point xy : key) {
            System.out.println("[" + xy.getX() + "][" + xy.getY() + "]");
        }
        return key;
    }

    static Point getNextZeroPoint(Point point) {
        if (point.equals(points[0][0])) {
            point = points[0][1];
        } else if (point.equals(points[0][1])) {
            point = points[0][2];
        } else if (point.equals(points[0][2])) {
            point = points[0][3];
        } else if (point.equals(points[0][3])) {
            point = points[0][0];
        }
        return point;
    }

    static Point getNextFirstPoint(Point point) {
        if (point.equals(points[1][0])) {
            point = points[1][1];
        } else if (point.equals(points[1][1])) {
            point = points[1][2];
        } else if (point.equals(points[1][2])) {
            point = points[1][3];
        } else if (point.equals(points[1][3])) {
            point = points[1][0];
        }
        return point;
    }

    static Point getNextSecondPoint(Point point) {
        if (point.equals(points[2][0])) {
            point = points[2][1];
        } else if (point.equals(points[2][1])) {
            point = points[2][2];
        } else if (point.equals(points[2][2])) {
            point = points[2][3];
        } else if (point.equals(points[2][3])) {
            point = points[2][0];
        }
        return point;
    }

    static Point getNextThirdPoint(Point point) {
        if (point.equals(points[3][0])) {
            point = points[3][1];
        } else if (point.equals(points[3][1])) {
            point = points[3][2];
        } else if (point.equals(points[3][2])) {
            point = points[3][3];
        } else if (point.equals(points[3][3])) {
            point = points[3][0];
        }
        return point;
    }

    public static List<String> splitEqually(String text, int size) {
        List<String> ret = new ArrayList<>();
        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }
}
