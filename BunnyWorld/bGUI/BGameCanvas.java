package bGUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;

import script.Script;

import data.BDataModel;
import data.BModelListener;
import data.BPage;
import data.BPageModel;
import data.BShape;
import data.BShapeModel;

/**
 * 
 */

/**
 * @author Matthew Chun-Lum
 * @author Chidozie Nwobilor
 *
 */
public class BGameCanvas extends BCanvas {
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = PossessionsAreaModel.DEFAULT_HEIGHT + BPageModel.DEFAULT_HEIGHT;
    
    private PossessionsArea pa;
    private Rectangle initialBounds;
    private boolean wasOnPage;
    private BDataModel data;
    
    public BGameCanvas(BDataModel model) {
        super(GAME_WIDTH, GAME_HEIGHT);
        setPa(new PossessionsArea());
        super.currentPage = null;
        initialBounds = null;
        wasOnPage = true;
        pa.setBounds(new Rectangle(0,BPageModel.DEFAULT_HEIGHT, PossessionsAreaModel.DEFAULT_WIDTH, PossessionsAreaModel.DEFAULT_HEIGHT));
        setData(model);
    }
    public void flipTo(BPage page){
    	if(page.getModel() == null) return;
    	if(currentPage != null) currentPage.removeCanvasAsListener(this);
    	currentPage = page;
    	selected = null;
    	currentPage.addListener(this);
    	Rectangle r = currentPage.getBounds();
        pa.setXY(0,r.height);
        page.executeScript(Script.ENTER, null, null, this);
    }
    private boolean IsInPossessionArea(){
    	List<Point> knobs = selected.getKnobs();
		for(Point pt: knobs){
			if(pa.getBounds().contains(pt)) return true;
		}
    	return false;
    }
    private boolean IsOnPage(){
		List<Point> knobs = selected.getKnobs();
		for(Point pt: knobs){
			if(currentPage.getBounds().contains(pt)) return true;
		}
    	return false;
    }
    // ---------------- Protected ------------- //
    @Override
    protected void handleMouseDraggedEvent(MouseEvent e) {
    	if(selected != null) {
            // move the selected shape and update the last point
            selected.move(getDeltaPoint(e.getPoint()));
            setLastPoint(e.getPoint());
            if(selected.getMovable()){
            	if(IsInPossessionArea() && !PointOnPage(e.getPoint())){
            		if(!pa.contains(selected)){
            			Rectangle r = pa.addShape(selected);
            			currentPage.removeShape(selected);
            			selected.getModel().setX(r.x);
            			selected.getModel().setY(r.y);
                    	//System.out.println("PA ADD");
                	}
                	//System.out.println("PA");
                }else if(IsOnPage()){
                	if(!currentPage.contains(selected)){
                		currentPage.addShape(selected);
                		//System.out.println("CP ADD");
                		pa.removeShape(selected);
                	}
                	//System.out.println("Page");
                }
            }
            repaint();
        }
            
    }

    @Override
    protected void handleMousePressedEvent(MouseEvent e) {
    	
    	Point pt = e.getPoint();
        
        // must update the last point
        setLastPoint(pt);
        
        if(PointOnPage(pt))selected = currentPage.getShape(pt);
        else if(PointInPossessionArea(pt)){
        	selected = pa.getShape(pt);
        }else{
        	selected = null;
        }
        if(selected != null && selected.getHidden()) selected = null; //prevents hidden objects from being moved
        if(selected != null && currentPage.contains(selected)){
			currentPage.executeScript(Script.CLICK, selected, null, this);
		}
        if(selected != null){
			initialBounds = selected.getBounds();
			wasOnPage = currentPage.contains(selected);
		}
        repaint();
    }
    private boolean PointInPossessionArea(Point pt) {
    	return pa.getBounds().contains(pt);
	}
	private boolean PointOnPage(Point pt) {
		return currentPage.getBounds().contains(pt);
	}
	@Override
	protected void handleMouseReleasedEvent(MouseEvent e) {
		if(selected != null && !IsInPossessionArea()){
			//System.out.println("in pa " + selected);
			if(CheckBounds(pa)){
        		if(!pa.contains(selected)){
        			Rectangle r = pa.addShape(selected);
        			currentPage.removeShape(selected);
        			selected.getModel().setX(r.x);
        			selected.getModel().setY(r.y);
                	//System.out.println("PA ADD");
            	}
            	//System.out.println("PA");
			}
		}
    	if(selected != null){
    		BShape droppedOn = currentPage.getDroppedOn(e.getPoint(), selected);
        	if(droppedOn != null && !droppedOn.getHidden()){
        		if(droppedOn.canRecieve(selected)) currentPage.executeScript(Script.DROP, selected, droppedOn, this);
        		else{
        			SnapBack();
        		}
        	}
    	}
    	
    	repaint();
	}
    private boolean CheckBounds(BPage page) {
		int sY = selected.getKnobs().get(0).y;
		int sheight = selected.getBounds().height;
		int pY = page.getBounds().y;
		return ((pY - sY) <= (sheight / 2));
	}
	private void SnapBack() {
		if(wasOnPage){
			selected.setBounds(initialBounds);
		}else{
			Rectangle r = pa.addShape(selected);
			currentPage.removeShape(selected);
			selected.getModel().setX(r.x);
			selected.getModel().setY(r.y);
		}
	}
	@Override
	protected void handleMouseClickedEvent(MouseEvent e) {
		
	}
	/**
	 * @param pa the pa to set
	 */
	protected void setPa(PossessionsArea pa) {
		this.pa = pa;
		pa.setDrawKnobs(false);
	}
	/**
	 * @return the pa
	 */
	protected PossessionsArea getPa() {
		return pa;
	}
	 /**
     * Paints the canvas. If the current page is not null,
     * invokes the page's draw method.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(selected != null)currentPage.drawIsDroppable(g, selected);
        pa.draw(g, selected);
        g.setColor(Color.BLACK);
    	g.drawLine(0, currentPage.getBounds().height, currentPage.getBounds().width, currentPage.getBounds().height);
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
}
