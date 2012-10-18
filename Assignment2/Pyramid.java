/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Pyramid extends GraphicsProgram {

    /** Width of each brick in pixels */
    private static final int BRICK_WIDTH = 30;

    /** Width of each brick in pixels */
    private static final int BRICK_HEIGHT = 12;

    /** Number of bricks in the base of the pyramid */
    private static final int BRICKS_IN_BASE = 25;

    private ArrayList<GRect> rects;
    private ArrayList<ArrayList<GRect>> rectDiagonals;

    public void run() {
        generatePyramid();
        setUpDiagonals();
        animate();

    }

    private void generatePyramid() {
        rects = new ArrayList<GRect>();
        rectDiagonals = new ArrayList<ArrayList<GRect>>();
        for(int i = 0; i < BRICKS_IN_BASE; i++) {
            int yOffset = getHeight() - (i + 1) * BRICK_HEIGHT;
            for(int j = 0; j < BRICKS_IN_BASE - i; j++) {
                int xOffset = (getWidth() - (BRICKS_IN_BASE - i) * BRICK_WIDTH) / 2 + j * BRICK_WIDTH;
                GRect rect = new GRect(xOffset, yOffset, BRICK_WIDTH, BRICK_HEIGHT);
                rect.setFilled(true);
                rects.add(rect);
                add(rect);
            }
        }
    }
    
    private void setUpDiagonals() {
        int initialIncrement = BRICKS_IN_BASE;
        for(int i = 0; i < BRICKS_IN_BASE; i++) {
            ArrayList<GRect> rectDiagonal = new ArrayList<GRect>();
            int index = i;
            int increment = initialIncrement;
            for(int j = 0; j < BRICKS_IN_BASE - i; j++) {
                rectDiagonal.add(rects.get(index));
                index += increment;
                increment --;
            }
            rectDiagonals.add(rectDiagonal);
        }
    }
    
   private void animate() {
       double initialHue = 0;
       int hueStep = 0;
       double hueStepSize = 200.0;
       while(true) {
           shiftColors();
           double hue = hueStep / hueStepSize * 360;
           double[] hsv = {hue, 0.7, 1};
           double[] rgb = HSVToRGB(hsv);
           Color color = new Color((int) rgb[0], (int) rgb[1], (int) rgb[2]);
           for(int j = 0; j < rectDiagonals.get(0).size(); j++) {
               rectDiagonals.get(0).get(j).setFillColor(color);
           }
           hueStep++;
           if(hueStep >= hueStepSize)
               hueStep = 0;

           pause(50);

       }
   }

    private void shiftColors() {
        for(int i = rectDiagonals.size() - 1; i > 0; i--) {
            int diag = i - 1;
            Color color = rectDiagonals.get(diag).get(0).getFillColor();
            for(int j = 0; j < rectDiagonals.get(i).size(); j++) {
                rectDiagonals.get(i).get(j).setFillColor(color);
            }
        }
    }

    private double[] HSVToRGB(double[] hsv) {
        double[] rgb = new double[3];
        double c = hsv[2] * hsv[1];
        double hp = hsv[0] / 60.0;
        double x = c * (1 - Math.abs(hp % 2 - 1));
        if (hp < 0) {
            setRGB(rgb, 0, 0, 0);
        }else if(hp < 1) {
            setRGB(rgb, c, x, 0);
        } else if(hp < 2) {
            setRGB(rgb, x, c, 0);
        } else if(hp < 3) {
            setRGB(rgb, 0, c, x);
        } else if(hp < 4) {
            setRGB(rgb, 0, x, c);
        } else if(hp < 5) {
            setRGB(rgb, x, 0, c);
        } else if(hp < 6) {
            setRGB(rgb, c, 0, x);
        } else {
            setRGB(rgb, 0, 0, 0);
        }

        for(int i = 0; i < rgb.length; i++) {
            rgb[i] += (hsv[2] - c);
            rgb[i] *= 255;
        }
        return rgb;
    }

    private void setRGB(double[] rgb, double r, double g, double b) {
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
    }

}




