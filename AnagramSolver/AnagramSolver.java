/*
 * Jonathan Wang
 * CSE 143 AJ
 * An anagram solver takes a dictionary of words
 * and a phrase and prints all the anagram phrases
 */

import java.util.*;

public class AnagramSolver {
    // The dictionary of words and their lettercounts
    private Map<String, LetterInventory> dictionary;
    private List<String> words; // The words stored in the AnagramSolver
    private List<String> curExploration; // The current exploration of words
    private Stack<String> explore; // The current exploration of the solver

    /*
     * Constructor:
     * Post: Takes a dictionary and constructs a new AnagramSolver,
     * matching up all words with their character counts
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
        } else if (max == 0) {
            max = -1;
        }
        LetterInventory sInv = new LetterInventory(s);
        curExploration = prune(sInv);
        printHelper(sInv, max);
    }

    /*
     * Post: Prints anagrams if possible
     */
    private void printHelper(LetterInventory curInv, int max) {
        if (curInv.isEmpty()) {
            System.out.println(explore);
        } else if (max != 0) {
            for (String s : curExploration) {
                LetterInventory sInv = dictionary.get(s);
                LetterInventory newInv = curInv.subtract(sInv);
                if (newInv != null) {
                    explore.push(s);
                    printHelper(newInv, max - 1);
                    explore.pop();
                }
            }
        }
    }

    /*
     * Post: Takes a word toPrune and prunes all impossible
     * anagrams from the dictionary
     */
    private List<String> prune(LetterInventory toPrune) {
        List<String> ret = new ArrayList<>();
        for (String s : words) {
            LetterInventory dictInv = dictionary.get(s);
            if (toPrune.subtract(dictInv) != null) {
                ret.add(s);
            }
        }
        return ret;
    }
}