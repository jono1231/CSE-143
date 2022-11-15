/*
 * Jonathan Wang
 * CSE 143 AJ
 * 3 November 2022
 * A grammar solver creates randomly generated sentences
 * based off of predetermined rules
 */

import java.util.*;

public class GrammarSolver {

    private SortedMap<String, List<String>> nonTerminals;
    // Stores all non-terminals and how they get built
    private Random random; // Random number generator

    /*
     * Constructor:
     * Pre: Grammar is nonempty (throws IllegalArgumentException)
     * Grammar symbols do not repeat (throws IllegalArgumentException)
     * Post: Constructs a new GrammarSolver, generating terminals and nonterminals
     * to be created later. Case matters for the list of nonterminals.
     */
    public GrammarSolver(List<String> grammar) {
        if (grammar.isEmpty()) {
            throw new IllegalArgumentException("No valid grammar lines!");
        }

        nonTerminals = new TreeMap<>();
        random = new Random();

        for (String link : grammar) {
            String[] nonTermAndStrings = link.split("::=");
            String nonTerminal = nonTermAndStrings[0];
            List<String> terminals = getTerminals(nonTermAndStrings[1]);

            if (nonTerminals.containsKey(nonTerminal)) {
                throw new IllegalArgumentException("This exists twice: " + nonTerminal);
            }

            nonTerminals.put(nonTerminal, terminals);
        }
    }

    /*
     * Post: checks if the symbol is a nonterminal in the ruleset
     */
    public boolean grammarContains(String symbol) {
        return nonTerminals.containsKey(symbol);
    }

    /*
     * Pre: times cannot be less than zero (Throws IllegalArgumentException)
     * The nonterminal symbol must be in the solver (Throws
     * IllegalArgumentException)
     * Post: Randomly generates a given number of times
     * using the symbol provided.
     */
    public String[] generate(String symbol, int times) {
        if (times < 0) {
            throw new IllegalArgumentException("Times is less than zero!");
        }
        if (!nonTerminals.containsKey(symbol)) {
            throw new IllegalArgumentException(symbol + " does not exist!");
        }

        String[] generated = new String[times];
        for (int i = 0; i < generated.length; i++) {
            generated[i] = generateHelper(symbol);
        }

        return generated;
    }

    /*
     * Post: Returns all the nonterminal symbols currently in the GrammarSolver
     */
    public String getSymbols() {
        return nonTerminals.keySet().toString();
    }

    /*
     * Post: Takes a string of terminals and turns it into a list
     */
    private List<String> getTerminals(String terminals) {
        List<String> terms = new ArrayList<>();
        String[] split = terminals.split("[|]");

        for (String term : split) {
            terms.add(term.trim());
        }

        return terms;
    }

    /*
     * Post: Takes a symbol and randomly generates a valid arrangement for it
     */
    private String generateHelper(String symbol) {
        if (!grammarContains(symbol)) {
            return symbol;
        }

        String ret = "";

        List<String> terminal = nonTerminals.get(symbol);
        int randomVal = random.nextInt(terminal.size());
        String nonterminal = terminal.get(randomVal);

        String[] parts = nonterminal.split("[ \t]+");

        for (String part : parts) {
            ret += generateHelper(part) + " ";
        }

        return ret.trim();
    }
}
