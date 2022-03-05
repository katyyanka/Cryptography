package SixthLW;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Decode {

    static final String ENCODEDIMAGE = "src/SixthLW/encoded.jpeg";

    public static String b_msg = "";
    public static int len = 0;

    public static void main(String[] args) throws Exception {

        BufferedImage yImage = getEncodedImage(ENCODEDIMAGE);

        DecodeTheMessage(yImage);
        String msg = "";
        for(int i = 0; i < len * 8; i = i + 8){

            String sub = b_msg.substring(i, i + 8);

            int m = Integer.parseInt(sub,2);
            char ch = (char) m;
            System.out.println("Char number was in image: " + m + " - " + ch);
            msg += ch;
        }

        System.out.println("Result message was in image: " + msg);
    }

    public static BufferedImage getEncodedImage(String COVERIMAGEFILE){
        BufferedImage theImage = null;
        File p = new File (COVERIMAGEFILE);
        try{
            theImage = ImageIO.read(p);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return theImage;
    }

    public static void DecodeTheMessage(BufferedImage yImage){

        int currentBitEntry = 0;
        String bx_msg = "";
        for (int x = 0; x < yImage.getWidth(); x++){
            for (int y = 0; y < yImage.getHeight(); y++){
                if(x == 0 && y < 8){
                    int currentPixel = yImage.getRGB(x, y);
//                    int red = currentPixel>>16;
////                    red = red & 255;
//                    int green = currentPixel>>8;
////                    green = green & 255;
//                    blue = blue & 255;
                    String x_s = Integer.toBinaryString(currentPixel);
                    bx_msg += x_s.charAt(x_s.length() - 1);
                    len = Integer.parseInt(bx_msg,2);

                }
                else if(currentBitEntry < len * 8){

                    int currentPixel = yImage.getRGB(x, y);
//                    int red = currentPixel>>16;
//                    red = red & 255;
//                    int green = currentPixel>>8;
//                    green = green & 255;
//                    int blue = currentPixel;
//                    blue = blue & 255;
                    String x_s = Integer.toBinaryString(currentPixel);
                    b_msg += x_s.charAt(x_s.length() - 1);
                    currentBitEntry++;
                }
            }
        }
        System.out.println("In image was encoded binary: " + b_msg);
    }
}
