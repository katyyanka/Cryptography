package ThirdLW;

import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;
import java.util.Scanner;

public class ElGomal {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        BigInteger p, q, y, secretKey;
        ArrayList<BigInteger> aList = new ArrayList<>();
        ArrayList<BigInteger> bList = new ArrayList<>();

        Random sc = new SecureRandom();
        secretKey = new BigInteger("3");

        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(16, sc);
        q = new BigInteger("2");

        /* p = BigInteger.valueOf(11);
        q = BigInteger.valueOf(2);
        secretKey = BigInteger.valueOf(8);*/
        /**значение для теста: p=11,q=2,x=8,y=3,a=6,b=9,m=5,k=9*/
        y = q.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("y = " + y);
        BigInteger k;
        do {
            k = new BigInteger(16, sc);
        } while (k.intValue() < 1 & k.intValue() > p.subtract(new BigInteger("1")).intValue() & k.gcd(p).intValue() != 1);
        System.out.println("Целое число k такое, что 1 < k < (p − 1): " + k);

        System.out.print("Введите сообщение --> ");
        String s = scan.nextLine();

        for(char ch: s.toCharArray()){
            BigInteger m = new BigInteger(String.valueOf((int)ch));
            System.out.println("Сообщение = " + m + "(" + ch + ")");

            BigInteger a = q.modPow(k, p);
            aList.add(a);
            System.out.println("Первая часть зашифрованного сообщения q^k mod p = " + a);
            BigInteger b = y.pow(k.intValue()).multiply(m).mod(p);
            System.out.println("Вторая часть зашифрованного сообщения (y^k)*m mod p = " + b);
            bList.add(b);
            System.out.println("__________________________________________________________");
        }


        System.out.println("DECRYPTION");
        for (int i = 0; i < aList.size(); i++) {

            BigInteger expKatya = p.subtract(BigInteger.valueOf(1)).subtract(secretKey);
            BigInteger resKatya = bList.get(i).multiply(aList.get(i).pow(expKatya.intValue())).mod(p);
            System.out.print((char) resKatya.intValue());
        }
        System.out.println("\n☺️☻☺️☻☺️☻☺️☻☺️☻☺️☻☺️☻☺️☻☺️☻☺️☻☺️");
    }
}
