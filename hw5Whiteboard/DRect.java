import java.awt.*;
/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class DRect extends DShape {

    /**
     * @param model
     */
    public DRect(DShapeModel model, Canvas canvas) {
        super(model, canvas);
        
    }
    
    /**
     * Returns the model
     */
    public DRectModel getModel() {
        return (DRectModel) model;
    }
    
    public void draw(Graphics g, boolean selected) {
        g.setColor(model.getColor());
        Rectangle bounds = model.getBounds();
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        if(selected) drawKnobs(g);
    }

}
