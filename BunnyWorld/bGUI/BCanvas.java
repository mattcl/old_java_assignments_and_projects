package bGUI;
import javax.swing.*;

import data.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;

/**
 * 
 */

/**
 * @author Matthew Chun-Lum
 * @author Chidozie Nwobilor
 *
 */
public abstract class BCanvas extends JPanel implements BModelListener{
    
    protected BShape selected;
    protected BPage currentPage;
    
    protected Point lastPoint;
    
    protected boolean dirty;
   
    /**
     * Constructor, adds MouseListeners to the canvas
     */
    public BCanvas(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        selected = null;
        currentPage = null;
        dirty = false;
        
        setBackground(Color.white);
        
        addMouseListener(new MouseAdapter() {
        	public void mouseReleased(MouseEvent e) {
                handleMouseReleasedEvent(e);
            }
            public void mousePressed(MouseEvent e) {
                handleMousePressedEvent(e);
            }
            public void mouseClicked(MouseEvent e){
            	handleMouseClickedEvent(e);
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
           public void mouseDragged(MouseEvent e) {
               handleMouseDraggedEvent(e);
           }
        });
    }

	/**
     * Method to determine if the canvas has a selected shape
     * @return <CODE>true</CODE> if the canvas has a selected shape, <CODE>false</CODE> otherwise
     */
    public boolean hasSelected() {
        return (selected != null);
    }
    
    /**
     * Gets the selected shape, if it exists
     * @return the selected shape, <CODE>null</CODE> if no shape is selected
     */
    public BShape getSelected() {
        return selected;
    }
    
    /**
     * Convenience method to retrieve the selected shape's model
     * directly. 
     * @return the model of the selected shape, <CODE>null</CODE> if there
     * is no selected shape
     */
    public BShapeModel getSelectedModel() {
        if(selected != null) 
            return selected.getModel();
        return null;
    }
    
    /**
     * Gets the current page
     * @return the current page or <CODE>null</CODE> if no page is selected
     */
    public BPage getCurrentPage() {
        return currentPage;
    }
    
    /**
     * Convenience method to retrieve the current page's model
     * directly
     * @return the model of the currentPage, <CODE>null</CODE> if there
     * is no currentPage
     */
    public BPageModel getCurrentPageModel() {
        if(currentPage != null)
            return currentPage.getModel();
        return null;
    }
    
    public void setSelected(BShape shape) {
        if(currentPage != null && (shape == null || currentPage.getShapes().contains(shape))) {
            Rectangle oldShapeBounds = null;
            if(selected != null) {
                oldShapeBounds = selected.getBigBounds();
            }
            
            selected = shape;
            
            if(oldShapeBounds != null) {
                repaint(oldShapeBounds);
            	setDirty(true);
            }
            
            if(selected != null) {
                repaint(shape.getBigBounds());
                setDirty(true);
            }
        }
    }
    
    /**
     * @param shape the shape to add
     */
    public void addShape(BShape shape) {
        if(currentPage != null) {
            currentPage.addShape(shape);
            Rectangle oldShapeBounds = null;
            if(selected != null) {
                oldShapeBounds = selected.getBigBounds();
            }
            
            selected = shape;
            
            if(oldShapeBounds != null)
                repaint(oldShapeBounds);
            
            repaint(shape.getBigBounds());
            setDirty(true);
        }
    }
    
    /**
     * @param shape the shape to remove
     */
    public void removeShape(BShape shape) {
        if(currentPage != null) {
            currentPage.removeShape(shape);
            repaint(shape.getBigBounds());
            setDirty(true);
        }
    }
    
    /**
     * @param shape the shape to move to the front
     */
    public void moveToFront(BShape shape) {
        if(currentPage != null) {
            currentPage.moveToFront(shape);
            setDirty(true);
        }
    }
    
    /**
     * @param shape the shape to move to the back
     */
    public void moveToBack(BShape shape) {
        if(currentPage != null) {
            currentPage.moveToBack(shape);
            setDirty(true);
        }
    }
    
    /**
     * Paints the canvas. If the current page is not null,
     * invokes the page's draw method.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        if(currentPage != null)
            setBackground(Color.white);
        else
            setBackground(Color.gray);
            
        super.paintComponent(g);
        
        if(currentPage != null)
            currentPage.draw(g, selected);
    }
    
    // --------------- Protected --------------- //
    
    /**
     * Abstract, Handles a mousePressedEvent
     * 
     * @param e
     */
    protected abstract void handleMousePressedEvent(MouseEvent e);
    /**
     * Abstract, Handles a mouseClickedEvent
     * 
     * @param e
     */
    protected abstract void handleMouseReleasedEvent(MouseEvent e);
    /**
     * Abstract, handles a mouseDraggedEvent
     * 
     * @param e
     */
    protected abstract void handleMouseDraggedEvent(MouseEvent e);
    /**
     * Abstract, handles a mouseClickedEvent
     * 
     * @param e
     */
	protected abstract void handleMouseClickedEvent(MouseEvent e);

    /**
     * Helper method that sets the last point variable
     * 
     * @param pt
     */
    protected void setLastPoint(Point pt) {
        lastPoint = new Point(pt);
    }
    
    /**
     * Helper method that gets a delta point for a given point.
     * Requires that lastPoint has been set
     * 
     * @param pt the point from which to compute delta
     * @return the delta point
     */
    protected Point getDeltaPoint(Point pt) {
        if(lastPoint == null)
            return null;
        
        return new Point(pt.x - lastPoint.x, pt.y - lastPoint.y);
    }
    public void modelChanged(BShapeModel model) {
		repaint();
	}
	/**
	 Accessors for the dirty bit.
	 */
	public boolean getDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
