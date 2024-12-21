/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 2
 * A Giant is a gray critter represented by a changing sequence of onomatopoeia 
 * words that typically moves in a clockwise fashion.
 */

import java.awt.Color;

public class Giant extends Critter {

    // Counter for how long each onomatopoeia word represents the Giant.
    private int moveCount;
    
    // Array containing all the onomatopoeia words.
    private final String[] names = {"", "fee", "fie", "foe", "fum"};
    
    // Tracks the current onomatopoeia word.
    private int index = 1;

    /**
     * Returns the infect action when another critter is detected in front.
     * Typically circles around the frame clockwise otherwise.
     * 
     * @param info The source of information for the critter's information.
     * @return     The action the Giant takes based on the critter's information.
     */
    public Action getMove(CritterInfo info) {
        moveCount++;
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        }
        if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        }
        return Action.RIGHT;
    }

    /**
     * Returns the gray color for the Giant.
     * 
     * @return The designated gray color of the Giant.
     */
    public Color getColor() {
        return Color.GRAY;
    }

    /**
     * Returns an onomatopoeia word within a sequence of onomatopoeia words to 
     * represent the Giant for 6 turns.
     * 
     * @return The onomatopoeia word as the icon.
     */
    public String toString() {
        // Return the current onomatopoeia word if 6 turns has not passed.
        if (moveCount != 6) {
            return names[index];
        }

        // Apply the next onomatopoeia word and reset the counter.
        moveCount = 0;
        index %= 4;
        return names[++index];
    }
}
