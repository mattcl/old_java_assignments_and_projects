import java.awt.*;

/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class DText extends DShape {
    public static final double STARTING_SIZE = 1.0;
    
    private Font computedFont;
    private String lastFont;
    private int lastHeight;
    private boolean needsRecomputeFont;
    
    public DText(DShapeModel model, Canvas canvas) {
        super(model, canvas);
        computedFont = null;
        needsRecomputeFont = true;
        lastFont = "";
        lastHeight = -1;
    }
    
    /**
     * Gets the text stored in the model
     * @return
     */
    public String getText() {
        return getModel().getText();
    }
    
    /**
     * sets the text stored in the model
     * @param newText
     */
    public void setText(String newText) {
        getModel().setText(newText);
    }
    
    /**
     * Gets the font name stored in the model
     * @return
     */
    public String getFontName() {
        return getModel().getFontName();
    }
    
    /**
     * Sets the font name stored in the model
     * @param newName
     */
    public void setFontName(String newName) {
        if(newName.equals(getModel().getFontName())) return;
        //needsRecomputeFont = true;
        getModel().setFontName(newName);
    }
    
    /**
     * ensures to set needsRecomputeFont every time the shape is modified
     */
    @Override
    public void modifyShapeWithPoints(Point anchorPoint, Point movingPoint) {
        //needsRecomputeFont = true;
        super.modifyShapeWithPoints(anchorPoint, movingPoint);
    }
    
    /* (non-Javadoc)
     * @see DShape#draw(java.awt.Graphics, boolean)
     */
    @Override
    public void draw(Graphics g, boolean selected) {
        Shape clip = g.getClip();
        g.setClip(clip.getBounds().createIntersection(getBounds()));
        
        Font font = computeFont(g);
        int fontOffset = (int) font.getLineMetrics(getModel().getText(), ((Graphics2D) g).getFontRenderContext()).getDescent();
        int yPos = getBounds().y + getBounds().height - fontOffset;
        
        g.setColor(getColor());
        g.setFont(font);
       
        g.drawString(getModel().getText(), getBounds().x, yPos);
        g.setClip(clip);
        if(selected) drawKnobs(g);
    }

    /**
     * Computes the font attributes for the shape based on the model
     * @param g
     */
    public Font computeFont(Graphics g) {
        if(needsRecomputeFont) {
            double size = STARTING_SIZE;
            double lastSize = size;
            while(true) {
                computedFont = new Font(getFontName(), Font.PLAIN, (int) size);
                if(computedFont.getLineMetrics(getText(), ((Graphics2D) g).getFontRenderContext()).getHeight() > getModel().getBounds().getHeight())
                    break;
                lastSize = size;
                size = (size * 1.1) + 1;
            }
            computedFont = new Font(getFontName(), Font.PLAIN, (int) lastSize);
            needsRecomputeFont = false;
        }
        return computedFont;
        
        
    }
    
    /* (non-Javadoc)
     * @see DShape#getModel()
     */
    @Override
    public DTextModel getModel() {
        return (DTextModel) model;
    }
    
    @Override
    public void modelChanged(DShapeModel model) {
        DTextModel textModel = (DTextModel) model;
        if(textModel.getBounds().height != lastHeight || !textModel.getFontName().equals(lastFont)) {
            lastHeight = textModel.getBounds().height;
            lastFont = textModel.getFontName();
            needsRecomputeFont = true;
        }
        super.modelChanged(textModel);
    }

}
