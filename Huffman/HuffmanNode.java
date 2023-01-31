/*
 * Jonathan Wang
 * CSE 143 AJ
 * 8th December 2022
 * A HuffmanNode stores the data of a huffman
 * character frequency tree
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    HuffmanNode zero; // Zero (left) node
    HuffmanNode one; // One (right) node
    int occurrences; // Character frequency
    int data; // Character stored in the node

    /*
     * Constructor:
     * General constructor for HuffmanNode
     */
    public HuffmanNode(int occ, int d, HuffmanNode l, HuffmanNode r) {
        occurrences = occ;
        data = d;
        zero = l;
        one = r;
    }

    /*
     * Constructor: Takes an int occurrences and
     * a character data and creates a new HuffmanNode,
     * storing the data and occurences
     */
    public HuffmanNode(int occ, int d) {
        this(occ, d, null, null);
    }

    /*
     * Constructor:
     * Takes 2 nodes l and r and creates a new huffman Node,
     * adding the total number of occurrences and summing up the character data.
     */
    public HuffmanNode(HuffmanNode l, HuffmanNode r) {
        this(l.occurrences + r.occurrences, 0, l, r);
    }

    /*
     * Takes a HuffmanNode other and
     * returns the difference between
     * this node's occurrences value and the
     * other node's occurrences value.
     */
    public int compareTo(HuffmanNode other) {
        int diff = occurrences - other.occurrences;
        return diff;
    }
}