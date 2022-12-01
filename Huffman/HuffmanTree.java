
/*
 * Jonathan Wang
 * CSE 143 AJ
 * A HuffmanTree class compresses a txt file
 * using the Huffman character frequency 
 * compression algorithm
 */
import java.util.*;
import java.io.*;

public class HuffmanTree {

    public class HuffmanNode implements Comparable<HuffmanNode> {
        HuffmanNode zero; // Zero (left) node
        HuffmanNode one; // One (right) node

        int occurrences; // Character frequency
        int data; // Character stored in the node

        /*
         * Constructor: Takes an int occurrences and
         * a character data and creates a new HuffmanNode,
         * storing the data and occurences
         */
        public HuffmanNode(int occ, int d) {
            occurrences = occ;
            data = d;

        }

        /*
         * Constructor Override:
         * Takes 2 nodes l and r and creates a new huffman Node,
         * adding the total number of occurrences and summing up the character data.
         */
        public HuffmanNode(HuffmanNode l, HuffmanNode r) {
            this(l.occurrences + r.occurrences, 0);
            zero = l;
            one = r;
        }

        /*
         * Compare to: Prioritizes number of occurrences
         * (returns the difference between the nodes)
         * otherwise prioritizes character occurrence
         * in the ascii table
         * (returns the difference between the two)
         */
        public int compareTo(HuffmanNode other) {
            int diff = occurrences - other.occurrences;
            return diff;
        }
    }

    private HuffmanNode root; // Root node to the Huffman tree

    public HuffmanTree(int[] counts) {
        Queue<HuffmanNode> nodes = new PriorityQueue<>();
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0) {
                HuffmanNode tooAdd = new HuffmanNode(counts[i], i);
                nodes.add(tooAdd);
            }
        }
        nodes.add(new HuffmanNode(1, 256));

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
     * Post: outputs the current HuffmanTree code
     * into a output .code file with binary tree
     * encoding
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