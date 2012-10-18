package smovielib;
import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;

/**
 * This is a helper class for Timeline. You don't need to modify or understand
 * it for the base assignment, but it might help if you're doing certain
 * extensions.
 * 
 */
class GTimelineElement extends GCompound {
	private GLabel label = new GLabel("");
	private GRect outerRectangle = new GRect(0, 0);
	
	private Clip clip;
	
	public static final int ELEMENT_HEIGHT = 20;
	public static final double FRAMES_PER_PIXEL = 10;
	
	private GCompound leftHandle;
	private GCompound rightHandle;
	
	/**
	 * Initializes a timeline element based on the information in the given
	 * clip model.
	 * 
	 * @param clip The clip to initalize from
	 */
	GTimelineElement(Clip clip) {
		this.clip = clip;

		outerRectangle.setFillColor(Color.GRAY);
		add(outerRectangle);
		
		add(label);
		
		leftHandle = new GCompound();
		leftHandle.add(new GRect(0, 0, 10, 10));
		leftHandle.add(new GLine(2, 3, 2, 7));
		leftHandle.add(new GLine(2, 5, 5, 5));
		this.add(leftHandle, 0, 0);
		
		rightHandle = new GCompound();
		rightHandle.add(new GRect(0, 0, 10, 10));
		rightHandle.add(new GLine(1, 5, 9, 5));
		rightHandle.add(new GLine(5, 2, 9, 5));
		rightHandle.add(new GLine(5, 8, 9, 5));
		this.add(rightHandle, getWidth() - 10, getHeight() - 10);
		
		modelChanged();
	}
	
	/**
	 * Updates the view based on the model. Gets all the information it needs
	 * from the model. Yay, MVC!
	 */
	void modelChanged() {
		setLocation(clip.getStartFrame() / FRAMES_PER_PIXEL, 
				    clip.getEffectOrder() * ELEMENT_HEIGHT);
		outerRectangle.setSize(
				(clip.getFramePastTheEnd() - clip.getStartFrame()) / FRAMES_PER_PIXEL,
			   ELEMENT_HEIGHT);
		
		int labelLength = clip.getLabel().length();
		do {
			label.setLabel(clip.getLabel().substring(0, labelLength));
			labelLength--;
		} while (labelLength >= 0 && label.getWidth() > getWidth() - 20);
		
		label.setLocation(
			getWidth() / 2 - label.getWidth() / 2, 
			getHeight() / 2  + label.getAscent() / 2);

		rightHandle.setLocation(getWidth() - 10, getHeight() - 10);
	}
	
	void setSelected(boolean selected) {
		outerRectangle.setFilled(selected);
	}
	
	public double getWidth() {
		return (clip.getFramePastTheEnd() - clip.getStartFrame()) / FRAMES_PER_PIXEL;
	}
	
	public double getHeight() {
		return ELEMENT_HEIGHT;
	}
	
	String getText() {
		return clip.getLabel();
	}
	
	Clip getClip() {
		return clip;
	}
	
	GCompound getLeftHandle() {
		return leftHandle;
	}
	
	GCompound getRightHandle() {
		return rightHandle;
	}
}
