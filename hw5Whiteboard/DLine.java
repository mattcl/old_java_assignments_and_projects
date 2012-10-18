import java.awt.*;
import java.util.*;

/**
 * 
 */

/**
 * @author admin
 *
 */
public class DLine extends DShape {

    /**
     * @param model
     * @param canvas
     */
    public DLine(DShapeModel model, Canvas canvas) {
        super(model, canvas);
    }

    /* (non-Javadoc)
     * @see DShape#draw(java.awt.Graphics, boolean)
     */
    @Override
    public void draw(Graphics g, boolean selected) {
        DLineModel lineM = getModel();
        g.setColor(getColor());
        g.drawLine(lineM.getPoint1().x, lineM.getPoint1().y, lineM.getPoint2().x, lineM.getPoint2().y);
        if(selected) drawKnobs(g);
    }

    /* (non-Javadoc)
     * @see DShape#getModel()
     */
    @Override
    public DLineModel getModel() {
        return (DLineModel) model;
    }
    
    public ArrayList<Point> getKnobs() {
        if(knobs == null || needsRecomputeKnobs) {
            knobs = new ArrayList<Point>();
            DLineModel lineM = (DLineModel) model;
            knobs.add(new Point(lineM.getPoint1()));
            knobs.add(new Point(lineM.getPoint2()));
            needsRecomputeKnobs = false;
        }
        return knobs;
    }

}
