package data;


import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Chidozie Nwobilor
 *
 */
public class BShapeTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link BShape#BShape()}.
	 */
	@Test
	public void testBShape() {
		BShape shape = new BShape();
		Rectangle r = initBounds();
		assertEquals(shape.getBounds(), r);
		assertEquals(shape.getName(), "");
		assertEquals(shape.getFont(),BShape.DEFAULT_FONT);
		assertTrue(shape.getFont().isPlain());
		assertEquals(shape.getImageName(), "");
		assertEquals(shape.getBigBounds(), new Rectangle(r.x-BShape.KNOB_SIZE/2, r.y-BShape.KNOB_SIZE/2, 
				r.width + BShape.KNOB_SIZE, r.height + BShape.KNOB_SIZE));
		ArrayList<Point> knobs = setUpKnobs(new Point(r.x,r.y), new Point(r.x + r.width, r.y), 
				new Point(r.x, r.y + r.height), new Point(r.x + r.width, r.y + r.height));
		assertTrue(hasKnobs(shape, knobs));
	}
	private Rectangle initBounds(){
		return new Rectangle(BShape.DEFAULT_START.x, BShape.DEFAULT_START.y, BShape.DEFAULT_WIDTH, BShape.DEFAULT_HEIGHT);
	}
	private ArrayList<Point> setUpKnobs(Point point, Point point2,
			Point point3, Point point4) {
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(point);
		result.add(point2);
		result.add(point3);
		result.add(point4);
		return result;
	}
	private boolean hasKnobs(BShape shape, ArrayList<Point> knobs) {
		List<Point> shk = shape.getKnobs();
		Iterator<Point> iter = shk.iterator();
		while(iter.hasNext()){
			if(!knobs.contains(iter.next())) return false;
		}
		return true;
	}

	/**
	 * Test method for {@link BShape#move(java.awt.Point)}.
	 */
	@Test
	public void testMove() {
		BShape shape = new BShape();
		shape.move(1, 1);
		assertEquals(shape.getBounds(), new Rectangle(1, 1, BShape.DEFAULT_WIDTH, BShape.DEFAULT_HEIGHT));
		for(int i = 0; i < 10; i++){
			shape.move(1, 1);
		}
		for(int i = 0; i < 10; i++){
			shape.move(-1, -1);
		}
		assertEquals(shape.getBounds(), new Rectangle(1, 1, BShape.DEFAULT_WIDTH, BShape.DEFAULT_HEIGHT));
		for(int i = 0; i < 10; i++){
			shape.move(-1, -1);
		}
		assertEquals(shape.getBounds(), new Rectangle(-9, -9, BShape.DEFAULT_WIDTH, BShape.DEFAULT_HEIGHT));
		for(int i = 0; i < 10; i++){
			shape.move(10, 10);
		}
		for(int i = 0; i < 10; i++){
			shape.move(-10, -10);
		}
		assertEquals(shape.getBounds(), new Rectangle(-9, -9, BShape.DEFAULT_WIDTH, BShape.DEFAULT_HEIGHT));
	}

	/**
	 * Test method for {@link BShape#resize(java.awt.Point, java.awt.Point)}.
	 */
	@Test
	public void testResize() {
		BShape shape = new BShape();
		Rectangle r = initBounds();
		shape.resize(shape.getAnchorForPoint(BShape.DEFAULT_START), GetNewPoint(BShape.DEFAULT_START, new Point(1, 1)));
		assertEquals(shape.getBounds(), new Rectangle(1, 1, r.width - 1, r.height - 1));
		List<Point> knobs = shape.getKnobs();
		Point pt = knobs.get(0);
		Point delta = new Point(-11, -11);
		Point pt2 = GetNewPoint(pt, new Point(-11, -11));
		r = shape.getBounds();
		shape.resize(shape.getAnchorForPoint(pt), pt2);
		assertEquals(shape.getBounds(), new Rectangle(pt2.x, pt2.y, r.width - delta.x, r.height - delta.y));
	}

	private Point GetNewPoint(Point p, Point delta) {
		return new Point(p.x + delta.x, p.y + delta.y);
	}

	/**
	 * Test method for {@link BShape#getKnobs()}.
	 */
	@Test
	public void testGetKnobs() {
		BShape shape = new BShape();
		Rectangle r = initBounds();
		TestCurrentKnobs(shape, r);
	}
	private void TestCurrentKnobs(BShape shape, Rectangle r){
		ArrayList<Point> knobs = setUpKnobs(new Point(r.x,r.y), new Point(r.x + r.width, r.y), 
				new Point(r.x, r.y + r.height), new Point(r.x + r.width, r.y + r.height));
		assertTrue(hasKnobs(shape, knobs));
	}
	/**
	 * Test method for {@link BShape#getAnchorForPoint(java.awt.Point)}.
	 */
	@Test
	public void testGetAnchorForPoint() {
		BShape shape = new BShape();
		Rectangle r = initBounds();
		TestCurrentAnchors(shape, r);
	}
	
	private void TestCurrentAnchors(BShape shape, Rectangle r){
		assertEquals(shape.getAnchorForPoint(new Point(r.x, r.y)), new Point(r.x + r.width,
				r.y + r.height));
		assertEquals(shape.getAnchorForPoint(new Point(r.x + r.width,
				r.y + r.height)), new Point(r.x, r.y));
		assertEquals(shape.getAnchorForPoint(new Point(r.x + r.width, r.y)), 
				new Point(r.x,	r.y + r.height));
		assertEquals(shape.getAnchorForPoint(new Point(r.x , r.y + r.height)), 
				new Point(r.x + r.width, r.y));
	}

	/**
	 * Test method for {@link BShape#getBigBounds()}.
	 */
	@Test
	public void testGetBigBounds() {
		BShape shape = new BShape();
		Rectangle r = initBounds();
		assertEquals(shape.getBigBounds(), new Rectangle(r.x-BShape.KNOB_SIZE/2, r.y-BShape.KNOB_SIZE/2, 
				r.width + BShape.KNOB_SIZE, r.height + BShape.KNOB_SIZE));
	}
	/**
	 * Test method for {@link BShape#setFontBold(boolean)}
	 */
	@Test
	public void testSetFontBold(){
		BShape shape = new BShape();
		assertTrue(shape.getFont().isPlain());
		shape.setFontBold(true);
		assertTrue(shape.getFont().isBold());
		shape.setFontBold(false);
		assertFalse(shape.getFont().isBold());
		shape.setFontItalics(true);
		shape.setFontBold(true);
		assertTrue(shape.getFont().isBold());
		shape.setFontItalics(false);
		assertTrue(shape.getFont().isBold());
	}
	/**
	 * Test method for {@link BShape#setFontItalic(boolean)}
	 */
	@Test
	public void testSetFontItalic(){
		BShape shape = new BShape();
		assertTrue(shape.getFont().isPlain());
		shape.setFontItalics(true);
		assertTrue(shape.getFont().isItalic());
		shape.setFontItalics(false);
		assertFalse(shape.getFont().isBold());
		shape.setFontItalics(true);
		shape.setFontBold(true);
		assertTrue(shape.getFont().isBold());
		shape.setFontBold(false);
		assertTrue(shape.getFont().isItalic());
		
	}
}
