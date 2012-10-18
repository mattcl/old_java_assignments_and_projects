import java.util.*;

/*
 Shape data for ShapeClient:
 "0 0  0 1  1 1  1 0"
 "10 10  10 11  11 11  11 10"
 "0.5 0.5  0.5 -10  1.5 0"
 "0.5 0.5  0.75 0.75  0.75 0.2"
*/

public class Shape {
    // This is declared as static in the even that the client wishes to use
    // the enum for something.
    public static enum Relation {
        NO_INTERSECTION, IS_CONTAINED_IN, ENCIRCLES
    }
    
    private Point center;
    private double radius;
    private LinkedList<Point> points;
    
    
    /**
     * Constructs a shape with a given descriptor. The descriptor is a string
     * representing an even number of double values separated by spaces.
     * 
     * @param descriptor
     */
    public Shape(String descriptor) {
        extractPoints(descriptor);
        computeCenter();
        computeRadius();
    }
    
    /**
     * Compares self with another shape object to determine if self and the other
     * shape "cross." Assumes a valid shape object.
     * 
     * @param s
     * @return
     */
    
    public boolean crossesShape(Shape s) {
        LinkedList<Point> refToS = s.getPoints();
        double last = center.distance(refToS.getFirst());
        double current = radius * 2;
        for (int i = 1; i < refToS.size(); i++) {
           current = center.distance(refToS.get(i));
           if(last <= radius && current > radius || 
               last > radius && current <= radius) {
               return true;
            }
           last = current;
        }
        current = center.distance(refToS.getFirst());
        return (last <= radius && current > radius || last > radius && current <= radius);
    }
    
    /**
     * @param s
     * @return the ordinal of the enum Relation which corresponds to the relation
     * of self and the passed circle.
     */
    
    public int encircles(Shape s) {
        double centerDistance = center.distance(s.getCenter());
        if (centerDistance <= radius) return Relation.ENCIRCLES.ordinal();
        if (centerDistance <= radius + s.getRadius()) return Relation.IS_CONTAINED_IN.ordinal();
        return Relation.NO_INTERSECTION.ordinal();
    }
    
    /**
     * @return a reference to the internal points list.
     */
    
    public LinkedList<Point> getPoints() {
        return points;
    }
    
    /**
     * @return a copy of the shape's center
     */
    
    public Point getCenter() {
        return new Point(center);
    }
    
    /**
     * @return the radius
     */
    
    public double getRadius() {
        return radius;
    }
    
    // ----------- Private Methods ------------- //
    
    /**
     * Extracts the points from the string. Assumes a valid string.
     */
    private void extractPoints(String scannerInput) {
        points = new LinkedList<Point>();
        Scanner s = new Scanner(scannerInput);
        /*if (s.match().groupCount() % 2 != 0) {
            System.err.println("Invalid input parameter to extractPoints: " + scannerInput);
            return;
        }*/
        while(s.hasNextDouble())
            points.add(new Point(s.nextDouble(), s.nextDouble()));
    }
    
    /**
     * Computes the center of the shape as dictated by the arithmetic mean.
     */
    private void computeCenter() {
        double xTotal = 0, yTotal = 0;
        for (Point p : points) {
            xTotal += p.getX();
            yTotal += p.getY();
        }
        center = new Point(xTotal/points.size(), yTotal/points.size());
    }
    
    /**
     * Computes the radius of the circle defined by the shape. Since the point
     * closest to the center lies on the edge of the circle, the distance
     * between that point and the center must be the radius of the circle.
     * 
     * I felt computing the radius would be too taxing an operation to perform
     * every time, so the value is stored in an instance variable.
     */
    
    private void computeRadius() {
        // initialize the radius to a value far larger or equal to
        // the actual minimum distance.
        radius = center.distance(points.getFirst()) * points.size();
        for (Point p : points)
            if (center.distance(p) < radius)
                radius = center.distance(p);
    }
}

