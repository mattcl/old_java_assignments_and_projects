import junit.framework.TestCase;


public class ShapeTest extends TestCase {
    
    public void testShapeContruction1() {
        Shape a = new Shape("0 0  0 1  1 1  1 0");
        Shape b = new Shape("10 10  10 11  11 11  11 10");
        Shape c = new Shape("0.5 0.5  0.5 -10  1.5 0");
        Shape d = new Shape("0.5 0.5  0.75 0.75  0.75 0.2");
        
        assertEquals(4, a.getPoints().size());
        assertEquals(4, b.getPoints().size());
        assertEquals(3, c.getPoints().size());
        assertEquals(3, d.getPoints().size());
    }
    
    public void testShapeConstruction2() {
        Shape a = new Shape("0.5 0.5  0.5 -10  1.5 0");
        assertEquals("0.5 -10.0", a.getPoints().get(1).toString());
    }
    
    public void testShapeContruction3() {
        Shape a = new Shape("0.5 0.5  0.5 -10  1.5 0");
        
    }

}
