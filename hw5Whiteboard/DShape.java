import java.awt.*;
import java.util.*;

/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public abstract class DShape implements ModelListener {
    public static final int KNOB_SIZE = 9;
    public static final Color KNOB_COLOR = Color.black;
    
    public static Rectangle getBigBoundsForModel(DShapeModel model) {
        Rectangle bounds = model.getBounds();
        return new Rectangle(bounds.x - KNOB_SIZE/2, bounds.y - KNOB_SIZE/2, bounds.width + KNOB_SIZE, bounds.height + KNOB_SIZE);
    }
    
    protected DShapeModel model;
    protected Canvas canvas;
    protected Rectangle lastBounds;
    
    protected ArrayList<Point> knobs;
    
    protected boolean needsRecomputeKnobs;
    
    public DShape(DShapeModel model, Canvas canvas) {
        this.model = model;
        this.canvas = canvas;
        lastBounds = new Rectangle(getBounds());
        knobs = null;
        needsRecomputeKnobs = false;
        model.addListener(this);
    }
    
    public void move(int dx, int dy) {
        needsRecomputeKnobs = true;
        model.move(dx, dy);
    }
    
    /**
     * Give the ID of the model
     * @return
     */
    public int getModelID() {
        return model.getID();
    }
    
    /**
     * @return the bounds of the shape
     */
    public Rectangle getBounds() {
        return model.getBounds();
    }
    
    /**
     * @return the bounds including knobs
     */
    public Rectangle getBigBounds() {
        return getBigBoundsForModel(model);
    }
    
    /**
     * @return the bounds of the last position including knobs
     */
    public Rectangle getBigBoundsOfLastPosition() {
        return new Rectangle(lastBounds.x - KNOB_SIZE/2, lastBounds.y - KNOB_SIZE/2, lastBounds.width + KNOB_SIZE, lastBounds.height + KNOB_SIZE);
    }
    
    public void modifyShapeWithPoints(Point anchorPoint, Point movingPoint) {
        needsRecomputeKnobs = true;
        model.modifyWithPoints(anchorPoint, movingPoint);
    }
    
    /**
     * Tests if the shape contains the given point
     * @param pt
     * @return true if the shape contains the given point
     */
    public boolean containsPoint(Point pt) {
        Rectangle bounds = getBounds();
        
        if(bounds.contains(pt))
            return true;
        
        // Handle conditions where the shape has width or height 0
        if(bounds.width == 0
           && Math.abs(pt.x - bounds.x) <= 3
           && pt.y <= bounds.y + bounds.height
           && pt.y >= bounds.y)
            return true;
        
        if(bounds.height == 0
           && Math.abs(pt.y - bounds.y) <= 3
           && pt.x >= bounds.x
           && pt.x <= bounds.x + bounds.width)
            return true;
        
        return false;
    }
    
    /**
     * gets the color of the underlying model
     * @return
     */
    public Color getColor() {
        return model.getColor();
    }
    
    /**
     * Sets the color of the shape's model
     * @param color
     */
    public void setColor(Color color) {
        model.setColor(color);
    }
    
    /**
     * Should override if the shape is only described by two knobs
     * @return an ArrayList of points representing the "knobs"
     */
    public ArrayList<Point> getKnobs() {
        if(knobs == null || needsRecomputeKnobs) {
            knobs = new ArrayList<Point>();
            Rectangle bounds = model.getBounds();
            for(int i = 0; i < 2; i++)
                for(int j = 0; j < 2; j++)
                    knobs.add(new Point(bounds.x + bounds.width * i, bounds.y + bounds.height * j));
            
            // Very 4-point specific, orders the knobs to make it easier to compute an anchor
            Point temp = knobs.remove(2);
            knobs.add(temp);
            
            needsRecomputeKnobs = false;
        }
        return knobs;
    }
    
    /**
     * Determines if a click falls on a knob
     * @param click
     * @param knobCenter
     * @return
     */
    public boolean selectedKnob(Point click, Point knobCenter) {
        Rectangle knob = new Rectangle(knobCenter.x - KNOB_SIZE/2, knobCenter.y - KNOB_SIZE/2, KNOB_SIZE, KNOB_SIZE);
        return knob.contains(click);
    }
    
    /**
     * Computes the anchor point for a given knob
     * @param pt
     * @return a copy of the point for the anchor knob
     */
    public Point getAnchorForSelectedKnob(Point pt) {
        int index = getKnobs().indexOf(pt);
        return new Point(knobs.get((index + knobs.size()/2) % knobs.size()));
    }
    
    /**
     * If the model has changed, update the canvas
     */
    public void modelChanged(DShapeModel model) {
        if(this.model == model) {
            if(model.markedForRemoval()) {
                canvas.removeShape(this);
                //this.model = null;
                return;
            }
            canvas.repaintShape(this);
            if(!lastBounds.equals(getBounds())) {
                canvas.repaintArea(getBigBoundsOfLastPosition());
                lastBounds = new Rectangle(getBounds());
            }
        }
    }
    
    /**
     * Instruct the shape to tell its model that its marked for removal
     */
    public void markForRemoval() {
        model.markForRemoval();
    }
    
    // ------------- Protected Methods -------------- //
    
    protected void drawKnobs(Graphics g) {
        g.setColor(KNOB_COLOR);
        for(Point point : getKnobs())
            g.fillRect(point.x - KNOB_SIZE/2, point.y - KNOB_SIZE/2, KNOB_SIZE, KNOB_SIZE);
    }
    
    // ------------ Abstract Methods -------------- //
    
    abstract public DShapeModel getModel();
    
    abstract public void draw(Graphics g, boolean selected);
}
