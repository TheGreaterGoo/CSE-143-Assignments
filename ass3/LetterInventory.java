/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 3
 * A LetterInventory holds an inventory of quantities of letters, and represents
 * them in an alphabetically sorted format.
 */

public class LetterInventory {
    // Contains a count for the frequency of every letter in the alphabet 
    // contained within the original string.
    private final int[] letters;

    // The amount of total letters within the inventory of letters.
    private int size;

    private static final int ALPHABET_LENGTH = 26;

    public static void main(String[] args) {
        LetterInventory l1 = new LetterInventory("skibbity2343hi");
        LetterInventory l2 = new LetterInventory("zzxcvbn3*");

        System.out.println(l1.get('b')); // 2
        l1.set('j', 25); 
        System.out.println(l1.size()); // 35
        System.out.println(l2.isEmpty()); // false

        LetterInventory l3 = l1.add(l2);
        LetterInventory l4 = l2.subtract(new LetterInventory("z"));
        System.out.println(l1.toString());
        System.out.println(l2.toString());
        System.out.println(l3.toString());
        System.out.println(l4.toString());
    }

    /**
     * Constructs a LetterInventory with an inventory of letters 
     * containing the frequency of each letter of the alphabet contained 
     * within the string parameter.
     * 
     * @param data The string to be converted into an inventory of letters.
     */
    public LetterInventory(String data) {
        // Create counters for the 26 letters of the alphabet.
        letters = new int[ALPHABET_LENGTH];

        data = data.toLowerCase();

        // Add to each counter based off the contents of data.
        for (int i = 0; i < data.length(); i++) {
            if (Character.isLetter(data.charAt(i))) {
                letters[charToInt(data.charAt(i))]++;
                size++;
            }
        }
    }

    /**
     * Returns the frequency of a letter within the inventory of letters.
     * 
     * @param letter The letter of the alphabet.
     * @return       The frequency of the letter.
     */
    public int get(char letter) {
        letter = Character.toLowerCase(letter);

        // Check for valid input.
        checkForAlphabeticInput(letter);

        return letters[charToInt(letter)];
    }

    /**
     * Sets the frequency of a letter to a new value.
     * 
     * @param letter The letter of the alphabet.
     * @param value  The new frequency of the letter.
     */
    public void set(char letter, int value) {
        letter = Character.toLowerCase(letter);

        // Check for valid input.
        checkForAlphabeticInput(letter);
        if (value < 0) {
            throw new IllegalArgumentException();
        }

        size -= letters[charToInt(letter)];
        letters[charToInt(letter)] = value;
        size += value;
    }

    /**
     * Returns how many letters total are contained within the inventory of 
     * letters.
     * 
     * @return The total amount of letters.
     */
    public int size() {
        return size;
    }

    /**
     * Returns an indicator of if the inventory of letters is empty or not.
     * 
     * @return The indicator.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Converts the inventory of letters into an alphabetically sorted string 
     * representation and returns it.
     * 
     * @return The string representation.
     */
    public String toString() {
        String res = "[";
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            for (int j = 0; j < letters[i]; j++) {
                res += intToChar(i);
            }
        }
        return res + "]";
    }

    /**
     * Returns a new LetterInventory containing the combined inventory of 
     * letters of this LetterInventory and another one.
     * 
     * @param other The other LetterInventory containing the inventory of 
     * letters to be added.
     * @return      A new LetterInventory object with the combined inventory 
     * of letters.
     */
    public LetterInventory add(LetterInventory other) {
        LetterInventory l = new LetterInventory("");

        // Add the letter counts together and put the result in a new
        // LetterInventory object.
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            l.set(intToChar(i), get(intToChar(i)) + other.get(intToChar(i)));
        }

        return l;
    }

    /**
     * Returns a new LetterInventory containing the inventory of letters 
     * of this LetterInventory minus another one.
     * 
     * @param other The other LetterInventory containing the inventory of 
     * letters to be subtracted.
     * @return      A new LetterInventory object with the subtracted inventory 
     * of letters of another inventory, or null if any letter counts would be 
     * negative.
     */
    public LetterInventory subtract(LetterInventory other) {
        LetterInventory l = new LetterInventory("");

        // Subtract the letter counts of other from this letter and put the 
        // result in a new LetterInventory object.
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            int diff = get(intToChar(i)) - other.get(intToChar(i));

            // If subtraction would result in a negative number, return null.
            if (diff < 0) {
                return null;
            }

            l.set(intToChar(i), diff);
        }

        return l;
    }

    /**
     * Throws an exception if the character is not a letter in the alphabet.
     * 
     * @param letter The character to be tested.
     */
    private void checkForAlphabeticInput(char letter) {
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Converts a character to an integer.
     * 
     * <p> This is used for determining where the letter falls within 
     * the order of the alphabet in a 0 based index format.
     * 
     * @param c The character to be converted.
     * @return  The typecasted integer.
     */
    private static int charToInt(char c) {
        return (int) (c - 'a');

    }

    /**
     * Converts an integer to a character.
     * 
     * <p> This is used for converting letters represented in a 0 based index 
     * system to their alphabetic letter counterparts.
     * 
     * @param letter
     * @return
     */
    private char intToChar(int letter) {
        return (char) ('a' + letter);
    }
}