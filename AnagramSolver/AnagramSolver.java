
/*
 * Jonathan Wang
 * CSE 143 AJ
 * An anagram solver takes a dictionary of words
 * and a phrase and prints all the anagrams of that phrase
 */
import java.util.*;

public class AnagramSolver {
    // The dictionary of words and their lettercounts
    Map<String, LetterInventory> dictionary;
    List<String> words; // The list of words in the AnagramSolver
    Stack<String> explore; // The current exploration of the solver

    /*
     * Constructor:
     * Post: Takes a dictionary and constructs a new AnagramSolver,
     * mapping each word to it's letter count
     */
    public AnagramSolver(List<String> list) {
        words = list;
        dictionary = new HashMap<>();
        explore = new Stack<>();
        for (String str : list) {
            LetterInventory newInv = new LetterInventory(str);
            dictionary.put(str, newInv);
        }
    }

    /*
     * Pre: max cannot be less than 0 (throws IllegalArgumentException otherwise)
     * Post: prints out all possible anagrams with the word
     * and containing the at most the same number of words as max.
     * If max is 0, then the word count is unlimited.
     * Ignores case when searching.
     */
    public void print(String s, int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Invalid Max: " + max);
        }
        LetterInventory sInv = new LetterInventory(s);
        printHelper(sInv, max, words);
    }

    /*
     * Post: Prints anagrams if possible
     * TODO: Ask TA about if this violates recursion/boolean zen tomorrow.
     */
    private void printHelper(LetterInventory curInv, int max,
            List<String> curExplored) {
        if (curInv.isEmpty() && (max == 0 || explore.size() <= max)) {
            System.out.println(explore);
        } else if (max == 0 || explore.size() < max) {
            List<String> newExplored = new ArrayList<>();
            Map<String, LetterInventory> map = new HashMap<>();
            for (String s : curExplored) {
                LetterInventory sInv = dictionary.get(s);
                LetterInventory newInv = curInv.subtract(sInv);
                if (newInv != null) {
                    newExplored.add(s);
                    map.put(s, newInv);
                }
            }
            for (String s : newExplored) {
                explore.push(s);
                printHelper(map.get(s), max, newExplored);
                explore.pop();
            }
        }
    }
}