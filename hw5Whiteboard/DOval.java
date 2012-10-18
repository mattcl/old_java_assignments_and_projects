import java.awt.*;

/**
 * 
 */

/**
 * @author admin
 *
 */
public class DOval extends DShape {

    public DOval(DShapeModel model, Canvas canvas) {
        super(model, canvas);
    }
    
    public DOvalModel getModel() {
        return (DOvalModel) model;
    }
    
    public void draw(Graphics g, boolean selected) {
        g.setColor(model.getColor());
        Rectangle bounds = model.getBounds();
        g.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
        if(selected) drawKnobs(g);
    }

}
