import java.awt.*;

import java.util.*;
/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public abstract class DShapeModel {
    
    protected ArrayList<ModelListener> listeners;
    
    protected Rectangle bounds;
    protected Color color;
    
    protected boolean markedForRemoval;
    
    protected int ID_number;
    
    public DShapeModel() {
        this(0, 0);
    }
    
    public DShapeModel(int x, int y) {
        this(x, y, 0, 0, Color.gray);
    }

    public DShapeModel(int x, int y, int width, int height, Color color) {
        bounds = new Rectangle(x, y, width, height);
        this.color = color;
        listeners = new ArrayList<ModelListener>();
        markedForRemoval = false;
    }
    
    public void mimic(DShapeModel other) {
        setID(other.getID());
        setBounds(other.getBounds());
        setColor(other.getColor());
        notifyListeners();
    }
    
    public int getID() {
        return ID_number;
    }
    
    public void setID(int num) {
        ID_number = num;
    }
    
    /**
     * @return the upper left-hand corner of the model
     */
    public Point getLocation() {
        return bounds.getLocation();
    }
    
    /**
     * Sets the location to the given coordinates
     * @param x
     * @param y
     */
    public void setLocation(int x, int y) {
        bounds.setLocation(x, y);
        notifyListeners();
    }
    
    /**
     * Sets the location to the given point
     * @param pt
     */
    public void setLocation(Point pt) {
        setLocation(pt.x, pt.y);
    }
    
    /**
     * Moves the point by the specified values
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy) {
        bounds.x += dx;
        bounds.y += dy;
        notifyListeners();
    }
    
    public void modifyWithPoints(Point anchorPoint, Point movingPoint) {
        int x = (anchorPoint.x < movingPoint.x ? anchorPoint.x : movingPoint.x);
        int y = (anchorPoint.y < movingPoint.y ? anchorPoint.y : movingPoint.y);
        int width = Math.abs(anchorPoint.x - movingPoint.x);
        int height = Math.abs(anchorPoint.y - movingPoint.y);
        setBounds(new Rectangle(x, y, width, height));
    }
    
    /**
     * 
     * @return the bounds of the shape model
     */
    public Rectangle getBounds() {
        return bounds;
    }
    
    /**
     * Sets the bounds given a set of coordinates and the height and width
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width
     * @param height
     */
    public void setBounds(int x, int y, int width, int height) {
        bounds = new Rectangle(x, y, width, height);
        notifyListeners();
    }
    
    /**
     * Sets the bounds given a point and the height and width
     * @param pt
     * @param width
     * @param height
     */
    public void setBounds(Point pt, int width, int height) {
        bounds = new Rectangle(pt.x, pt.y, width, height);
        notifyListeners();
    }
    
    /**
     * Sets the bounds given a rectangle
     * @param newBounds
     */
    public void setBounds(Rectangle newBounds) {
        bounds = new Rectangle(newBounds);
        notifyListeners();
    }
    
    /**
     * Sets the color
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
    }
    
    /**
     * gets the color
     * @return the color of the model
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Tell the model that it should remove itself
     */
    public void markForRemoval() {
        markedForRemoval = true;
        notifyListeners();
    }
    
    /**
     * 
     * @return true if the model is marked for removal
     */
    public boolean markedForRemoval() {
        return markedForRemoval;
    }
    
    /**
     * Adds the passed listening object to the list of listeners
     * @param listener
     */
    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Removes the passed listening object from the list of listeners
     * @param listener
     * @return true if the listener was removed, false otherwise
     */
    public boolean removeListener(ModelListener listener) {
        return listeners.remove(listener);
    }
    
    public void notifyListeners() {
        for(ModelListener listener : listeners)
            listener.modelChanged(this);
    }
}
