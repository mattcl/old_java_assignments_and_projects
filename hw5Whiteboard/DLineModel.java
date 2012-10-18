import java.awt.*;
/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class DLineModel extends DShapeModel {
    private Point p1, p2;
    
    public DLineModel() {
        super();
        p1 = new Point(bounds.x, bounds.y);
        p2 = new Point(bounds.x + bounds.width, bounds.y + bounds.height);
    }
    
    @Override
    public void mimic(DShapeModel other) {
        DLineModel toMimic = (DLineModel) other;
        setPoint1(toMimic.getPoint1());
        setPoint2(toMimic.getPoint2());
        super.mimic(other);
    }
    
    /**
     * Moves the point by the specified values
     */
    @Override
    public void move(int dx, int dy) {
        p1.x += dx;
        p1.y += dy;
        
        p2.x += dx;
        p2.y += dy;
        
        super.move(dx, dy);
    }
    
    public Point getPoint1() {
        return p1;
    }
    
    public void setPoint1(Point pt) {
        p1 = new Point(pt);
    }
    
    public Point getPoint2() {
        return p2;
    }
    
    public void setPoint2(Point pt) {
        p2 = new Point(pt);
    }
    
    public void modifyWithPoints(Point anchorPoint, Point movingPoint) {
        p1 = new Point(anchorPoint);
        p2 = new Point(movingPoint);
        super.modifyWithPoints(anchorPoint, movingPoint);
    }
    
}
