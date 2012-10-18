/**
 * 
 */
package bGUI;

import java.awt.*;

import javax.swing.*;

/**
 * @author admin
 *
 */
public class BInspectorContainer extends JPanel {
    public static final String SHAPE_INSPECTOR = "Shape Inspector";
    public static final String RESOURCE_INSPECTOR = "Resource Inspector";
    public static final String PAGE_INSPECTOR = "Page Inspector";
    
    private BEditor editor;
    private BShapeInspectorPanel shapeInspector;
    private BResourceInspectorPanel resourceInspector;
    private BPageInspectorPanel pageInspector;
    
    public BInspectorContainer(BEditor editor, BShapeInspectorPanel shapeInspector, BResourceInspectorPanel resourceInspector, BPageInspectorPanel pageInspector) {
        this.editor = editor;
        this.shapeInspector = shapeInspector;
        this.resourceInspector = resourceInspector;
        this.pageInspector = pageInspector;
        
        setLayout(new CardLayout());
        
        add(shapeInspector, SHAPE_INSPECTOR);
        add(resourceInspector, RESOURCE_INSPECTOR);
        add(pageInspector, PAGE_INSPECTOR);
        
        
    }
    
    /**
     * Switches the inspector view
     * @param inspector
     */
    public void changeInspector(String inspector) {
        if(inspector.equals(SHAPE_INSPECTOR) || inspector.equals(RESOURCE_INSPECTOR) || inspector.equals(PAGE_INSPECTOR)) {
            CardLayout layout = (CardLayout) getLayout();
            layout.show(this, inspector);
        }
    }

}
