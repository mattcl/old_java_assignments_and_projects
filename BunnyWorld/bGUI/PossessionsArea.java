package bGUI;
import java.awt.*;
import java.util.List;

import data.BPage;
import data.BShape;

/**
 * 
 * @author Junichi Tsutsui
 *
 */
public class PossessionsArea extends BPage {
	
	PossessionsAreaModel possessionsModel;
	
	/**
	 * Default constructor for PossessionsArea
	 */
	public PossessionsArea() {
		possessionsModel = new PossessionsAreaModel();
	}
	
	/**
	 * Constructs a Possession Area with a given x, y, width, height
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public PossessionsArea(int x, int y, int width, int height) {
		possessionsModel = new PossessionsAreaModel(x,y,width, height);
	}
	
	/**
	 * Returns a shape from the Possessions Area if the provided point pt is contained in the shape
	 * @param pt
	 * @return
	 */
	public BShape getShape(Point pt) {
		return possessionsModel.getShape(pt);
	}
	
	/**
	 * Adds a shape into the Possessions Area
	 */
	public Rectangle addShape(BShape shape) {
		return possessionsModel.addShape(shape);
	}
	
	/**
	 * Removes a shape from the Possessions Area
	 */
	public void removeShape(BShape shape) {
		possessionsModel.removeShape(shape);
	}
	
	/**
	 * Returns the name of the Possession Area
	 */
	public String getName() {
		return possessionsModel.getName();
	}
	
	/**
	 * Sets the name of the Possession Area
	 */
	public void setName(String name) {
		possessionsModel.setName(name);
	}
	
	/**
	 * Returns the bounds of the Possession Area in a Rectangle
	 */
	public Rectangle getBounds() {
		return possessionsModel.getBounds();
	}
	
	/**
	 * Sets the bounds of the Possession Area with the provided Rectangle
	 */
	public void setBounds(Rectangle newBounds) {
		possessionsModel.setBounds(newBounds);
	}
	
	/**
	 * Draws a provided shape on the Possessions Area
	 * @param g
	 */
	public void draw(Graphics g, BShape selected) {
		//Rectangle r = this.getBounds();
		//g.setColor(Color.white);
		//g.fillRect(r.x, r.y, r.width, r.height);
		for(BShape shape: possessionsModel.getShapes()) {
			g.setColor(shape.getColor());
			shape.draw(g);
			if(possessionsModel.isShape(shape, selected) && drawKnobs) drawShapeWithKnobs(g, shape);
		}
	}
	
	/**
	 * Draws knobs on a shape in the Possessions Area
	 */
	public void drawShapeWithKnobs(Graphics g, BShape selected) {
		List<Point> knobs = selected.getKnobs();
		for(Point p: knobs) {
			g.setColor(Color.BLACK);
			g.fillRect((int)p.getX()-BShape.KNOB_SIZE/2, (int)p.getY()-BShape.KNOB_SIZE/2, 
					BShape.KNOB_SIZE, BShape.KNOB_SIZE);
		}
	}
	public boolean contains(BShape selected) {
		// TODO Auto-generated method stub
		return possessionsModel.contains(selected);
	}

	public void setXY(int x, int y) {
		possessionsModel.setXY(x,y);
	}
}
