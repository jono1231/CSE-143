import java.io.*;
import java.util.*;

public class testcode {
    public static void main(String[] args) throws Exception {
        System.out.println("Test code for Huffman Project bit matching.");
        System.out.println();

        String encode1 = "short.short"; // put filepath for first encoded (.short) file
        String encode2 = "test.short"; // put filepath into other file here

        // open encoded file, open output, decode
        BitInputStream input1 = new BitInputStream(encode1);
        BitInputStream input2 = new BitInputStream(encode2);

        int bit = 0;
        int curBit = 1;
        while (bit != -1) {
            bit = input1.readBit();
            int bit2 = input2.readBit();
            if (bit != bit2) {
                throw new Exception("Bits do not match!");
            } else {
                System.out.println("Cur bits: " + bit + " " + bit2);
            }
        }
        if (input2.readBit() != -1) {
            throw new Exception("Input 2 still has bits when input 1 doesnt!");
        }

        System.out.println("All tests passed");
    }
}