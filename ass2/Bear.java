/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 2
 * A Bear is a black or white critter represented by alternating slashes 
 * that typically moves in a counter-clockwise fashion.
 */

import java.awt.Color;

public class Bear extends Critter {

    // The indicator for whether the bear is black or white.
    private final boolean polar;

    // Stores the slash icon associated with the current turn.
    private String icon;

    /**
     * Constructs a bear as either a polar or regular bear.
     * 
     * @param polar Indicates if the bear is a polar bear.
     */
    public Bear(boolean polar) {
        this.polar = polar;
        icon = "\\";
    }

    /**
     * Returns the infect action another critter when it is detected in front.
     * Typically circles around the frame counter-clockwise otherwise.
     * 
     * @param info The source of information for the critter's information.
     * @return     The action the bear takes based on the critter's information.
     */
    public Action getMove(CritterInfo info) {
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        }
        if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        }
        return Action.LEFT;
    }

    /**
     * Returns white or black based on if the bear is polar or not.
     * 
     * @return The designated color of the bear.
     */
    public Color getColor() {
        if (polar) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    /**
     * Returns a slash to represent the bear, which alternates between a 
     * backwards and forwards slash.
     * 
     * @return The slash icon of the bear.
     */
    public String toString() {
        if (icon.equals("/")) {
            return icon = "\\";
        }
        return icon = "/";
    }
}
