/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 2
 * A Husky is a gray critter shaped as an "H" that groups up with other Huskies in 
 * packs and defends each of its sides unprotected by other Huskies. 
*/

/*
 * ////////////////////////////////////////////////////////////////////////////////////////////////
 * Thought process, experiments, and strategy:
 * 
 * The Husky strategy takes inspiration from the successful FlyTrap design. However, I wanted to
 * build on the concept of turning and infecting in place by grouping up Huskies to protect each 
 * other. By decreasing the amount of exposed sides of the Husky, there is a greater chance the
 * Husky turns to the correct side that an enemy critter could be at.
 * 
 * Experiments:
 * I originally wanted the Huskies to group up along a wall so even more sides could be protected.
 * However, due to the wall-clinging nature of some critters like the Bear, the Huskies would
 * typically get wiped out and fail to form a pack.
 * 
 * The wall experiment showed that it was a stronger strategy to get Huskies to group up in the
 * middle of the frame. This altered the movement strategy as well, which was to originally move
 * and turn at walls, changing to a random sequence resulting in the Husky wandering in the middle 
 * of the frame.
 * ////////////////////////////////////////////////////////////////////////////////////////////////
 */

import java.awt.Color;
import java.util.*;

public class Husky extends Critter {

    private final Random r;

    // List containing the four directions.
    private final List<Direction> directions;

    // Set indicating which adjacent directions contain other Huskies.
    private final Set<Direction> protectedSides;

    // The two-action turnaround sequence indicator.
    private boolean turnAround;

    // Constructs a Husky.
    public Husky() {
        r = new Random();

        directions = new ArrayList<>();
        directions.add(Direction.NORTH);
        directions.add(Direction.EAST);
        directions.add(Direction.SOUTH);
        directions.add(Direction.WEST);

        protectedSides = new HashSet<>();
    }

    /**
     * Returns an action based on the potential interference of other critters, other 
     * Huskies, and walls with the objective of Huskies grouping up and defending each other 
     * for the Husky to follow. Follows a random movement sequence in the absence of the 
     * aforementioned.
     * 
     * @param info The source of information for the critter's information.
     * @return     Returns the action the Husky takes based on the critter's information.
     */
    public Action getMove(CritterInfo info) {
        Direction d = info.getDirection();

        // Infect an enemy in front.
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        }

        // Turn the Husky around as a part of a two-action sequence.
        if (turnAround) {
            turnAround = false;
            return Action.LEFT;
        }
        
        // Define sides of the Husky protected by other Huskies.
        if (info.getFront() == Neighbor.SAME) { 
            protectedSides.add(d); 
        } else {
            protectedSides.remove(d);
        }
        if (info.getBack() == Neighbor.SAME) { 
            protectedSides.add(getSide("back", d));
        } else {
            protectedSides.remove(getSide("back", d));
        }
        if (info.getLeft() == Neighbor.SAME) { 
            protectedSides.add(getSide("left", d));  
        } else {
            protectedSides.remove(getSide("left", d));
        }
        if (info.getRight() == Neighbor.SAME) { 
            protectedSides.add(getSide("right", d)); 
        } else {
            protectedSides.remove(getSide("right", d));
        }

        // Turn to a side that is not protected by another Husky.
        if (!protectedSides.isEmpty()) {
            return turnToUnprotected(info, d); 
        }

        // Initiate a turnaround sequence if a wall is detected.
        if (info.getFront() == Neighbor.WALL) {
            turnAround = true;
            return Action.LEFT;
        }

        // Follow a random action except infect.
        int randomActionNum = r.nextInt(7);
        if (randomActionNum < 5) {
            return Action.HOP;
        } else if (randomActionNum == 5) {
            return Action.LEFT;
        }
        return Action.RIGHT;
    }

    /**
     * Returns a turn action to an unprotected side of the Husky.
     * 
     * <p> An unprotected side is a side where a Husky is not a neighbor. Unprotected 
     * sides are defined during the getMove method and are added to the protectedSides HashSet
     * 
     * @param info The source of information for the critter's information.
     * @param d    The intended direction to turn in.
     * @return     Returns the action that turns to a unprotected side
     */
    public Action turnToUnprotected(CritterInfo info, Direction d) {
        // Turn left or right, because the current direction is protected.
        if (protectedSides.contains(d)) {
            if (protectedSides.contains(getSide("right", d))) {
                return Action.LEFT;
            }
            return Action.RIGHT;
        // Current direction is not protected, switch to another unprotected direction or 
        // choose to stay in place and infect.
        } else {
            if (!protectedSides.contains(getSide("right", d)) && 
                r.nextInt(protectedSides.size() + 1) == 0) {
                    return Action.RIGHT;
                }
            if (!protectedSides.contains(getSide("left", d)) 
                && r.nextInt(protectedSides.size() + 1) == 0) {
                    return Action.LEFT;
                }
        }
        return Action.INFECT;
    }

    /**
     * Returns the direction of the provided side.
     * 
     * <p> A side of the Husky does not indicate the cardinal direction. For example, 
     * the right of a Husky facing south would not be east, it would be west. This method
     * calculates the cardinal direction when provided a orientation change relative to the
     * way the Husky is facing.
     * 
     * @param side The side relative to the Husky's orientation. 
     * @param d    The current direction the Husky is facing.
     * @return     The direction of the side.
     */
    public Direction getSide(String side, Direction d) {
        if (side.equals("back")) { 
            return directions.get((directions.indexOf(d) + 2) % 4);
        }
        if (side.equals("right")) {
            return directions.get((directions.indexOf(d) + 1) % 4);
        }
        return directions.get((directions.indexOf(d) + 3) % 4);
    }

    /**
     * Returns the gray color for the Husky.
     * 
     * @return The designated gray color of the Husky.
     */
    public Color getColor() {
        return Color.GRAY;
    }

    /**
     * Returns the "H" icon that represents the Husky.
     * 
     * @return The "H" icon of the Husky.
     */
    public String toString() {
        return "H";
    }
}