/**
 * 
 */

/**
 * @author Matt Chun-Lum
 *
 */
public class DTextModel extends DShapeModel {
    public static final String DEFAULT_TEXT = "Hello";
    public static final String DEFAULT_FONT = "Dialog";
    
    private String text;
    private String font;
    
    
    public DTextModel() {
        super();
        text = DEFAULT_TEXT;
        font = DEFAULT_FONT;
    }
   
    @Override
    public void mimic(DShapeModel other) {
        DTextModel toMimic = (DTextModel) other;
        setText(toMimic.getText());
        setFontName(toMimic.getFontName());
        super.mimic(other);
    }
    /** 
     * Gets the current text
     * @return
     */
    public String getText() {
        return text;
    }
    
    /**
     * Sets the text for the model
     * @param newText
     */
    public void setText(String newText) {
        text = newText;
        notifyListeners();
    }
    
    /**
     * Gets the name of the font
     * @return
     */
    public String getFontName() {
        return font;
    }
    
    /**
     * Sets the font name
     * @param fontName
     */
    public void setFontName(String fontName) {
        font = fontName;
        notifyListeners();
    }
    
}
