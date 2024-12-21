/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 7
 * An AnagramSolver produces phrase anagrams of a provided word. For example, 
 * the words bee, go, and shrug combine to form a phrase anagram of 
 * "George Bush." This program produces all possible combinations of words in 
 * the provided dictionary that form a phrase anagram.
 */

import java.util.*;

public class AnagramSolver {

    private final Map<String, LetterInventory> inventories;
    private final List<String> dictionary;

    /**
     * Sets up the dictionary and map of letter inventories for the program
     * to use.
     * 
     * @param list The dictionary of words used to form the anagram phrase.
     */
    public AnagramSolver(List<String> list) {
        inventories = new HashMap<>();
        dictionary = list;
        
        // Create a LetterInventory for each word, making them key-value pairs.
        for (String word : dictionary) {
            inventories.put(word, new LetterInventory(word));
        }
    }

    /**
     * Prints out all possible phrase anagrams of the provided string. The 
     * amount of words used to form the anagrams are limited by the provided 
     * integer, max. Throws an IllegalArgumentException if max is less than 0.
     * 
     * @param s   The word to form anagrams of.
     * @param max The maximum number of words to be included in a phrase 
     *            anagram of the provided string, s.
     */
    public void print(String s, int max) {
        // Check if max is valid.
        if (max < 0) {
            throw new IllegalArgumentException();
        }

        // Set no max if max is 0.
        if (max == 0) max = Integer.MAX_VALUE;

        // Reduced size data structures to increase efficiency.
        Map<String, LetterInventory> reducedInventories = new HashMap<>();
        List<String> reducedDictionary = new ArrayList<>();
        
        // Create an inventory of letters for the word.
        LetterInventory word = new LetterInventory(s);

        // Reduce working list of words to relevant words.
        for (String w : dictionary) {
            if (word.subtract(inventories.get(w)) != null) {
                reducedInventories.put(w, inventories.get(w));
                reducedDictionary.add(w);
            }
        }

        // Recursively print all possible phrase anagrams.
        printRecursively(word, reducedInventories, reducedDictionary, max, new Stack<>());
    }

    /**
     * Recursively generates anagram phrases for a provided word, which is 
     * represented as a LetterInventory.
     * 
     * <p> The parameters in this method change with each recursive call as a 
     * part of the "choosing" and "unchoosing" recursive process (words are 
     * added and removed to see if they can be used to form the anagram). 
     * Therefore, the parameter inventory will not always be the original 
     * inventory of words, and the stack of results may not always contain 
     * words that combine to form an anagram.
     * 
     * @param inventory The word to generate anagrams of, represented as a 
     *                  LetterInventory.
     * @param results   The words that combine to form an anagram of the 
     *                  provided word.
     */
    private void printRecursively(LetterInventory inventory,
                                  Map<String, LetterInventory> reducedInventories,
                                  List<String> reducedDictionary,
                                  int max,
                                  Stack<String> results) {

        for (String word : reducedDictionary) {

            // Check if the stack has space for more words.
            if (results.size() < max) {
                // Subtract the letters of the current dictionary word from the
                // current inventory.
                LetterInventory remaining = inventory.subtract(reducedInventories.get(word));

                // The inventory is empty, phrase anagram found.
                if (remaining != null && remaining.isEmpty()) {
                    results.push(word);
                    System.out.println(results);
                    results.remove(word);
                }

                // A word fits in the remaining inventory.
                else if (remaining != null) {

                    // Choose
                    results.push(word);

                    // Explore
                    printRecursively(remaining, reducedInventories, reducedDictionary, max, 
                                     results);

                    // Unchoose
                    results.remove(word);
                    inventory.add(reducedInventories.get(word));
                }
            }
        }
    }
}
