package SecondLW;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SDES {

    public int Key1, Key2;

    public static final int[][] SBOX1 = {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 3, 2}
    };
    public static final int[][] SBOX2 = {
            {0, 1, 2, 3},
            {2, 0, 1, 3},
            {3, 0, 1, 2},
            {2, 1, 0, 3}
    };

    public static final int[] P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    public static final int P10max = 10;
    public static final int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};
    public static final int P8max = 10;

    public static final int[] P4 = {2, 4, 3, 1}; //правило для боксов
    public static final int P4max = 4;
    public static final int[] IP = {2, 6, 3, 1, 4, 8, 5, 7}; //правило начальной перестановки
    public static final int IPmax = 8;
    public static final int[] IPI = {4, 1, 3, 5, 7, 2, 8, 6};//выходная перестановка
    public static final int IPImax = 8;
    public static final int[] EP = {4, 1, 2, 3, 2, 3, 4, 1}; //для боксов
    public static final int EPmax = 4;



    //1001010011
    //10110110


    public static int permute(int origin, int[] ruleArray, int pmax) {
        int result = 0;
        for (int j : ruleArray) {
            result = result << 1;
            result = origin >> (pmax - j) & 1 | result; //pr
        }
        return result;
    }

    public static void print(int x, int n) {
        int mask = 1 << (n - 1);
        while (mask > 0) {
            System.out.print(((x & mask) == 0) ? '0' : '1');
            mask >>= 1;
        }
    }

    public static int workWithSBoxes(int R, int K) {
        int result = permute( R, EP, EPmax) ^ K;
        int t0 = (result >> 4) & 0b1111;
        int t1 = result & 0b1111;
        t0 = SBOX1[ ((t0 & 0b1000) >> 2) | (t0 & 1) ][ (t0 >> 1) & 0b11 ];
        t1 = SBOX2[ ((t1 & 0b1000) >> 2) | (t1 & 1) ][ (t1 >> 1) & 0b11 ];
        result = permute( (t0 << 2) | t1, P4, P4max);
        return result;
    }

    public static int round(int m, int K) {// 2 и 4 этапы
        int L = (m >> 4) & 0b1111;         // здесь делим на левую и правую часть, левая останется без изменений, в то время как правая попадает в сбокс
        int R = m & 0b1111;
        return ((L ^ workWithSBoxes(R,K)) << 4) | R;
    }

    public static int swap(int x) {           // 3 этап
        return ((x & 0b1111) << 4) | ((x >> 4) & 0b1111);
    }

    public byte encrypt(int byteMessage) {
        byteMessage = permute(byteMessage, IP, IPmax); // 1 этап перестановка
        byteMessage = round(byteMessage, Key1);        // 2 этап первый раунд с ключом 1
        byteMessage = swap(byteMessage);               // 3 раун перестановка частей местами
        byteMessage = round(byteMessage, Key2);        //4 этап второй раун с ключом 2
        byteMessage = permute(byteMessage, IPI, IPImax);//5 этап выходная перестановка
        return (byte) byteMessage;
    }

    public byte decrypt(int m) {
        System.out.println("\nDecryption Process Starts........");
        print(m, 8);
        m = permute(m, IP, IPmax);
        System.out.print("\nAfter Permutation : ");
        print(m, 8);
        m = round(m, Key2);
        System.out.print("\nbefore Swap : ");
        print(m, 8);
        m = swap(m);
        System.out.print("\nAfter Swap : ");
        print(m, 8);
        m = round(m, Key1);
        System.out.print("\nBefore Extraction Permutation : ");
        print(m, 4);
        m = permute(m, IPI, IPImax);
        System.out.print("\nAfter Extraction Permutation : ");
        print(m, 8);
        return (byte) m;
    }

    public SDES(int MainKey) {
        MainKey = permute(MainKey, P10, P10max);
        print(MainKey, 10);
        System.out.println(" main key after permutation");

        int left = (MainKey >> 5);
        int right = MainKey;
        left = ((left & 0b1111) << 1) | ((left & 0b10000) >> 4);
        right = ((right & 0b1111) << 1) | ((right & 0b10000) >> 4);
        Key1 = permute((left << 5) | right, P8, P8max);

        left = ((left & 0x7) << 2) | ((left & 0x18) >> 3);
        right = ((right & 0x7) << 2) | ((right & 0x18) >> 3);
        Key2 = permute((left << 5) | right, P8, P8max);

        /* System.out.print("\nKey K1: ");
        SDES.print(Key1, 8);
        System.out.print("\nKey K2: ");
        SDES.print(Key2, 8);*/
    }

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("E-encrypt, D-decrypt: ");

        String code = scanner.nextLine();
        if (code.equals("E") || code.equals("e")){

            byte[] fileBytes = file("filename.bin");
            DataInputStream inp = new DataInputStream(System.in);
            int Key10Bit = -1;
            try {
                do {
                    if (Key10Bit != -1) {
                        System.out.println("Too big key!");
                    }
                    System.out.println("Enter the 10 Bit Key :"); //1001010011
                    Key10Bit = Integer.parseInt(inp.readLine(), 2);

                } while (Key10Bit > 0b1111111111);
                SDES sdes = new SDES(Key10Bit);
                byte[] m = new byte[fileBytes.length]; //10110110
                OutputStream out = (new BufferedOutputStream(new FileOutputStream("newfile.txt")));
                for (int i = 0; i< file("filename.bin").length; i++){
                    m[i] = sdes.encrypt( fileBytes[i]);
                }
                out.write(m);
                out.close();

            } catch (NumberFormatException exception) {
                System.out.println("Number must be in binary system");
            }


        } else if (code.equals("D") || code.equals("d")){
            byte[] fileBytes = file("newfile.txt");
            DataInputStream inp = new DataInputStream(System.in);
            int Key10Bit = -1;
            try {
                do {
                    if (Key10Bit != -1) {
                        System.out.println("Too big key!");
                    }
                    System.out.println("Enter the 10 Bit Key :"); //1001010011
                    Key10Bit = Integer.parseInt(inp.readLine(), 2);

                } while (Key10Bit > 0b1111111111);
                SDES sdes = new SDES(Key10Bit);
                System.out.println("Enter the 8 Bit message To be Encrypt  : ");
                byte[] m = new byte[fileBytes.length]; //10110110
                OutputStream out = (new BufferedOutputStream(new FileOutputStream("newfile2.txt")));
                for (int i = 0; i< file("newfile.txt").length; i++){
                    m[i] = sdes.decrypt( fileBytes[i]);
                }
                out.write(m);
                out.close();

            } catch (NumberFormatException exception) {
                System.out.println("Number must be in binary system");
            }
        } else {
            System.err.println("Bad choice :(");
        }
    }

    static byte[] file(String s) throws IOException {
        ArrayList<Byte> bytesFromFile = new ArrayList<>();
        File file = new File(s);
        byte[] fileData = new byte[(int) file.length()];
        FileInputStream in = new FileInputStream(file);
        in.read(fileData);
        in.close();
        return fileData;
    }

}