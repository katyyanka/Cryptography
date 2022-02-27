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
        secretKey = new BigInteger("1234");
        //
        // public key calculation
        //
        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(64, sc);
        q = new BigInteger("3");
        y = q.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("y = " + y);
        //
        // Encryption
        //
        System.err.print("Введите сообщение --> ");
        String s = scan.next();

        for(char ch: s.toCharArray()){

            BigInteger m = new BigInteger(String.valueOf((int)ch));
            System.out.println("Сообщение = " + m);

            BigInteger k = new BigInteger(64, sc); //Выбирается случайное секретное число k(1, p−1), взаимно простое с p−1.
            System.out.println("целое число k такое, что 1 < k < (p − 1) ---> k = " + k);

//            BigInteger EC = m.multiply(y.modPow(k, p)).mod(p);
            BigInteger a = q.modPow(k, p);
            aList.add(a);
            System.out.println("Первая часть зашифрованного сообщения q^k mod p = " + a);
//            BigInteger crmodp = a.modPow(secretKey, p);
            BigInteger b = y.mod(k).multiply(m).mod(p);
            System.out.println("Вторая часть зашифрованного сообщения (y^k)*m mod p = " + b);
            bList.add(b);
            System.out.println("------------");
        }


        //
        // Decryption
        //
        System.out.println("DECRYPTION");
        for(int i = 0; i < aList.size(); i++){
            BigInteger res = bList.get(i).multiply(aList.get(i).pow(secretKey.modInverse(new BigInteger("1")).intValue())).mod(p);
            System.out.println(res);
            System.out.println("+=+=+=+=+=+");
        }


//        BigInteger d = crmodp.modInverse(p);
//        BigInteger ad = d.multiply(EC).mod(p);
//        System.out.println("Дешифрованное сообщение: " + ad);

    }
}
