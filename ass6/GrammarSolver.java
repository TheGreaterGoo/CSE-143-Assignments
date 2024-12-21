/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 6
 * A GrammarSolver randomly produces a series of terminals based on the rules 
 * of various nonterminals represented in Backus-Naur form. This program is
 * broadly applicable - not only does it work to represent gramatically 
 * correct randomized sentences containing various nonterminals representing
 * the various elements of a sentence - it can work for any input represented
 * in Backus-Naur form.
 */

import java.util.*;

public class GrammarSolver {

    // A SortedMap containing nonterminals as keys and rules as values.
    private final SortedMap<String, String> grammar = new TreeMap<>();

    // Random object for generating random numbers.
    private final Random r;

    /**
     * Reads a List containing lines of input as entries in Backus-Naur form
     * and splits the entries into Strings of nonterminals and rules to be
     * contained as keys and values in the SortedMap grammar. Throws an
     * IllegalArgumentException if the input List is empty or if there are
     * duplicate nonterminals within the List.
     * 
     * @param grammar The List containing entries in Backus-Naur form.
     */
    public GrammarSolver(List<String> grammar) {
        // Throw exception if List is empty.
        if (grammar.isEmpty()) {
            throw new IllegalArgumentException();
        }

        r = new Random();

        for (String g : grammar) {
            // Detect duplicates and throw exception.
            if (this.grammar.get(g) != null) {
                throw new IllegalArgumentException();
            }

            // Split nonterminal and rules and add them as a key-value pair.
            String[] parts = g.split("::=");
            this.grammar.put(parts[0], parts[1]);
        }
    }

    /**
     * Returns a boolean indicating if a specified nonterminal is a key within
     * the SortedMap grammar.
     * 
     * @param symbol The nonterminal to be searched for in grammar.
     * @return       A boolean indicating if symbol is a key in grammar.
     */
    public boolean grammarContains(String symbol) {
        return grammar.containsKey(symbol);
    }

    /**
     * Returns a random sequence of terminals based on the specified
     * nonterminal. Throws an IllegalArgumentException if the symbol is not a
     * nonterminal within the SortedMap grammar or if an invalid number of
     * times to randomly produce the random sequence is provided.
     * 
     * @param symbol The nonterminal that the random sequence of terminals is 
     *               based on.
     * @param times  The number of times the sequence based on the nonterminal
     *               symbol should be randomly generated.
     * @return       The randomly generated sequence of terminals.
     */
    public String[] generate(String symbol, int times) {
        // Throw exception if the nonterminal is not in the SortedMap grammar
        // or if times is less than 0.
        if (grammar.get(symbol) == null || times < 0) {
            throw new IllegalArgumentException();
        }

        // String array containing each randomly generated sequence.
        String[] results = new String[times];

        // Call helper method to generate each sequence.
        for (int i = 0; i < times; i++) {
            results[i] = recursivelyGenerate(grammar.get(symbol), "").trim();
        }
        return results;
    }

    /**
     * Helper method for generate that recursively generates each random
     * sequence of terminals and returns it.
     * 
     * <p> The recursive aspect of this method is that nonterminals may
     * contain other nonterminals, so before the next rule may be generated in
     * in the original set of rules, the current nonterminal must be generated
     * to completion.
     * 
     * @param symbol The current section of the rules, which could be a set of 
     *               rules, one rule or just a terminal.
     * @param output The working sequence, which is updated with each recursive
     *               call.
     * @return       The randomly generated sequence.
     */
    private String recursivelyGenerate(String symbol, String output) {
        // Split the rule(s) into each choice.
        String[] nonterminals = symbol.split("[|]");

        // Reassign the String array to the set of rules or rule of a randomly
        // chosen section from the previous array of choices.
        nonterminals = nonterminals[r.nextInt(nonterminals.length)].trim().split("[ \\t]+");

        for (String nonterminal : nonterminals) {
            // Terminal is a nonterminal
            if (grammarContains(nonterminal)) {
                output = recursivelyGenerate(grammar.get(nonterminal), output);

            // Nonterminal is a word
            } else {
                output += nonterminal.trim() + " ";
            }
        }
        return output;
    }

    /**
     * Return the set of nonterminals currently available to pick from for 
     * generation. 
     * 
     * @return The set of nonterminals in the SortedMap grammar.
     */
    public String getSymbols() {
        return grammar.keySet().toString();
    }
}