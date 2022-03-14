package FifthLW;

import FourthLW.FNV1A;

import java.io.FileReader;
import java.math.BigInteger;

public class RSA {

    public static void main(String[] args)
    {
        int p, q, r, eiler_function, d, e;
        StringBuilder builder = new StringBuilder();

        try (FileReader reader = new FileReader("src/FourthLW/origin.txt")) {
            while (reader.ready()) {
                builder.append(reader.read());
            }
        } catch (Exception ignore) {}

        long msg = ( new FNV1A().FNV1AHash(builder.toString()));
        System.out.println("m is "+msg);
        BigInteger c;
        BigInteger msgback;

        // 1st step
        p = 3251  ;
        q = 3253  ;
        System.out.println("the value of p = " + p +
                ", q = "+q);
        //3rd step
        //2nd step
        r = p * q;
        System.out.println("the value of r = " + r);
        //3rd step
        eiler_function = (p - 1) * (q - 1);
        System.out.println("the value of eiler_function = " + eiler_function);
        //4th step
        for (e = 2; e < eiler_function; e++) {
            if (grandCentralDispatch(e, eiler_function) == 1) {
                break;
            }
        }
        System.out.println("the value of e = " + e);
        for (d = 0; true ; d++) {
            if ((d*e)%eiler_function==1) {
                break;
            }
        }
        BigInteger msg_bigint = new BigInteger(String.valueOf(msg));
        System.out.println("the value of d = " + d);
        c = (msg_bigint.pow(e)).mod(BigInteger.valueOf(r));
        System.out.println("Encrypted message is : " + c);

        // converting int value of r to BigInteger
        BigInteger N = BigInteger.valueOf(r);

        // converting float value of c to BigInteger
        msgback = (c.pow(d)).mod(N);
        System.out.println("Decrypted HASH is : "
                + msgback);
        builder= new StringBuilder();
        try (FileReader reader = new FileReader("src/FourthLW/origin2.txt")) {
            while (reader.ready()) {
                builder.append(reader.read());
            }
        } catch (Exception ignore) {}
        msg = ( new FNV1A().FNV1AHash(builder.toString()));
        System.out.println("HASH of message is " + msg);
        if (msgback.equals(BigInteger.valueOf(msg))){
            System.out.println("All is OK! ☺");
        } else {
            System.out.println("Something wrong! The message is corrupted or the signature is invalid!");
        }
    }

    //Euclidean algorithm
    static int grandCentralDispatch(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return grandCentralDispatch(z % e, e);
    }
}