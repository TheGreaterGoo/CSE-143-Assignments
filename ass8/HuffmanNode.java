/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 8
 * A HuffmanNode is a unit of data typically stored within a HuffmanTree that
 * represents a letter and how frequently it appears in some body of text.
 */

public class HuffmanNode implements Comparable<HuffmanNode> {
    public HuffmanNode left;
    public HuffmanNode right;
    public int frequency;
    public int letter;

    /**
     * Constructs a HuffmanNode with provided frequency, leaving the letter
     * value as the null default.
     * 
     * @param frequency A value held by branch nodes representing the combined
     *                  frequency of all nodes below, or a dummy value.
     */
    public HuffmanNode(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Constructs a HuffmanNode with provided frequency and letter.
     * 
     * @param frequency The frequency of the letter.
     * @param letter    The letter value.
     */
    public HuffmanNode(int frequency, int letter) {
        this.frequency = frequency;
        this.letter = letter;
    }

    /**
     * Returns a value corresponding to whether this HuffmanNode is "greater," 
     * "lesser," or "equal" to another HuffmanNode. This is based on which
     * HuffmanNode has the greater frequency value.
     * 
     * @return An integer greater than, less than, or equal to zero.
     */
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
}
