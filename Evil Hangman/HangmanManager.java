/*
 * Jonathan Wang
 * CSE 143 AJ
 * 27 October 2022
 * A Hangman Manager creates a game of hangman where 
 * the game tries to make the player lose
 */

import java.util.*;

public class HangmanManager {
    private String curPattern; // The current pattern for the words.
    private Set<String> words; // The current list of words in the game.
    private Set<Character> chars; // The current list of characters that have been guessed
    private int guesses; // The number of guesses currently remaining

    /*
     * Constructor:
     * Pre: Length is greater than 1 and max is greater than 0
     * (throws IllegalArgumentException otherwise)
     * Post: Initializes a HangmanManager, putting in it
     * all words with the specified length and giving the user the specified
     * number of guesses
     */
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        if (length < 1 || max <= 0) {
            throw new IllegalArgumentException(
                    "Cannot init HangmanManager with length " + length + " and guesses " + max);
        }
        initFields(length, max);
        for (String word : dictionary) {
            if (word.length() == length) {
                words.add(word);
            }
        }
    }

    /*
     * Pre: Remaining Guesses is at least 1 and
     * set of words is nonempty (Throws IllegalStateException otherwise)
     * Character has not been guessed (Throws IllegalArgumentException otherwise)
     * Post: Guesses a character, creating a pattern based on the number of words
     * appearing then returns the number of
     * times that the character appears in the resulting pattern.
     */
    public int record(char guess) {
        if (guessesLeft() < 1) {
            throw new IllegalStateException("No guesses remaining!");
        }
        if (words().isEmpty()) {
            throw new IllegalStateException("No words left to guess!");
        }
        if (chars.contains(guess)) {
            throw new IllegalArgumentException("Character has already been guessed!");
        }

        Map<String, Set<String>> patterns = getPatternsMap(guess);
        String pattern = getLargestPattern(patterns);

        words = patterns.get(pattern);
        chars.add(guess);
        curPattern = pattern;

        return getCharOccurrences(pattern, guess);
    }

    /*
     * Post: gets the current set of words
     * considered in the HangmanManager
     */
    public Set<String> words() {
        return words;
    }

    /*
     * Post: Returns the number of wrong guesses the player can still make
     */
    public int guessesLeft() {
        return guesses;
    }

    /*
     * Post: returns the set of characters already guessed
     */
    public Set<Character> guesses() {
        return chars;
    }

    /*
     * Pre: The set of current words cannot be empty (throws IllegalStateException)
     * Post: Returns the current pattern of the guessed characters
     */
    public String pattern() {
        if (words().isEmpty()) {
            throw new IllegalStateException("No words to match pattern!");
        }
        return curPattern;
    }

    /*
     * Post: Initializes all fields, creating a curPattern according to length
     * and giving the user guesses corresponding to max.
     */
    private void initFields(int length, int max) {
        chars = new TreeSet<>();
        words = new TreeSet<>();
        guesses = max;
        curPattern = "-";

        for (int i = 0; i < length - 1; i++) {
            curPattern += " -";
        }
    }

    /*
     * Post: returns a Map mapping all the possible patterns to
     * all words with that specific pattern
     */
    private Map<String, Set<String>> getPatternsMap(char guess) {
        Map<String, Set<String>> ret = new TreeMap<>();

        for (String s : words) {
            String pattern = getPattern(s, guess);
            if (!ret.containsKey(pattern)) {
                ret.put(pattern, new TreeSet<String>());
            }
            ret.get(pattern).add(s);
        }

        return ret;
    }

    /*
     * Post: takes a character ch and word word and creates a pattern
     * that corresponds to that character
     * , merging it with the current pattern
     */
    private String getPattern(String s, char guess) {
        int patternIndex = 0;
        String pattern = "";

        for (int i = 0; i < s.length() - 1; i++) {
            char curChar = s.charAt(i);
            if (curChar == guess) {
                pattern += guess + " ";
            } else if (curPattern.charAt(patternIndex) != '-') {
                pattern += curPattern.charAt(patternIndex) + " ";
            } else {
                pattern += "- ";
            }
            patternIndex += 2;
        }

        if (s.charAt(s.length() - 1) == guess) {
            pattern += guess + " ";
        } else if (curPattern.charAt(patternIndex) != '-') {
            pattern += curPattern.charAt(patternIndex);
        } else {
            pattern += "-";
        }

        return pattern;
    }

    /*
     * Post: returns the number of times the character appears in the String
     * Updates guesses if the number of character occurrences is 0
     */
    private int getCharOccurrences(String str, char ch) {
        int ret = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                ret++;
            }
        }

        if (ret == 0) {
            guesses--;
        }

        return ret;
    }

    /*
     * Post: Returns the pattern string with the most occurrences in patterns.
     */
    private String getLargestPattern(Map<String, Set<String>> patterns) {
        int max = 0;
        String pattern = "";

        for (String p : patterns.keySet()) {
            int size = patterns.get(p).size();
            if (size > max) {
                max = size;
                pattern = p;
            }
        }

        return pattern;
    }
}
