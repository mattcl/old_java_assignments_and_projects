package bGUI;
import java.util.*;
import java.util.List;
import java.awt.*;

import data.BPageModel;
import data.BShape;

/**
 * 
 * @author Junichi Tsutsui
 * @author Chidozie Nwobilor
 *
 */
public class PossessionsAreaModel extends BPageModel {
	
	public static final int DEFAULT_WIDTH = BGameCanvas.GAME_WIDTH;
	public static final int DEFAULT_HEIGHT = 100;
	private static final int DEFALUT_X = 0;
	private static final int DEFAULT_Y = 501;
	
	private ArrayList<BShape> scaledShapes;
	private static final int OFFSET = 50;
	/**
	 * Constructs a PossessionsAreaModel with a provided x, y, width, and height
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public PossessionsAreaModel(int x, int y, int width, int height) {
		shapes = new ArrayList<BShape>();
		bounds = new Rectangle(x, y, width, height);
		scaledShapes = new ArrayList<BShape>();
		
	}
	
	/**
	 * Default constructor for PossessionsAreaModel
	 */
	public PossessionsAreaModel () {
		this(DEFALUT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	/**
	 * Returns the shape that contains the point given by pt
	 * @param pt
	 * @return BShape
	 */
	public BShape getShape(Point pt) {
		for(int i = scaledShapes.size() - 1; i >= 0;i--){
			if(scaledShapes.get(i).getBounds().contains(pt)) return shapes.get(i);
		}
		return null;
	}
	public Rectangle addShape(BShape shape){
		Rectangle r = (Rectangle) this.getBounds().clone();
		r.x = OFFSET * shapes.size();
		super.addShape(shape);
		scaledShapes.add(BShape.getScaled(shape, r));
		return BShape.getScaled(shape, r).getBounds();
	}
	public void removeShape(BShape shape){
		int index = shapes.indexOf(shape);
		super.removeShape(shape);
		scaledShapes.remove(index);
		Reshuffle(index);
		return;
	}
	/*
	 * This function moves all the elements after the removed element forward
	 */
	private void Reshuffle(int index) {
		for(int i = index; i < scaledShapes.size(); i++){
			BShape curr = scaledShapes.get(i);
			Rectangle bounds = curr.getBounds();
			bounds.x = i * OFFSET;
			curr.setBounds(bounds);
		}
	}

	/**
	 * Returns the array of BShapes within the BPageModel
	 * @return List<BShape>
	 */
	public List<BShape> getShapes() {
		return scaledShapes;
	}

	public boolean isShape(BShape scaledShape, BShape selected) {
		return selected == shapes.get(scaledShapes.indexOf(scaledShape));
	}

	public void setXY(int x, int y) {
		bounds.x = x;
		bounds.y = y;
	}
}
