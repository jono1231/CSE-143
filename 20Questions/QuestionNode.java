/*
 * Jonathan Wang
 * CSE 143 AJ
 * November 24, 2022
 * A QuestionNode class is used to store
 * questions and their answers.
 */

public class QuestionNode {
    public QuestionNode yes; // Stores answer if answer is yes (left)
    public QuestionNode no; // Stores answer if answer is no (right)
    public String data; // The data being stored (can be question or answer)
    public boolean isAnswer; // Whether the node stores a question or answer.

    /*
     * Constuctor: Takes some data and if it has a left or right and is a answer
     * and constructs a QuestionNode
     */
    public QuestionNode(String str, boolean isA, QuestionNode left, QuestionNode right) {
        data = str;
        yes = left;
        isAnswer = isA;
        no = right;
    }

    /*
     * Constructor:
     * Takes a String answer and creates a new QuestionNode with the answer
     */
    public QuestionNode(String answer) {
        this(answer, true, null, null);
    }
}