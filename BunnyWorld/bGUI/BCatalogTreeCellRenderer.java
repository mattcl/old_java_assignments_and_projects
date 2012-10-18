/**
 * 
 */
package bGUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

import data.*;

/**
 * @author admin
 *
 */
public class BCatalogTreeCellRenderer extends DefaultTreeCellRenderer {

    public BCatalogTreeCellRenderer() {
        
    }

    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        if (leaf && shapeDisplaysImage(value)) {
            setIcon(getIconForNode(value));
        } else {
            
        } 

        return this;
    }

    protected boolean shapeDisplaysImage(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        
        if(obj instanceof BShape) {
            BShape shape = (BShape) obj;
            if(!shape.getImageName().equals("") && shape.getImageName() != null && !shape.getImageName().equals("none")) {
                return true;
            }
        }
        
        if(obj instanceof ResourceModel) {
            ResourceModel resource = (ResourceModel) obj;
            if(resource.getDataType().equals("Image")) {
                return true;
            }
        }
        
        return false;
    }
    
    private Icon getIconForNode(Object value) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        String imageName = "";
        if(node.getUserObject() instanceof BShape) {
            BShape shape = (BShape) node.getUserObject();
            imageName = shape.getImageName();
        } else {
            ResourceModel resource = (ResourceModel) node.getUserObject();
            imageName = resource.getName();
        }
        
        if(imageName.equals("") || imageName.equals("none")) {
            return null;
        }
        
        return new ImageIcon(BEditor.getImage(imageName).getScaledInstance(20, -1, Image.SCALE_FAST));
    }
}
