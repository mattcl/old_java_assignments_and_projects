import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class Canvas extends JPanel implements ModelListener{
    public static final int SIZE = 400;
    public static final int INITIAL_SHAPE_POS = 10;
    public static final int INITIAL_SHAPE_SIZE = 20;
    
    private ArrayList<DShape> shapes;
    private DShape selected;
    private Point movingPoint;
    private Point anchorPoint;
    
    private int lastX, lastY;
    
    private Whiteboard whiteboard;
    
    public Canvas(Whiteboard bard) {
        setMinimumSize(new Dimension(SIZE, SIZE));
        setPreferredSize(getMinimumSize());
        setBackground(Color.white);
        
        whiteboard = bard;
        
        shapes = new ArrayList<DShape>();
        selected = null;
        movingPoint = null;
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(whiteboard.isNotClient())
                    selectObjectForClick(e.getPoint());
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
           public void mouseDragged(MouseEvent e) {
               if(whiteboard.isNotClient()) {
                   int dx = e.getX() - lastX;
                   int dy = e.getY() - lastY;
                   lastX = e.getX();
                   lastY = e.getY();
                   
                   if(movingPoint != null) {
                       movingPoint.x += dx;
                       movingPoint.y += dy;
                       selected.modifyShapeWithPoints(anchorPoint, movingPoint);
                   } else if(selected != null) {
                       selected.move(dx, dy);
                   }
               }
           }
        });
    }
    
    /**
     * Adds a shape to the canvas
     * @param shape the shape to remove
     */
    public void addShape(DShapeModel model) {
        if(whiteboard.isNotClient())
            model.setID(Whiteboard.getNextIDNumber());
        
        // repaint the area where the last shape was
        if(selected != null) 
            repaintShape(selected);
        
        DShape shape = null;
        if(model instanceof DRectModel)
            shape = new DRect(model, this);
        else if(model instanceof DOvalModel)
            shape = new DOval(model, this);
        else if(model instanceof DLineModel)
            shape = new DLine(model, this);
        else if(model instanceof DTextModel) {
            shape = new DText(model, this);
            DText textShape = (DText) shape;
            whiteboard.updateFontGroup(textShape.getText(), textShape.getFontName());
        }
        model.addListener(this);
        shapes.add(shape);
        whiteboard.addToTable(shape);
        if(whiteboard.isNotClient()) 
            selected = shape;
        
        if(whiteboard.isServer())
            whiteboard.doSend(Whiteboard.Message.ADD, model);
        
        repaintShape(shape);
    }
    
    /**
     * Returns a pointer to the shapes list
     * @return a pointer to the shapes list
     */
    public ArrayList<DShape> getShapes() {
        return shapes;
    }
    
    /**
     * Returns the shape with the matching ID
     * @param ID
     * @return
     */
    public DShape getShapeWithID(int ID) {
        for(DShape shape : shapes)
            if(shape.getModelID() == ID)
                return shape;
        return null;
    }
    
    /**
     * Returns an array list of all the shape models of the shapes on the canvas
     * @return
     */
    public ArrayList<DShapeModel> getShapeModels() {
        ArrayList<DShapeModel> models = new ArrayList<DShapeModel>();
        for(DShape shape : shapes)
            models.add(shape.getModel());
        return models;
    }
    
    /**
     * Marks an object for removal
     */
    public void markSelectedShapeForRemoval() {
        markForRemoval(selected);
        selected = null;
    }
    
    /**
     * Marks the passed shape for removal
     */
    public void markForRemoval(DShape shape) {
        shape.getModel().removeListener(this);
        shape.markForRemoval();
    }
    
    public void markAllForRemoval() {
        selected = null;
        for(int i = shapes.size() - 1; i >= 0; i--)
            markForRemoval(shapes.get(i));
    }
    
    /**
     * removes all of the shapes from the canvas
     */
    public void clearCanvas() {
        shapes.clear();
        selected = null;
        whiteboard.clearTable();
        repaint();
    }
    
    /**
     * Removes a specific shape from the canvas, should never be called by whiteboard
     * @param shape the shape to remove
     */
    public void removeShape(DShape shape) {
        shapes.remove(shape);
        whiteboard.didRemove(shape);
        if(whiteboard.isServer())
            whiteboard.doSend(Whiteboard.Message.REMOVE, shape.getModel());
        repaintArea(shape.getBigBounds());
    }
    
    /**
     * Moves the selected shape to the front
     */
    public void moveSelectedToFront() {
        moveToFront(selected);
    }
    
    /**
     * moves the specified shape to the front
     * @param shape
     */
    public void moveToFront(DShape shape) {
        if(!shapes.isEmpty() && shapes.remove(shape))
            shapes.add(shape);
        whiteboard.didMoveToFront(shape);
        if(whiteboard.isServer())
            whiteboard.doSend(Whiteboard.Message.FRONT, shape.getModel());
        repaintShape(shape);
    }
    
    /**
     * Moves the selected shape to the back
     */
    public void moveSelectedToBack() {
        moveToBack(selected);
    }
    
    /**
     * Moves the specified shape to the back
     * @param shape
     */
    public void moveToBack(DShape shape) {
        if(!shapes.isEmpty() && shapes.remove(shape))
            shapes.add(0, shape);
        whiteboard.didMoveToBack(shape);
        if(whiteboard.isServer())
            whiteboard.doSend(Whiteboard.Message.BACK, shape.getModel());
        repaintShape(shape);
    }
    
    /**
     * Sets the text for the selected object
     * @param text
     */
    public void setTextForSelected(String text) {
        if(selected instanceof DText)
            ((DText) selected).setText(text);
    }
    
    /**
     * Sets the font name for the selected shape
     * @param fontName
     */
    public void setFontForSelected(String fontName) {
        if(selected instanceof DText)
            ((DText) selected).setFontName(fontName);
    }
    
    /**
     * Selects the object that contains the given point if it exists.
     * If no object was selected, the selected pointer is set to null
     * @param pt
     */
    public void selectObjectForClick(Point pt) {
        lastX = pt.x;
        lastY = pt.y;
        movingPoint = null;
        anchorPoint = null;
        
        // If we already have an object selected, test for a knob selection
        if(selected != null) {
            for(Point point : selected.getKnobs())
                if(selected.selectedKnob(pt, point)) {
                    movingPoint = new Point(point);
                    anchorPoint = selected.getAnchorForSelectedKnob(point);
                    break;
                }
        }
        
        // If we have no knob selected (or we have not selected object) check
        // if the user is clicking on a valid object
        if(movingPoint == null) {
            selected = null;
            for(DShape shape : shapes)
                if(shape.containsPoint(pt))
                    selected = shape;
        }
        
        // handle selection of a text shape
        if(selected != null && selected instanceof DText) {
            DText textShape = (DText) selected;
            whiteboard.updateFontGroup(textShape.getText(), textShape.getFontName());
            
        } else {
            // clear the textArea and reset the font chooser to the default font
            whiteboard.updateFontGroup("", DTextModel.DEFAULT_FONT);
        }
        
        // If we are in server mode, let the table know that we have selected
        // a different object
        if(selected != null && whiteboard.isServer()) {
            whiteboard.doSend(Whiteboard.Message.CHANGE, selected.getModel());
        }
        
        whiteboard.updateTableSelection(selected);
        
        repaint();
    }
    
    /**
     * @return true if the canvas has a selected shape
     */
    public boolean hasSelected() {
        return selected != null;
    }
    
    public DShape getSelected() {
        return selected;
    }
    
    /**
     * Sets the color for the selected shape
     * @param color
     */
    public void setSelectedColor(Color color) {
        selected.setColor(color);
    }
    
    /**
     * repaints the passed shape
     */
    public void repaintShape(DShape shape) {
        if(shape == selected)
            repaint(shape.getBigBounds());
        else
            repaint(shape.getBounds());
    }
    
    /**
     * repaints an area specified by the passed bounds
     * @param bounds
     */
    public void repaintArea(Rectangle bounds) {
        repaint(bounds);
    }
    
    /**
     * Saves the canvas to the passed file
     * @param file
     */
    public void saveCanvas(File file) {
        // From Handout 32
        try {
            XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
            
            DShapeModel[] shapeModels = getShapeModels().toArray(new DShapeModel[0]);
            
            xmlOut.writeObject(shapeModels);
            
            xmlOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a canvas from the passed file
     * @param file
     */
    public void openCanvas(File file) {
        markAllForRemoval();
        // From Handout 32
        try {
            XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
            
            DShapeModel[] shapeModels = (DShapeModel[]) xmlIn.readObject();
            
            xmlIn.close();
            
            for(DShapeModel shapeModel : shapeModels) {
                addShape(shapeModel);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Saves a PNG representation to the passed file
     * @param file
     */
    public void saveImage(File file) {
        // Don't draw knobs
        DShape wasSelected = selected;
        selected = null;
        // From Handout 32
        BufferedImage image = (BufferedImage) createImage(getWidth(), getHeight());
        
        Graphics g = image.getGraphics();
        paintAll(g);
        g.dispose();
        
        try {
            javax.imageio.ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        selected = wasSelected;
    }
    
    /**
     * Paints the canvas
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(DShape shape : shapes)
            shape.draw(g, (selected == shape));
    }

    /* (non-Javadoc)
     * @see ModelListener#modelChanged(DShapeModel)
     */
    public void modelChanged(DShapeModel model) {
        if(whiteboard.isServer() && !model.markedForRemoval()) 
            whiteboard.doSend(Whiteboard.Message.CHANGE, model);
    }
}
