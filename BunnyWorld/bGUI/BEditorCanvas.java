package bGUI;
import java.awt.*;
import java.awt.event.MouseEvent;

import data.*;

/**
 * 
 */

/**
 * @author Matthew Chun-Lum
 *
 */
public class BEditorCanvas extends BCanvas {
    public static final int EDITOR_WIDTH = 500;
    public static final int EDITOR_HEIGHT = 400;
    
    private Point anchor;
    private BEditor editor;
    /**
     * Constructor
     */
    public BEditorCanvas(BEditor editor) {
        super(EDITOR_WIDTH, EDITOR_HEIGHT);
        this.editor = editor;
        // for now 
//        currentPage = new BPage();
//        currentPage.setName(BEditor.pageCounter.getPageName());
        //this.editor.addPage();
    }
    
    public void paintComponent(Graphics g) {
        if(currentPage != null)
            setBackground(Color.white);
        else
            setBackground(Color.gray);
            
        super.paintComponent(g);
        
        if(currentPage != null)
            currentPage.draw(g, selected, true);
    }
    
    public BPage getCurrentPage() {
    	return currentPage;
    }
    
    public void setCurrentPage(BPage page) {
    	currentPage = page;
    	repaint();
    }
    
    public void setDirty(boolean dirty) {
    	super.setDirty(dirty);
    	if(dirty) editor.enableSave();
    	else editor.disableSave();
    }
    
    // -------------- Protected --------------- //

    /* (non-Javadoc)
     * @see BCanvas#handleMousePressedEvent(java.awt.event.MouseEvent)
     */
    @Override
    protected void handleMousePressedEvent(MouseEvent e) {
        Point pt = e.getPoint();
        
        // must update the last point
        setLastPoint(pt);
        
        if(currentPage != null) {
            
            // clear the previous anchor
            anchor = null;
            
            // First check if we have a selected shape, if so
            // check for a knob-select
            if(selected != null)
                anchor = selected.getAnchorForPoint(pt);
            
            // if there is no knob selected, check if the point
            // falls on a shape
            if(anchor == null)
                selected = currentPage.getShapeForEditor(pt);
                
            editor.selected(selected);
            
            repaint();
        }
            
    }
    
    /* (non-Javadoc)
     * @see BCanvas#handleMouseDraggedEvent(java.awt.event.MouseEvent)
     * 
     */
    @Override
    protected void handleMouseDraggedEvent(MouseEvent e) {
        if(selected != null) {
            Rectangle oldBounds = selected.getBigBounds();
            if(anchor != null) {
                // Handle resize action
                selected.resize(anchor, e.getPoint());
            } else {
                // move the selected shape and update the last point
                selected.move(getDeltaPoint(e.getPoint()));
                setLastPoint(e.getPoint());
            }
            editor.selected(selected);
            repaint(oldBounds);
            repaint(selected.getBigBounds());  
            setDirty(true);
        }
    }

	@Override
	protected void handleMouseReleasedEvent(MouseEvent e) {}

	@Override
	protected void handleMouseClickedEvent(MouseEvent e) {
		// TODO Auto-generated method stub
		
	};

}
