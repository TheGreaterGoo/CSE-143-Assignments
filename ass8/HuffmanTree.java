/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 8
 * A HuffmanTree is a tree of HuffmanNodes that stores the nodes in an order
 * such that the letters with the greatest frequency have the shortest path 
 * from the overall root node. This technique is used for file compression,
 * as the most frequently used letters are represented with the smallest
 * possible byte code. The idea of letters being represented by bytes is
 * inherent in the tree structure, given that going left in a tree represents a
 * 0 and going right in a tree represents a 1.
 */

import java.io.*;
import java.util.*;

public class HuffmanTree {

    private final HuffmanNode overallRoot;

    /**
     * Constructs a HuffmanTree based on a code file that stores a HuffmanTree 
     * in standard format - the file contains the letters (represented as 
     * integers) and byte equivalents line-by-line.
     * 
     * @param input The scanner for the code file.
     */
    public HuffmanTree(Scanner input) {
        overallRoot = new HuffmanNode(0);
        while (input.hasNextLine()) {
            int n = Integer.parseInt(input.nextLine());
            String code = input.nextLine();
            
            // Call recursive helper method.
            buildTree(overallRoot, code, n);
        }
    }

    /**
     * Recursively explores and builds the HuffmanTree given a path to the
     * leaf node to be built.
     * 
     * <p> Building the HuffmanTree refers to creating new HuffmanNodes, which 
     * could include branch nodes and/or leaf nodes. The branch nodes are only 
     * built if they did not previously exist - subsequent calls to the same 
     * location do not rebuild the node, instead they explore through it.
     * 
     * @param root The current sub-tree root being explored, starting with the 
     *             overall root.
     * @param code The path through the tree to be built for the new leaf node,
     *             represented by a sequence of 0s and 1s, 0 being left and 1
     *             being right.
     * @param n    The letter corresponding to the byte code.
     */
    private void buildTree(HuffmanNode root, String code, int n) {
        // Base case, build the leaf node.
        if (code.length() == 1) {
            if (code.charAt(0) == '0') {
                root.left = new HuffmanNode(0, n);
            } else { // code.charAt(i) == 1
                root.right = new HuffmanNode(0, n);
            }

        // Recursive case, explore and/or build the branch nodes.
        } else {
            if (code.charAt(0) == '0') {
                // Build a new branch node if it doesn't exist.
                if (root.left == null) {
                    root.left = new HuffmanNode(0);
                }
                buildTree(root.left, code.substring(1), n);
            } else { // code.charAt(i) == 1
                // Build a new branch node if it doesn't exist.
                if (root.right == null) {
                    root.right = new HuffmanNode(0);
                }
                buildTree(root.right, code.substring(1), n);
            }
        }
    }

    /**
     * Constructs a HuffmanTree based on an array containing the frequencies of 
     * each character, with each character being represented by the index of 
     * the frequency in the array. An end-of-file (eof) character is added to 
     * the tree with letter value 1 greater than the last letter in the array.
     * 
     * @param count The array containing the frequencies of each character.
     */
    public HuffmanTree(int[] count) {
        Queue<HuffmanNode> priorityQueue = new PriorityQueue<>();

        // Add letters as HuffmanNodes to the priority queue.
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                priorityQueue.add(new HuffmanNode(count[i], i));
            }
        }

        // Add the EOF character with letter value 1 higher than the last index
        // of count.
        priorityQueue.add(new HuffmanNode(1, count.length));

        // Build the tree from the bottom up until only the overall root 
        // remains.
        while (priorityQueue.size() != 1) {
            // Remove the nodes with the lowest frequencies.
            HuffmanNode left = priorityQueue.remove();
            HuffmanNode right = priorityQueue.remove();

            // Create the sub tree root with the combined frequencies.
            int total = left.frequency + right.frequency;
            HuffmanNode subTreeRoot = new HuffmanNode(total);
            subTreeRoot.left = left;
            subTreeRoot.right = right;

            // Add this node back to the priority queue.
            priorityQueue.add(subTreeRoot);
        }

        // Set the overall root.
        overallRoot = priorityQueue.remove();
    }

    /**
     * Writes the leaf nodes of a HuffmanTree to a PrintStream in the standard
     * format, each leaf node being represented by two consecutive lines.
     * The first line is the letter and the second line is the code of 0s and 
     * 1s that represents the path through the tree to get to the node.
     * 
     * @param output The PrintStream to be printed to.
     */
    public void write(PrintStream output) {
        // Call recursive helper method.
        traversal(overallRoot, "", output);
    }

    /**
     * Recursively explores a built HuffmanTree and prints each leaf node from
     * left to right. 
     * 
     * @param root   The current sub-tree root being explored, starting with the 
     *               overall root.
     * @param code   The path to the leaf node that is recursively built.   
     * @param output The PrintStream to be printed to.
     */
    private void traversal(HuffmanNode root, String code, PrintStream output) {
        // Base case, print the letter and code of the leaf node.
        if (root.left == null && root.right == null) {
            output.println(root.letter);
            output.println(code);
        
        // Recursive case, explore the tree.
        } else {
            if (root.left != null) {
                traversal(root.left, code + "0", output);
            }
            if (root.right != null) {
                traversal(root.right, code + "1", output);
            }
        }
    }

    /**
     * Decodes a stream of binary digits and prints the corresponding letters 
     * until the end-of-file (eof) character is reached.
     * 
     * @param input  The stream of binary digits to be decoded.
     * @param output The PrintStream to be printed to.
     * @param eof    The end of file character letter value.
     */
    public void decode(BitInputStream input, PrintStream output, int eof) {
        HuffmanNode root = overallRoot;
        while (root.letter != eof) {
            // Restart from the overall root.
            root = overallRoot;

            // Explore tree until at a leaf node.
            while (root.left != null && root.right != null) {
                int bit = input.readBit();
                if (bit == 0) {
                    root = root.left;
                } else { // bit == 1
                    root = root.right;
                }
            }

            // Write the letter to the PrintStream.
            if (root.letter != eof) {
                output.write(root.letter);
            }
        }
    }
}