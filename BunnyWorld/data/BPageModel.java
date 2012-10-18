package data;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import script.Script;
import bGUI.*;

import bGUI.BGameCanvas;

/**
 * 
 * @author Junichi Tsutsui
 * @author Chidozie Nwobilor
 *
 */
public class BPageModel {
	
	protected List<BShape> shapes;
	protected String name;
	protected Rectangle bounds;
	protected String background;
	private boolean isTiled;
	private BDataModel data;
	private List<BModelListener> listeners;
	public static final int DEFAULT_WIDTH = BGameCanvas.GAME_WIDTH;
	public static final int DEFAULT_HEIGHT = 400;
	
	/**
	 * Constructs a BPageModel with a provided width and height
	 * @param width
	 * @param height
	 */
	public BPageModel(int width, int height) {
		shapes = new ArrayList<BShape>();
		bounds = new Rectangle(0,0, width, height);
		background = "";
		data = null;
		isTiled = false;
		listeners = new ArrayList<BModelListener>();
	}
	
	/**
	 * Default constructor of BPageModel
	 */
	public BPageModel() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * Adds a shape into BPageModel
	 * @param shape
	 */
	public Rectangle addShape(BShape shape) {
		shapes.add(shape);
		for(BModelListener l: listeners){
			if(!shapes.get(shapes.indexOf(shape)).listenerContains(l)){
				shape.addListener(l);
			}
		}
		return null;
	}
	
	/**
	 * Removes a shape from the BPageModel
	 * @param shape
	 */
	public void removeShape(BShape shape) {
		if(shape != null) {
			shapes.remove(shape);
		}
	}
	
	/**
	 * Moves a provided shape to the front of the BPageModel
	 * @param shape
	 */
	public void moveToFront(BShape shape) {
		if(shape!=null) {
			shapes.remove(shape);
			shapes.add(shape);
		}
	}
	
	/**
	 * Moves a given shape to the back of the BPageModel
	 * @param shape
	 */
	public void moveToBack(BShape shape) {
		if(shape!=null) {
			shapes.remove(shape);
			shapes.add(0, shape);
		}
	}
	
	/**
	 * Returns the name of the BPageModel
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the BPageModel to the provided String 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the bounds of the BPageModel through a Rectangle
	 * @return Rectangle
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Sets the bounds of the BPageModel to a given Rectangle
	 * @param newBounds
	 */
	public void setBounds(Rectangle newBounds) {
		bounds = newBounds;
	}
	
	/**
	 * Returns the array of BShapes within the BPageModel
	 * @return List<BShape>
	 */
	public List<BShape> getShapes() {
		return shapes;
	}
	
	public void setShapes(List<BShape> shapes) {
		this.shapes = shapes;
	}
	
	/**
	 * Returns the shape associated with the point if not hidden. returns null otherwise
	 * @param pt
	 * @return
	 */
	 public BShape getShape(Point pt) {
		for (int i=shapes.size()-1; i>=0; i--) {
			BShape s = shapes.get(i);
			Rectangle r = s.getBigBounds();
			if(r.contains(pt) && !s.getHidden()) return s;
		}
		return null;
	}
	 /**
	 * Returns the shape associated with the point. returns null otherwise
	 * @param pt
	 * @return
	 */
	 public BShape getShapeForEditor(Point pt) {
		for (int i=shapes.size()-1; i>=0; i--) {
			BShape s = shapes.get(i);
			Rectangle r = s.getBigBounds();
			if(r.contains(pt)) return s;
		}
		return null;
	}

	public boolean contains(BShape selected) {
		// TODO Auto-generated method stub
		return shapes.contains(selected);
	}

	public void executeScript(String s, BShape selected, BShape droppedOn, BGameCanvas canvas) {
		if(s.equals(Script.ENTER)){
			for(BShape shape: shapes){
				shape.doScript(s, canvas);
			}
		}else if(s.equals(Script.CLICK)){
			shapes.get(shapes.indexOf(selected)).doScript(s, canvas);
		}else if(s.equals(Script.DROP)){
			s = s + " " + selected.getName();
			if(selected.getName().equals(Script.extractShapeName(droppedOn.getScript().get(s)))){
				droppedOn.doScript(s, canvas);
			}
		}
	}
	/**
	 * finds the BShape the selected BShape has been dropped on
	 * @param point
	 * @param selected
	 * @return BShape droppedOn 
	 */
	public BShape getDroppedOn(Point point, BShape selected) {
		int index = shapes.indexOf(selected) - 1;
		while(index >= 0){
			BShape curr = shapes.get(index);
			List<Point> knobs = selected.getKnobs();
			for(Point pt: knobs){
				if(curr.getBounds().contains(pt)&& !curr.getHidden()) return curr;
			}
			index--;
		}
		return null;
	}

	public void removeCanvasAsListener(BGameCanvas gameCanvas) {
		listeners.remove(gameCanvas);
		for(BShape shape: shapes){
			shape.removeListener(gameCanvas);
		}
	}

	public void addListener(BModelListener l) {
		listeners.add(l);
		for(BShape shape: shapes){
			shape.addListener(l);
		}
	}
	
	public String toString() {
	    return name;
	}
	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(BDataModel data) {
		this.data = data;
	}

	/**
	 * @return the data
	 */
	public BDataModel getData() {
		return data;
	}

	/**
	 * @param isTiled the isTiled to set
	 */
	public void setTiled(boolean isTiled) {
		this.isTiled = isTiled;
		notifyAllListeners();
	}

	/**
	 * @return the isTiled
	 */
	public boolean isTiled() {
		return isTiled;
	}
	public BPageModel clone(){
		BPageModel clone = new BPageModel();
		clone.setBackground(background);
		clone.setBounds((Rectangle) this.getBounds().clone());
		clone.setName(this.name);
		for(BShape shape: shapes){
			clone.addShape(shape.clone());
		}
		clone.setTiled(this.isTiled);
		return clone;
	}
	private void notifyAllListeners(){
		
	}
}

