package data;
import java.awt.*;
import java.util.List;

import bGUI.BGameCanvas;

/**
 * 
 * @author Junichi Tsutsui
 * @author Chidozie Nwobilor
 *
 */
public class BPage {
	
	private BPageModel pageModel;
	protected boolean drawKnobs;
	
	/**
	 * Default constructor for a page
	 * constructs a page model to store data.
	 */
	public BPage() {
		pageModel = new BPageModel();
		setDrawKnobs(true);
	}
	
	/**
	 * Constructs the page model with a provided width and height
	 * @param width
	 * @param height
	 */
	public BPage(int width, int height) {
		pageModel = new BPageModel(width, height);
		setDrawKnobs(true);
	}
	
	/**
	 * Constructs new page from inputted model. Sets drawknobs to false
	 */
	public BPage(BPageModel model){
		setModel(model);
		setDrawKnobs(false);
	}
	public void setModel(BPageModel model) {
		this.pageModel = model;
	}

	/**
	 * Adds a provided shape to the Page 
	 * @param shape
	 */
	public Rectangle addShape(BShape shape) {
		pageModel.addShape(shape);
		return null;
	}
	
	/**
	 * Removes a provided shape from the Page 
	 * @param shape
	 */
	public void removeShape(BShape shape) {
		pageModel.removeShape(shape);
	}
	
	/**
	 * Moves a provided shape to the front of the page
	 * @param shape
	 */
	public void moveToFront(BShape shape) {
		pageModel.moveToFront(shape);
		
	}
	
	/**
	 * Moves a provided shape to the back of the page
	 * @param shape
	 */
	public void moveToBack(BShape shape) {
		pageModel.moveToBack(shape);
	}
	
	/**
	 * Returns the name of the page
	 * @return
	 */
	public String getName() {
		return pageModel.getName();
	}
	
	/**
	 * Sets the name of the page;
	 * @param name
	 */
	public void setName(String name) {
		pageModel.setName(name);
	}
	
	/**
	 * Returns the bounds of the page as a Rectangle
	 * @return Rectangle
	 */
	public Rectangle getBounds() {
		return pageModel.getBounds();
	}
	
	/**
	 * Sets the bounds of the page with a Rectangle
	 * @param newBounds
	 */
	public void setBounds(Rectangle newBounds) {
		pageModel.setBounds(newBounds);
	}
	
	/**
	 * Returns the shapes on the page
	 * @return List<BShape>
	 */
	public List<BShape> getShapes() {
		return pageModel.getShapes();
	}
	
	/**
	 * Returns the shape associated with the point. returns null otherwise
	 * @param pt
	 * @return
	 */
	public BShape getShape(Point pt) {
		return pageModel.getShape(pt);
	}
	
	/**
	 * Draws a provided shape on the page
	 * @param g
	 * @param selected
	 */
	public void draw(Graphics g, BShape selected) {
		BDataModel data = pageModel.getData();
		if(data != null){
			
		}
		for(BShape shape: pageModel.getShapes()) {
			if(!shape.getHidden()) {
			    shape.draw(g);
			    if(selected == (shape)&&drawKnobs) drawShapeWithKnobs(g, selected);
			}	
		}
	}
	
	public void draw(Graphics g, BShape selected, boolean drawHidden) {
	    BDataModel data = pageModel.getData();
        if(data != null){
            
        }
        for(BShape shape: pageModel.getShapes()) {
            shape.draw(g);
            if(selected == (shape)&&drawKnobs) drawShapeWithKnobs(g, selected);
        }
	}
	
	/**
	 * Draws knobs on the selected shape
	 * @param g
	 * @param selected
	 */
	public void drawShapeWithKnobs(Graphics g, BShape selected) {
		List<Point> knobs = selected.getKnobs();
		for(Point p: knobs) {
			g.setColor(Color.BLACK);
			g.fillRect((int)p.getX()-BShape.KNOB_SIZE/2, (int)p.getY()-BShape.KNOB_SIZE/2, 
					BShape.KNOB_SIZE, BShape.KNOB_SIZE);
		}
	}

	/**
	 * @param drawKnobs the drawKnobs to set
	 */
	public void setDrawKnobs(boolean drawKnobs) {
		this.drawKnobs = drawKnobs;
	}

	public boolean contains(BShape selected) {
		// TODO Auto-generated method stub
		return pageModel.contains(selected);
	}
	
	public BPageModel getModel() {
		return pageModel;
	}

	public void executeScript(String s, BShape selected, BShape droppedOn, BGameCanvas canvas) {
		pageModel.executeScript(s, selected, droppedOn, canvas);
	}

	public BShape getDroppedOn(Point point, BShape selected) {
		return pageModel.getDroppedOn(point, selected);
	}

	public void drawIsDroppable(Graphics g, BShape selected) {
		for(BShape shape: pageModel.getShapes()) {
			if(shape.canRecieve(selected)){
				Rectangle r = shape.getBounds();
				g.setColor(Color.GREEN);
				g.drawRect(r.x, r.y, r.width, r.height);
			}
		}

	}

	public void removeCanvasAsListener(BGameCanvas gameCanvas) {
		pageModel.removeCanvasAsListener(gameCanvas);
	}

	public void addListener(BModelListener l) {
		pageModel.addListener(l);
	}
	public String getBackgroundName(){
		return pageModel.getBackground();
	}
	public void setBackgroundName(String background){
		pageModel.setBackground(background);
	}

	public BShape getShapeForEditor(Point pt) {
		return pageModel.getShapeForEditor(pt);
	}
}
