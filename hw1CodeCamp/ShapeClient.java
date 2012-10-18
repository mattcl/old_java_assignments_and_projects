
public class ShapeClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Shape a = new Shape("0 0  0 1  1 1  1 0");
        Shape b = new Shape("10 10  10 11  11 11  11 10");
        Shape c = new Shape("0.5 0.5  0.5 -10  1.5 0");
        Shape d = new Shape("0.5 0.5  0.75 0.75  0.75 0.2");
        
        System.out.println("a crosses b: " + a.crossesShape(b));
        System.out.println("a crosses c: " + a.crossesShape(c));
        System.out.println("a crosses d: " + a.crossesShape(d));
        
        System.out.println("a encircles b: " + a.encircles(b));
        System.out.println("a encircles c: " + a.encircles(c));
        System.out.println("a encircles d: " + a.encircles(d));
        
    }

}