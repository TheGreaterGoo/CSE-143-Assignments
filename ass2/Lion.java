/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 2
 * A Lion is a color-changing critter represented by an L
 * that typically moves in a counter-clockwise fashion.
 */

import java.awt.Color;
import java.util.Random;

public class Lion extends Critter {

    private final Random r;

    // Counter for how long a lion stays one color.
    private int turns;
    private Color color;

    // Constructs a lion so that the first color is randomly chosen.
    public Lion() {
        r = new Random();
        turns = 2;
    }

    /**
     * Returns the infect action when another critter is detected in front.
     * Moves left and right based on nearby walls and other Lions.
     * 
     * @param info The source of information for the critter's information.
     * @return     The action the Lion takes based on the critter's information.
     */
    public Action getMove(CritterInfo info) {
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        }
        if (info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL) {
            return Action.LEFT;
        }
        if (info.getFront() == Neighbor.SAME) {
            return Action.RIGHT;
        }
        return Action.HOP;
    }

    /**
     * Returns a random color among three options, red, green, and blue. This 
     * color is rechosen every 3 turns.
     * 
     * @return The color that represents the Lion for 3 turns.
     */
    public Color getColor() {
        // If 3 turns has not passed, return the current color.
        if (turns < 2) {
            turns++;
            return color;
        }

        // Choose a new color since 3 turns have passed.
        int n = r.nextInt(3);
        turns = 0;
        if (n == 0) {
            return color = Color.RED;
        }
        if (n == 1) {
            return color = Color.GREEN;
        }
        return color = Color.BLUE;
    }

    /**
     * Returns the "L" icon that represents the Lion.
     * 
     * @return The "L" icon of the Lion.
     */
    public String toString() {
        return "L";
    }
}
