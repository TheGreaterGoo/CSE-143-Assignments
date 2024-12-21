/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 1
 * A CafeWall draws rows and grids of black and white squares in various
 * ways, such as standalone rows, grids with no even row pixel offsets,
 * and grids with even row pixel offsets that produce the Cafe Wall illusion.
 */

import java.awt.*;

public class CafeWall {

    // Row pixel separation constant for the grid.
    private static final int MORTAR = 2;

    public static void main(String[] args) {

        // 650x400 DrawingPanel and Graphics objects.
        DrawingPanel panel = new DrawingPanel(650, 400);
        Graphics g = panel.getGraphics();

        // Set background color to gray.
        panel.setBackground(Color.GRAY);

        // Draw rows and grids.
        drawRow(g, 20, 4, 0, 0);
        drawRow(g, 30, 5, 50, 70);
        drawGrid(g, 25, 4, 10, 150, 0);
        drawGrid(g, 25, 3, 250, 200, 10);
        drawGrid(g, 20, 5, 425, 180, 10);
        drawGrid(g, 35, 2, 400, 20, 35);
    }

    /**
     * Draws a specified amount of black and white squares in a row, at a
     * given location and square size using a global graphics object.
     * 
     * <p> The squares alternate in color, each pair starting with black and
     * ending with white. Each black square has a blue X drawn inside it.
     *
     * @param squareSize The size (length and width) of each square to be drawn
     * @param pairs      The amount of black and white square pairs to be drawn
     * @param x          The x-coordinate of the top left pixel of the first square
     * @param y          The y-coordinate of the top left pixel of the first square
     * 
     */
    public static void drawRow(Graphics g, int squareSize, int pairs, int x, int y) {
        for (int i = 0; i < pairs; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, squareSize, squareSize);

            // Draw the blue X in the black squares. 
            g.setColor(Color.BLUE);
            g.drawLine(x, y, x + squareSize, y + squareSize);
            g.drawLine(x, y + squareSize, x + squareSize, y);

            g.setColor(Color.WHITE);
            g.fillRect(x + squareSize, y, squareSize, squareSize);
            // Adjust x to draw the next pair of squares.
            x += squareSize * 2;
        }
    }

    /**
     * Draws a specified amount of black and white square rows to form a
     * grid at a given location and square size using a global graphics object.
     *
     * @param squareSize The size (length and width) of each square to be drawn
     * @param pairs      The amount of black and white square pairs to be drawn
     * @param x          The x-coordinate of the top left pixel of the first square
     * @param y          The y-coordinate of the top left pixel of the first square
     * @param offset     The rightward pixel offset of each even row of the grid
     * 
     */
    public static void drawGrid(Graphics g, int squareSize, int pairs, int x, int y, int offset) {
        for (int i = 0; i < pairs; i++) {
            // Draw the odd row.
            drawRow(g, squareSize, pairs, x, y + squareSize * i * 2);
            // Draw the even row with offset.
            drawRow(g, squareSize, pairs, x + offset, y + squareSize * (i * 2 + 1) + MORTAR);
            // Adjust y to account for MORTAR.
            y += MORTAR * 2;
        }
    }
}