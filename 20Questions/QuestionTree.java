
/*
 * Jonathan Wang
 * CSE 143 AJ
 * November 24, 2022
 * A QuestionTree creates and plays a game of 20 questions,
 * getting better at guessing each play
 */
import java.io.*;
import java.util.*;

public class QuestionTree {
    private Scanner console; // The scanner for user input
    private QuestionNode root; // The root questionNode

    /*
     * Constructor:
     * Initializes a new QuestionTree with one answer node
     * representing "computer"
     */
    public QuestionTree() {
        console = new Scanner(System.in);
        root = new QuestionNode("computer");
    }

    /*
     * Takes in a Scanner with a file in it and
     * creates a new questionTree with the file's
     * line if questioning
     */
    public void read(Scanner input) {
        root = readHelper(input);
    }

    /*
     * Helper method for read
     */
    private QuestionNode readHelper(Scanner input) {
        String isAns = input.nextLine().trim();
        if (isAns.equals("A:")) {
            String ans = input.nextLine();
            return new QuestionNode(ans);
        } else {
            String q = input.nextLine();
            QuestionNode left = readHelper(input);
            QuestionNode right = readHelper(input);

            return new QuestionNode(q, false, left, right);
        }
    }

    /*
     * Saves the current line of questioning to an output file
     */
    public void write(PrintStream output) {
        printHelper(root, output);
    }

    /*
     * Helper method for write
     */
    private void printHelper(QuestionNode curNode, PrintStream output) {
        if (curNode.isAnswer) {
            output.println("A:");
            output.println(curNode.data);
        } else {
            output.println("Q:");
            output.println(curNode.data);
            printHelper(curNode.yes, output);
            printHelper(curNode.no, output);
        }
    }

    /*
     * Initializes a game of 20 questions, playing unitl
     * the tree gets an answer and then improves the questioning
     * if the answer is wrong.
     */
    public void askQuestions() {
        root = askQuestionsHelper(root);
    }

    /*
     * Helper method for playing game of 20 quesitons
     */
    private QuestionNode askQuestionsHelper(QuestionNode curNode) {
        if (curNode.isAnswer) {
            return getAnswer(curNode);
        } else {
            String prompt = curNode.data;
            if (yesTo(prompt)) {
                curNode.yes = askQuestionsHelper(curNode.yes);
            } else {
                curNode.no = askQuestionsHelper(curNode.no);
            }
        }
        return curNode;
    }

    /*
     * Checks if an answer is correct and if it isn't,
     * learns.
     */
    private QuestionNode getAnswer(QuestionNode ansNode) {
        String prompt = "Would your object happen to be " + ansNode.data + "?";
        if (yesTo(prompt)) {
            System.out.println("Great, I got it right!");
        } else {
            System.out.print("What is the name of your object? ");
            String obj = console.nextLine();
            QuestionNode newAnswer = new QuestionNode(obj);

            System.out.print(
                    "Please give me a yes or no question"
                            + "\n that distinguishes between your object"
                            + "\n and mine --> ");
            String q = console.nextLine();

            prompt = "And what is the answer for your object?";
            if (yesTo(prompt)) {
                ansNode = new QuestionNode(q, false, newAnswer, ansNode);
            } else {
                ansNode = new QuestionNode(q, false, ansNode, newAnswer);
            }
        }

        return ansNode;
    }

    // post: asks the user a question, forcing an answer of "y" or "n";
    // returns true if the answer was yes, returns false otherwise
    public boolean yesTo(String prompt) {
        System.out.print(prompt + " (y/n)? ");
        String response = console.nextLine().trim().toLowerCase();
        while (!response.equals("y") && !response.equals("n")) {
            System.out.println("Please answer y or n.");
            System.out.print(prompt + " (y/n)? ");
            response = console.nextLine().trim().toLowerCase();
        }
        return response.equals("y");
    }

}
