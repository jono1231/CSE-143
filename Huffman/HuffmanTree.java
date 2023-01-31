
/*
 * Jonathan Wang
 * CSE 143 AJ
 * 8th December 2022
 * A HuffmanTree class compresses a txt file
 * using Huffman's character frequency 
 * compression algorithm
 */
import java.util.*;

import java.io.*;

public class HuffmanTree {

    public final static int END_OF_FILE = 256; // Int storing end of file
    private HuffmanNode root; // Root node to the Huffman tree

    public HuffmanTree(int[] counts) {
        Queue<HuffmanNode> nodes = new PriorityQueue<>();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0) {
                HuffmanNode tooAdd = new HuffmanNode(counts[i], i);
                nodes.add(tooAdd);
            }
        }
        nodes.add(new HuffmanNode(1, END_OF_FILE));

        while (nodes.size() > 1) {
            HuffmanNode left = nodes.remove();
            HuffmanNode right = nodes.remove();
            HuffmanNode add = new HuffmanNode(left, right);
            nodes.add(add);

        }

        root = nodes.remove();
    }

    /*
     * Constructor:
     * Takes a constructor and reconstructs
     * the tree from an input file.
     */
    public HuffmanTree(Scanner input) {
        HuffmanNode sentinal = new HuffmanNode(0, 0);
        while (input.hasNextLine()) {
            int charCode = Integer.parseInt(input.nextLine());
            sentinal = placeNode(sentinal, charCode, input.nextLine());
        }
        root = sentinal;
    }

    /*
     * Helper method for the overloaded constructor above
     */
    private HuffmanNode placeNode(HuffmanNode curNode, int code, String path) {
        if (path.isEmpty()) {
            curNode = new HuffmanNode(1, code);
        } else {
            char ch = path.charAt(0);
            String newPath = path.substring(1);

            if (curNode == null) {
                curNode = new HuffmanNode(0, 0);
            }

            if (ch == '0') {
                curNode.zero = placeNode(curNode.zero, code, newPath);
            } else {
                curNode.one = placeNode(curNode.one, code, newPath);
            }
        }
        return curNode;
    }

    /*
     * Post: outputs the current HuffmanTree
     * into a output .code file, recording where
     * the code is in the tree and what the character
     * stored there is
     */
    public void write(PrintStream output) {
        writeHelper(root, output, "");
    }

    private void writeHelper(HuffmanNode curNode, PrintStream output, String code) {
        if (curNode.data != 0) {
            output.println(curNode.data + "\n" + code);
        } else {
            writeHelper(curNode.zero, output, code + "0");
            writeHelper(curNode.one, output, code + "1");
        }
    }

    /*
     * Post: takes a BitInputStream with the encrypted file,
     * a output PrintStream, and an int representing an
     * end of file character and decrypts the file using
     * the current HuffmanTree, outputting results to the
     * PrintStream.
     */
    public void decode(BitInputStream input, PrintStream output, int eof) {
        boolean going = true;
        while (going) {
            HuffmanNode curNode = root;
            while (curNode.data == 0) {
                int nextBit = input.readBit();
                if (nextBit == 1) {
                    curNode = curNode.one;
                } else {
                    curNode = curNode.zero;
                }
            }

            if (eof == curNode.data) {
                going = false;
            } else {
                output.write(curNode.data);
            }
        }
    }
}