package jpsam3hklam9.des;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Class to provide a simple user interface to the DES algorithm.
 */
public class DESInterface {
    public static String main(String s) {
        return "paçoca";
    }

    public static String encripht(String s) {

        byte[] text = s.getBytes();
        long key = getKey("12345678");

        long[] blocks = splitInputIntoBlocks(text);
        return runDecipher(blocks, key, 1);

    }

    public static String decript(String s) {
        byte[] text = s.getBytes();
        long key = getKey("12345678");//getKey(reader);
        long IV = getKey("12345678");
        long[] blocks = splitInputIntoBlocks(text);

        return runDecipher(blocks, key, 0);
    }

    public static String decripht(String s) {
        byte[] text = s.getBytes();
        long key = getKey("12345678");

        long[] blocks = splitInputIntoBlocks(text);
        return runDecipher(blocks, key, 0);// coverte pra byte e pra string;
    }

    public static String decripht2(String s) {
        byte[] text = s.getBytes();
        long key = getKey("12345678");

        long[] blocks = splitInputIntoBlocks(text);

        String c = runCipher3(blocks, key, 1);// coverte pra byte e pra string;

        text = c.getBytes();
        key = getKey("12345678");

        blocks = splitInputIntoBlocks(text);



        return runCipher3(blocks, key, 0);// coverte pra byte e pra string;
    }

    private static String runCipher3(long[] blocks, long key,int mode ) {
        ArrayList<String> mensagem = new ArrayList<String>();
        String acm_str = new String();

        DES des = new DES();
        byte[] bytes;
        long[] cipherTexts = new long[blocks.length], plainTexts = new long[blocks.length];

        if(mode==1){

            System.out.println("Input plaintext: ");
            for (long block : blocks) {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                System.out.print(new String(bytes));
            }

            System.out.println("\nEncrypted ciphertext: ");
            for (int i = 0; i < blocks.length; i++) {
                cipherTexts[i] = des.encrypt(blocks[i], key);
            }


            for (long block : cipherTexts)
            {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                String a = new String(bytes);
                mensagem.add(a);

            }

            for(int i = 0; i<mensagem.size(); i++){
                acm_str += mensagem.get(i);
            }
            return acm_str;


        }
        else {

            cipherTexts=blocks;
            System.out.println("\nDecrypted plaintext: ");
            for (int i = 0; i < cipherTexts.length; i++) {
                plainTexts[i] = des.decrypt(cipherTexts[i], key);
            }
            for (long block : plainTexts) {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                String a = new String(bytes);


                mensagem.add(a);

            }

            for (int i = 0; i < mensagem.size(); i++) {
                acm_str += mensagem.get(i);
            }


            acm_str = acm_str.replace("\\u0000\\", "");

            acm_str = acm_str.replace("\\u0000", "");
            acm_str = acm_str.replace("u0000", "");
            acm_str = acm_str.replace("u0000\\", "");
            acm_str = acm_str.replace("\\", "");

            return acm_str;
        }
    }




    private static String runDecipher(long[] blocks, long key, int mode)

    {
        ArrayList<String> mensagem = new ArrayList<String>();
        String acm_str = new String();
        DES des = new DES();
        byte[] bytes;
        long[] cipherTexts = new long[blocks.length],plainTexts = new long[blocks.length];

        if(mode==1){
            System.out.println("Input plaintext: ");
            for (long block : blocks)
            {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                System.out.print(new String(bytes));
            }

            System.out.println("\nEncrypted ciphertext: ");
            for (int i = 0; i < blocks.length; i++)
            {
                cipherTexts[i] = des.encrypt(blocks[i], key);

            }

            for (long block : cipherTexts)
            {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                String a = new String(bytes);
                mensagem.add(a);

            }

            for(int i = 0; i<mensagem.size(); i++){
                acm_str += mensagem.get(i);
            }
            return acm_str;


        }
        else {

            cipherTexts = blocks;
            System.out.println("\nDecrypted plaintext: ");
            for (int i = 0; i < cipherTexts.length; i++) {
                plainTexts[i] = des.decrypt(cipherTexts[i], key);
            }
            for (long block : plainTexts) {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                String a = new String(bytes);
                mensagem.add(a);
                //acm_str = acm_str + a;
            }
            for(int i = 0; i<mensagem.size(); i++){
                acm_str += mensagem.get(i);
            }
            return acm_str;
        }

    }
    private static String runDecipher2(long[] blocks, long key, int mode)

    {
        ArrayList<String> mensagem = new ArrayList<String>();
        String acm_str = new String();
        DES des = new DES();
        byte[] bytes;
        long[] cipherTexts = new long[blocks.length],plainTexts = new long[blocks.length];


            System.out.println("Input plaintext: ");
            for (long block : blocks)
            {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                System.out.print(new String(bytes));
            }

            System.out.println("\nEncrypted ciphertext: ");
            for (int i = 0; i < blocks.length; i++)
            {
                cipherTexts[i] = des.encrypt(blocks[i], key);

            }

            for (long block : cipherTexts)
            {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                String a = new String(bytes);
                mensagem.add(a);

            }

            for(int i = 0; i<mensagem.size(); i++){
                acm_str += mensagem.get(i);
            }


//            byte[] text = acm_str.getBytes();

//            cipherTexts = splitInputIntoBlocks(text);


            System.out.println("\nDecrypted plaintext: ");
            for (int i = 0; i < cipherTexts.length; i++) {
                plainTexts[i] = des.decrypt(cipherTexts[i], key);
            }
            for (long block : plainTexts) {
                bytes = ByteBuffer.allocate(8).putLong(block).array();
                String a = new String(bytes);
                mensagem.add(a);
                //acm_str = acm_str + a;
            }
            for(int i = 0; i<mensagem.size(); i++){
                acm_str += mensagem.get(i);
            }
            return acm_str;


    }





    /**
     * Runs DES in CBC mode on input @param blocks using @param key with appropriate output
     * displayed.
     */
    private static String runCBC(long[] blocks, long key, long IV)
    {
        DES des = new DES();
        byte[] bytes;
        byte[] total=new byte[0];

        String acm_str=new String("string ");
        long[] cipherTexts, plainTexts;

        acm_str=acm_str+"   texto cifrado ";
        cipherTexts = des.CBCEncrypt(blocks, key, IV);

        int c=0;

        for (long block : cipherTexts)
        {

                bytes = ByteBuffer.allocate(8).putLong(block).array();
                if(c==1){
                    total=bytes;

                }
                c=0;

                acm_str=acm_str+bytes.toString();

        }
        acm_str=acm_str+"   texto decifrado ";

        plainTexts = des.CBCDecrypt(cipherTexts, key, IV);

        String a= new String();
        for (long block : plainTexts)
        {
            bytes = ByteBuffer.allocate(8).putLong(block).array();
            acm_str=acm_str+bytes.toString();
        }




        return total.toString();

    }


    private static String decript(long[] cipherTexts, long key, long IV)
    {
        DES des = new DES();
        byte[] bytes;
        String acm_str=new String();
        long[]  plainTexts;


        plainTexts = des.CBCDecrypt(cipherTexts, key, IV);

        for (long block : plainTexts)
        {
            bytes = ByteBuffer.allocate(8).putLong(block).array();
            acm_str=acm_str+bytes.toString();

        }
        return acm_str;

    }

    /**
     * Runs standard DES mode encryption and decryption on @param blocks using @param key with
     * appropriate output displayed.
     */

    /**
     * Splits the input bytes into blocks of 64 bits.
     *
     * @param input The input text as a byte array.
     * @return An array of longs, representing the 64 bit blocks.
     */
    private static long[] splitInputIntoBlocks(byte[] input)
    {
        long blocks[] = new long[input.length / 8 + 1];

        for (int i = 0, j = -1; i < input.length; i++)
        {
            if (i % 8 == 0)
                j++;
            blocks[j] <<= 8;
            blocks[j] |= input[i];
        }

        return blocks;
    }

    /**
     * Gets a file path to the input file from @param reader.
     *
     * @return The contents of the file in byte array form.
     */
    private static byte[] getText(BufferedReader reader)
    {
        String path = "";
        try
        {
            path = reader.readLine();
        } catch (IOException e)
        {
            printErrorAndDie("");
        }

        return getByteArrayFromFile(path);
    }


    /**
     * @param filePath Path to file containing input text.
     * @return Byte array representing the text in the file.
     */
    private static byte[] getByteArrayFromFile(String filePath)
    {
        File file = new File(filePath);
        byte[] fileBuff = new byte[(int) file.length()];

        try
        {
            DataInputStream fileStream = new DataInputStream(new FileInputStream(file));
            fileStream.readFully(fileBuff);
            fileStream.close();
        } catch (IOException e)
        {
            printErrorAndDie("Cannot read from file.");
        }

        return fileBuff;
    }

    /**
     * Gets a key from @param reader and formats it correctly.
     *
     * @return A 64 bit key as type long. If the input is greater than 64 bits, it will be
     *         truncated. If less than 64 bits, it will be left-padded with 0s.
     */
    private static long getKey(String stri)
    {
        String keyStr = stri;
        byte[] keyBytes;
        long key64 = 0;

        if (keyStr.length() > 8)
        {
            System.out.println("Input is greater than 64 bits.");
            System.exit(0);
        }

        keyBytes = keyStr.getBytes();

        for (byte keyByte : keyBytes)
        {
            key64 <<= 8;
            key64 |= keyByte;
        }

        return key64;
    }
    private static long getKey(BufferedReader reader)
    {
        String keyStr = "";
        byte[] keyBytes;
        long key64 = 0;

        try
        {
            keyStr = reader.readLine();
        } catch (IOException e)
        {
            printErrorAndDie("");
        }

        if (keyStr.length() > 8)
        {
            System.out.println("Input is greater than 64 bits.");
            System.exit(0);
        }

        keyBytes = keyStr.getBytes();

        for (byte keyByte : keyBytes)
        {
            key64 <<= 8;
            key64 |= keyByte;
        }

        return key64;
    }

    /**
     * Uses @param reader to get user confirmation on CBC mode.
     *
     * @return True if user wants CBC mode, else false.
     */
    private static boolean getCBCConfirmation(BufferedReader reader)
    {
        int c = 0;
        try
        {
            c = reader.read();
        } catch (IOException e)
        {
            printErrorAndDie("");
        }

        return (Character.toLowerCase(c) == 'y');
    }

    private static void printErrorAndDie(String message)
    {
        System.err.println("Fatal IO error encountered." + "\n" + message);
        System.exit(1);
    }
    private static void runCipher(long[] blocks, long key)
    {
        DES des = new DES();
        byte[] bytes;
        long[] cipherTexts = new long[blocks.length], plainTexts = new long[blocks.length];

        System.out.println("Input plaintext: ");
        for (long block : blocks)
        {
            bytes = ByteBuffer.allocate(8).putLong(block).array();
            System.out.print(new String(bytes));
        }

        System.out.println("\nEncrypted ciphertext: ");
        for (int i = 0; i < blocks.length; i++)
        {
            cipherTexts[i] = des.encrypt(blocks[i], key);
        }

        for (long block : cipherTexts)
        {
            bytes = ByteBuffer.allocate(8).putLong(block).array();
            System.out.print(new String(bytes));
        }

        System.out.println("\nDecrypted plaintext: ");
        for (int i = 0; i < cipherTexts.length; i++)
        {
            plainTexts[i] = des.decrypt(cipherTexts[i], key);
        }
        for (long block : plainTexts)
        {
            bytes = ByteBuffer.allocate(8).putLong(block).array();
            System.out.print(new String(bytes));
        }
    }

}