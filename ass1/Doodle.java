/* Ethan Schroeder
 * CSE 143 XA with Ido Avnon
 * Homework 1
 * A Doodle draws a yellow smiley face on a grey gridded
 * background.
 */

import java.awt.*;

public class Doodle {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(400, 400);
        Graphics g = panel.getGraphics();
        panel.setBackground(Color.LIGHT_GRAY);
        
        // Draws the vertical lines of the grid background.
        g.setColor(Color.WHITE);
        for (int i = 0; i < 10; i++) {
            g.drawLine(0, 40 * i + 20, 400, 40 * i + 20);
        }

        // Draws the horizontal lines of the grid background.
        for (int i = 0; i < 10; i++) {
            g.drawLine(40 * i + 20, 0, 40 * i + 20, 400);
        }

        // Draws the outside circle of the face.
        g.setColor(Color.BLACK);
        g.fillOval(50, 50, 300, 300);
        g.setColor(Color.YELLOW);
        g.fillOval(55, 55, 290, 290);

        // Draws the mouth.
        g.setColor(Color.BLACK);
        g.fillOval(75, 75, 250, 250);
        g.setColor(Color.RED);
        g.fillOval(80, 80, 240, 240);
        g.setColor(Color.YELLOW);

        int[] xPoints = {60, 340, 300, 200, 100};
        int[] yPoints = {210, 210, 100, 65, 100};
        g.fillPolygon(xPoints, yPoints, 5);

        g.setColor(Color.BLACK);
        g.fillRect(80, 210, 240, 5);

        // Draws the left eye.
        g.setColor(Color.BLACK);
        g.fillOval(105, 125, 75, 75);
        g.setColor(Color.YELLOW);
        g.fillOval(105, 145, 75, 60);

        // Draws the right eye.
        g.setColor(Color.BLACK);
        g.fillOval(220, 125, 75, 75);
        g.setColor(Color.YELLOW);
        g.fillOval(220, 145, 75, 60);
    }
}