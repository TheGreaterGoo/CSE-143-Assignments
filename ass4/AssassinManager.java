/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 4
 * An AssassinManager creates and maintains information related to a game of
 * Assassin, where players assassinate other players, sending them to the
 * graveyard until only one player is left. Note, all names used in 
 * AssassinManager are case-insensitive, so "Friday" and "friday" would be 
 * considered the same name.
 */

import java.util.*;

public class AssassinManager {

    AssassinNode front;
    AssassinNode graveyard;

    /**
     * Creates a linked list of AssassinNodes based on a provided list of names.
     * Throws an IllegalArgumentException if the list of names is empty.
     * 
     * <p> The last player points to the first player logically, but not in the 
     * code. This distinction is accounted for in the rest of the class.
     * 
     * @param names The names of the players of the assassination game.
     */
    public AssassinManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }

        front = new AssassinNode(names.get(0));
        AssassinNode curr = front;
        for (int i = 1; i < names.size(); i++) {
            curr.next = new AssassinNode(names.get(i));
            curr = curr.next;
        }
    }

    /** 
     * Displays the current players still in the kill ring, and the player 
     * they are stalking.
     */
    public void printKillRing() {
        AssassinNode curr = front;
        while (curr.next != null) {
            System.out.println("    " + curr.name + " is stalking " + curr.next.name);
            curr = curr.next;
        } 

        // Account for last AssassinNode
        System.out.println("    " + curr.name + " is stalking " + front.name);
    }

    /**
     * Displays the assassinated players in the graveyard and which player 
     * killed them. Returns null if no players have been killed.
     * 
     * <p> The assassinated players are displayed in the order of which player 
     * was most recently killed.
     */
    public void printGraveyard() {
        if (graveyard != null) {
            AssassinNode curr = graveyard;

            while (curr != null) {
                System.out.println("    " + curr.name + " was killed by " + curr.killer);
                curr = curr.next;
            }
        } 
    }

    /**
     * Returns true or false based on if a player with a provided name is still
     * in the kill ring. 
     * 
     * @param name The player to be searched for in the kill ring.
     * @return     A boolean indicating whether or not the kill ring contains 
     *             the specified player.
     */
    public boolean killRingContains(String name) {
        AssassinNode curr = front;

        // Return null if the kill ring is empty.
        if (curr == null) {
            return false;
        }

        // Return true if the player is found.
        while (curr != null) {
            if (curr.name.toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Returns true or false based on if a player with a provided name is in 
     * the graveyard.
     * 
     * @param name The player to be searched for in the graveyard.
     * @return     A boolean indicating whether or not the graveyard contains 
     *             the specified player.
     */
    public boolean graveyardContains(String name) {
        AssassinNode curr = graveyard;

        // Return null if the graveyard is empty.
        if (curr == null) {
            return false;
        }
        
        // Return true if the player is found.
        while (curr != null) {
            if (curr.name.toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Returns true or false based on if the game is over.
     * 
     * <p> The game is considered over when there is only one player left 
     * in the kill ring.
     * 
     * @return A boolean indicating the state of the game.
     */
    public boolean gameOver() {
        return front.next == null;
    }

    /**
     * Returns the name of the winner of the game. Returns null if the game 
     * is not over.
     * 
     * @return The name of the winner of the game.
     */
    public String winner() {
        if (!gameOver()) {
            return null;
        }
        return front.name;
    }

    /**
     * Assassinates a player in the kill ring, provided the name of the player 
     * to be assassinated. Throws an IllegalArgumentException if the name is not
     * in the kill ring. Throws an IllegalStateException if the game is over.
     * 
     * <p> The player that is assassinated is removed from the kill ring and 
     * becomes part of the graveyard.
     * 
     * @param name The name of the player to be assassinated.
     */
    public void kill(String name) {
        if (gameOver()) {
            throw new IllegalStateException();
        }

        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        }

        // Reference that keeps track of the assassinated player.
        AssassinNode temp = front;

        // Special kill ring removal procedure to account for the front.
        if (front.name.toLowerCase().equals(name.toLowerCase())) {

            // Iterate to last player.
            while (temp.next != null) {
                temp = temp.next;
            }
            
            // Set killer to last player.
            front.killer = temp.name;
            
            // Remove the assassinated player from the kill ring.
            temp = front;
            front = front.next;

        // Standard kill ring removal procedure.
        } else {
            // Iterate until curr.next is the player to be assassinated.
            AssassinNode curr = front;
            while (!curr.next.name.toLowerCase().equals(name.toLowerCase())) {
                curr = curr.next;
            }

            // Set killer.
            curr.next.killer = curr.name;

            // Remove the assassinated player from the kill ring.
            temp = curr.next;
            curr.next = curr.next.next;
        }
        // Add the assassinated player to the graveyard.
        addToGraveyard(temp);
    }

    /**
     * Adds an assassinated player to the graveyard. Throws an 
     * IllegalArgumentException if the AssassinNode player is null.
     * 
     * @param player The player to be added to the front of the graveyard.
     */
    private void addToGraveyard(AssassinNode player) {
        if (player == null) {
            throw new IllegalArgumentException();
        }
        
        // Add the first assassinated player to the graveyard.
        if (graveyard == null) {
            graveyard = player;
            player.next = null;

        // Add the assassinated player to the front of a graveyard with 
        // other assassinated players.
        } else {
            player.next = graveyard;
            graveyard = player;
        }
    }
}