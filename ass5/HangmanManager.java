/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 5
 * A HangmanManager creates and maintains information related to a game of 
 * hangman, but not any ordinary game of hangman. This program violates
 * the conventional rules of hangman by only choosing a word when it is
 * forced to. By purposefully choosing a set of words that contains the 
 * highest amount of words matching a specific pattern given the player's 
 * guessed letter, the player is constantly guessing against the odds.
 */

import java.util.*;

public class HangmanManager {

    // The set of words being currently considered by a HangmanManager for the 
    // provided length.
    private Set<String> wordSet;

    // The set of characters that have already been guessed by the player.
    private Set<Character> guessedLetters;

    // The map containing all possible patterns provided the player's guessed 
    // character.
    private Map<String, Set<String>> wordFamily;

    // The amount of incorrect guesses the player has left.
    private int guessesLeft;

    // The current pattern for the round.
    private String currentPattern;

    /**
     * Sets up important information for the program, including the amount of 
     * incorrect guesses the player gets, the length of the word to be guessed, 
     * and the set of words to be considered for the game based on a provided 
     * source. Throws an IllegalArgumentException if the provided length 
     * logically cannot form a word, or if a negative amount of guesses is 
     * provided.
     * 
     * @param dictionary The set of words the program can use throughout the
     *                   hangman game.
     * @param length     The length of the word used in the game.
     * @param max        The maximum number of incorrect guesses the player 
     *                   gets to guess the word.
     */
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        // Throw an IllegalArgumentException if the length is less than 1, 
        // or if the amount of guesses is less than 0.
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }

        // Create the starting pattern, full of dashes.
        currentPattern = "-";
        for (int i = 0; i < length - 1; i++) {
            currentPattern += " -";
        }

        // Set the amount of guesses the player has.
        guessesLeft = max;

        // Initialize the the various data structures to store important 
        // information for the program.
        guessedLetters = new TreeSet<>();
        wordFamily = new TreeMap<>();
        wordSet = new TreeSet<>();

        // Add every word of the provided length from the source of words 
        // to the current set of words being considered by the program.
        for (String word : dictionary) {
            if (word.length() == length) {
                wordSet.add(word.toLowerCase());
            }
        } 
    }

    /**
     * Returns the current set of words being considered by the program.
     * 
     * @return The set of words of the provided length the program can 
     * potentially use.
     */
    public Set<String> words() {
        return wordSet;
    }

    /**
     * Returns the amount of incorrect guesses the player has left.
     * 
     * @return The amount of incorrect characters the player can guess before 
     * the game ends.
     */
    public int guessesLeft() {
        return guessesLeft;
    }

    /**
     * Returns the set of characters the player has already guessed.
     * 
     * @return The set of characters guessed in previous rounds of the hangman 
     * game by the player.
     */
    public Set<Character> guesses() {
        return guessedLetters;
    }

    /**
     * Returns the current pattern for the game of hangman.
     * 
     * <p> The pattern is a representation of the word to be guessed, 
     * containing characters and dashes. Characters represent correctly guessed 
     * letters, and dashes represent undiscovered letters. An example of a 
     * pattern could be "- O O -" in a game of hangman with words of length 4, 
     * where the player has correctly guessed the letter O at the second and 
     * third letters of the word. The letters represented by dashes represent 
     * the undiscovered letters. Throws an IllegalStateException if no words are 
     * in the set of words being considered.
     * 
     * @return The player's pattern of the current round of hangman.
     */
    public String pattern() {
        if (wordSet.isEmpty()) {
            throw new IllegalStateException();
        }

        return currentPattern;
    }

    /**
     * Uses the player's guessed character for the round in an algorithm that 
     * generates the new set of words to be the largest possible set of words. 
     * If all sets are equal in size, the first set is picked.
     * 
     * 
     * @param guess The lowercase letter guessed by the player for the current 
     *              round.
     * @return      The amount of times the guessed letter appears in the newly 
     *              generated pattern for the round.
     */
    public int record(char guess) {
        // Add this letter to the set of guessed letters.
        guessedLetters.add(guess);

        // Generate all possible patterns.
        generatePatterns(guess);
        
        // Find the pattern set with the most words in it.
        String highestLengthPattern = "";
        int size = 0;
        for (String key : wordFamily.keySet()) {
            if (wordFamily.get(highestLengthPattern) == null) {
                highestLengthPattern = key;
            } else if (wordFamily.get(key).size() > size) {
                highestLengthPattern = key;
            }
            size = wordFamily.get(highestLengthPattern).size();
        }
        String largestSet = highestLengthPattern;

        // Set the new set of words and pattern.
        wordSet = wordFamily.get(largestSet);
        currentPattern = largestSet;

        // Clear the map of old patterns for the next round.
        wordFamily.clear();

        // Count how many characters were correctly guessed this round.
        int count = count(currentPattern, guess);

        // Subtract a guess if the guess was incorrect.
        if (count == 0) {
            guessesLeft--;
        }
        return count;
    }

    /**
     * Generates all patterns for the current set of words and puts them into
     * a map, with each key being the pattern and each value being a set
     * containing each word that matches the pattern.
     * 
     * @param guess The character used to form new patterns.
     */
    private void generatePatterns(char guess) {
        // Add each possible pattern to the wordFamily, and the words that 
        // correspond to those patterns.
        for (String word : wordSet) {
            // Get the pattern for each word.
            String pattern = dashes(word, guess);

            // Pattern already exists, add the word to the set.
            if (wordFamily.get(pattern) != null) {
                wordFamily.get(pattern).add(word);

            // Pattern doesn't exist yet, create it and the set.
            } else {
                Set<String> patternSet = new TreeSet<>();
                patternSet.add(word);
                wordFamily.put(pattern, patternSet);
            }
        }
    }

    /**
     * Returns an updated version of the current pattern, replacing any dashes
     * with the provided character, given that the character matches a provided
     * word.
     * 
     * @param word   The word whose pattern is updated using the provided
     *               character.
     * @param letter The new character that is prospectively part of the word.
     * @return       The new updated pattern.
     */
    private String dashes(String word, char letter) {
        String res = "";
        for (int i = 0; i < word.length(); i++) {
            // Insert existing correct letters into the pattern.
            if (Character.isLetter(currentPattern.charAt(i * 2))) {
                res += currentPattern.charAt(i * 2) + " ";

            // Insert the new correctly guessed letter.
            } else if (word.charAt(i) == letter) {
                res += Character.toLowerCase(letter) + " ";

            // Insert a dash for all other unknown letters.
            } else {
                res += "- ";
            }
        }

        return res.substring(0, res.length() - 1);
    }

    /**
     * Returns the number of occurences of a letter within a string.
     * 
     * @param s The pattern containing various guessed and unknown letters. 
     * @param c The character to be counted within s.
     * @return  The number of occurrence of the letter within s.
     */
    private int count(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == Character.toLowerCase(c)) {
                count++;
            }
        }

        return count;
    }
}