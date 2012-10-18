package data;




import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bGUI.BEditorCanvas;

import script.Script;

/**
 * @author Chidozie Nwobilor
 */
public class BShapeModel {
	private int x;
	private int y;
	private int width;
	private int height;
	private String text;
	private Font font;
	private Map<String, String> scripts;
	private Color color;
	private String name;
	private String imageName;
	private boolean hidden;
	private boolean movable;
	private BDataModel data;
	
	private ArrayList<BModelListener> listen;
	/**
	 * 
	 */
	public BShapeModel() {
		listen = new ArrayList<BModelListener>();
		setX(0);
		setY(0);
		setWidth(BShape.DEFAULT_WIDTH);
		setHeight(BShape.DEFAULT_HEIGHT);
		setScripts(new HashMap<String, String>());
		setColor(Color.LIGHT_GRAY);
		setImageName("none");
		setHidden(false);
		setMovable(true);
	}
	private int SmallestInt(int a, int b){
		if(a < b) return a;
		return b;
	}
	public void DefineWithTwo(Point a, Point b){
		this.height = Math.abs(a.y - b.y);
		this.width = Math.abs(a.x - b.x);
		this.x = SmallestInt(a.x, b.x);
		this.y = SmallestInt(a.y, b.y);
	}
	public void adjust(Point anchor, Point newPoint){
		DefineWithTwo(anchor, newPoint);
	}
	public void adjust(Point delta, Point moving, Point anchor){
		Point next = new Point(moving.x + delta.x, moving.y + delta.y);
		DefineWithTwo(anchor, next);
		notifyAllListeners();
	}
	public void move(Point delta){
		this.x += delta.x;
		this.y += delta.y;
		notifyAllListeners();
	}
	public void addListener(BModelListener l){
		listen.add(l);
	}
	public void removeListener(BModelListener l){
		Iterator<BModelListener> iter = listen.iterator();
		while(iter.hasNext()){
			BModelListener curr = iter.next();
			if(curr == l) iter.remove();
		}
	}
	public void notifyAllListeners(){
		if(listen.size() <= 0) return;
		Iterator<BModelListener> iter = listen.iterator();
		while(iter.hasNext()){
			iter.next().modelChanged(this);
		}
	}
	public void setBounds(Rectangle r){
		setBounds(r.x, r.y, r.width, r.height);
	}
	public void setBounds(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		notifyAllListeners();
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,width, height);
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
		notifyAllListeners();
	}
	public int getX() {
		return x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
		notifyAllListeners();
	}
	public int getY() {
		return y;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
		notifyAllListeners();
	}
	public int getWidth() {
		return width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
		notifyAllListeners();
	}
	public int getHeight() {
		return height;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
		notifyAllListeners();
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
		notifyAllListeners();
	}
	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}
	/**
	 * @param scripts the scripts to set
	 */
	public void setScripts(Map<String, String> scripts) {
		this.scripts = scripts;
	}
	public void setScript(String script){
		addScript(script);
	}
	/**
	 * @return the scripts
	 */
	public Map<String, String> getScripts() {
		return scripts;
	}
	/**
	 * adds script if valid to list of scripts
	 * @param script
	 */
	public void addScript(String script){
		scripts = Script.parseScript(script);
	}
	
	public BShapeModel clone(){
		BShapeModel result = new BShapeModel();
		result.setX(this.x);
		result.setY(this.y);
		result.setWidth(this.width);
		result.setHeight(this.height);
		result.setScripts(this.getScripts());
		result.setData(this.data);
		result.setFont(getFont());
		result.setColor(getColor());
		result.setData(getData());
		result.setImageName(imageName);
		result.setName(getName());
		result.setHidden(hidden);
		result.setMovable(movable);
		result.setScripts(scripts);
		result.setText(text);
		return result;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
		notifyAllListeners();
	}
	/**
	 * @return the hidden
	 */
	public boolean getHidden() {
		return hidden;
	}
	/**
	 * @param movable the movable to set
	 */
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	/**
	 * @return the movable
	 */
	public boolean getMovable() {
		return movable;
	}
	public boolean canRecieve(BShape selected) {
		String name = selected.getName();
		String script = scripts.get(Script.DROP + " " + name);
		if(script == null) return false;
		//System.out.println(this.name + " " + Script.DROP + " " + name);
		return name.equals(Script.extractShapeName(script));
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

	
	public String toString() {
	    return name;
	}
	
	public String getScriptBlock(){
		String result = "";
		Iterator<String> iter = scripts.keySet().iterator();
		while(iter.hasNext()){
			result += scripts.get(iter.next()) + "\n";
		}
		return result;
	}
	public boolean containsListener(BModelListener l) {
		return listen.contains(l);
	}

}
