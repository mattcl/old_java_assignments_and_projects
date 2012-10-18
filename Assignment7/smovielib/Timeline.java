package smovielib;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;

import acm.graphics.GCanvas;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;

/**
 * This class represents the traditional movie editing "timeline" view.
 * 
 * 
 * NOTE: Although the code is provided, we don't expect you to modify it,
 *       nor will we test you on it. It's there so that you can extend it
 *       if you're looking to do extensions, or examine it if you're idly 
 *       wondering what's going on behind the scenes. It probably won't 
 *       give you any insight if you're having trouble with the base
 *       assignment.
 *
 */
public class Timeline implements MouseListener, MouseMotionListener {
	private GCanvas canvas;
	// Don't worry about the TimelineProgram - it's just an implementation detail
	private TimelineProgram tp;
	
	// Used to go from models to views efficiently
	private HashMap <Clip, GTimelineElement> modelToViewMap
			= new HashMap<Clip, GTimelineElement>();
	
	// Keeps track of the currently selected element
	private GTimelineElement selectedElement;
	
	// Keeps track of the state for mouse dragging events
	private GCompound selectedHandle;
	private boolean isLeftHandle;
	private double startingMouseX;
	private long delta;
	
	// This constructor is intentionally not visible from your program.
	// A Timeline is set up for you in TimelineProgram, and is
	// accessible through getTimeline()
	Timeline(GCanvas canvas, TimelineProgram tp) {
		this.canvas = canvas;
		this.tp = tp;
		// Equivalent to addMouseListeners() in professional Java-land
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
	}
	
	/**
	 * Adds the given clip to the timeline.
	 * 
	 * @param clip The clip to add
	 */
	public void addClip(Clip clip) {
		modelToViewMap.put(clip, new GTimelineElement(clip));
		canvas.add(modelToViewMap.get(clip));
		tp.repaint();
	}
	
	/**
	 * Removes the given clip from the timeline.
	 * 
	 * @param clip The clip to remove
	 */
	public void removeClip(Clip clip) {
		canvas.remove(modelToViewMap.get(clip));
		tp.repaint();
	}
	
	/**
	 * Signals to the timeline that the clip has been modified.
	 * This method should be called each time the clip is modified
	 * in order to ensure that the view is synchronized.
	 * 
	 * @param clip The modified clip
	 */
	public void clipModified(Clip clip) {
		modelToViewMap.get(clip).modelChanged();
		tp.repaint();
	}
	
	/**
	 * Returns the currently selected clip.
	 * If no clip is currently selected, then this method returns null.
	 * 
	 * @return The currently selected clip or null.
	 */
	public Clip getSelectedClip() {
		if (selectedElement == null) {
			return null;
		}
		return selectedElement.getClip();
	}

	/**
	 * Updates a selection internally.
	 * 
	 * @param elem the selected parameter.
	 */
	private void select(GTimelineElement elem) {
		if (selectedElement != null) {
			selectedElement.setSelected(false);
		}
		selectedElement = elem;
		selectedElement.setSelected(true);
	}
	
	public void mouseReleased(MouseEvent me) {
		delta = convertXToFrame(me.getX()) - convertXToFrame(startingMouseX);
		if (selectedHandle == null) {
			return;
		}
		for (TimelineListener listener : listeners) {
			if (isLeftHandle) {
				listener.clipMoved(selectedElement.getClip(), delta);
			} else {
				listener.clipResized(selectedElement.getClip(), delta);
			}
		}
		selectedHandle = null;
		tp.repaint();
	}
	
	// Never used, but we have to declare them because we're implementing
	// MouseListener
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent me) {}

	// Used to keep track of classes that are interested in receiving timeline
	// events.
	// Look up the observer pattern if you're interested in learning more.
	private HashSet<TimelineListener> listeners 
		= new HashSet<TimelineListener>();
	
	/**
	 * Adds a TimelineListener to be notified of events from this timeline.
	 * 
	 * @param tsl The listener to add
	 */
	public void addListener(TimelineListener tsl) {
		listeners.add(tsl);
	}
	
	/**
	 * Removes a TimelineListener from the list notified by this timeline.
	 * 
	 * @param tsl The listener to remove
	 */
	public void removeListener(TimelineListener tsl) {
		if (listeners.contains(tsl)) listeners.remove(tsl);
	}
	
	/**
	 * Converts from screen coordinates to timeline coordinates.
	 */
	private int convertXToFrame(double x) {
		return (int) (x * GTimelineElement.FRAMES_PER_PIXEL);
	}
	
	/**
	 * Converts from screen coordinates to timeline coordinates.
	 */
	private int convertYToOffset(double y) {
		return (int) (y / GTimelineElement.ELEMENT_HEIGHT);
	}
	
	/**
	 * Displays the given PixelMatrix in a pop-up window.
	 * 
	 * @param pm The image to display
	 * @param title Title for the window
	 */
	public void showPreview(PixelMatrix pm, String title) {
		GImage previewImage = new GImage(pm.getImage());
		GCanvas previewCanvas = new GCanvas();
		previewCanvas.add(previewImage);
		
		// Creates a new window for the previewImage. You
		// aren't expected to know this for this course, but it
		// might be useful later on.
		JFrame previewFrame = new JFrame(title);
		previewFrame.setContentPane(previewCanvas);
		previewFrame.getContentPane().setPreferredSize(
				new Dimension((int)previewImage.getWidth(), 
							  (int)previewImage.getHeight()));
		// pack causes it to lay itself out
		previewFrame.pack();
		previewFrame.setVisible(true);
	}
	
	public void mousePressed(MouseEvent e) {
		int frame = convertXToFrame(e.getX());
		int offset = convertYToOffset(e.getY());
		for (TimelineListener listener : listeners) {
			listener.pointSelected(frame, offset);
		}
		
		GObject elem = canvas.getElementAt(e.getX(), e.getY());
		// If we've clicked on a timeline element...
		if (elem instanceof GTimelineElement) {
			GTimelineElement timelineElem = (GTimelineElement) elem;
			GPoint location = timelineElem.getLocalPoint(e.getX(), e.getY());
			GObject interiorElement = timelineElem.getElementAt(location);
			// If it's a handle...
			if (interiorElement == timelineElem.getLeftHandle()) {
				selectedHandle = timelineElem.getLeftHandle();
				isLeftHandle = true;
			} else if (interiorElement == timelineElem.getRightHandle()){
				selectedHandle = timelineElem.getRightHandle();
				isLeftHandle = false;
			} else {
				selectedHandle = null;
			}
			startingMouseX = e.getX();
			select(timelineElem);
		}

		tp.repaint();
	}

	public void mouseMoved(MouseEvent e) {}
}
