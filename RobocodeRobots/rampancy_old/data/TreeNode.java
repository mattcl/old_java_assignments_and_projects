/**
 * TreeNode.java
 */
package rampancy_old.data;

import java.io.Serializable;

/**
 * This represents a node in the tree for an exported data model
 * @author Matthew Chun-Lum
 *
 */
public class TreeNode implements Serializable {
    public byte index;
    public TreeNode[] children;
    public double nodeValue;
    
    public TreeNode() {
        nodeValue = -1.0;
    }
    
    /**
     * @return {@code true} if this TreeNode is a leaf
     */
    public boolean isLeaf() {
        return nodeValue > 0;
    }
    
    /**
     * @param index
     * @return the TreeNode specified by the index if it exists
     */
    public TreeNode getChildForIndex(byte index) {
        if(children == null)
            return null;
        
        for(int i = 0; i < children.length; i++)
            if(children[i].index == index)
                return children[i];
        
        return null;
    }
    
    /**
     * Adds a new child node for the specified index
     * @param index
     * @return the newly created child
     */
    public TreeNode addChild(byte index) {
        TreeNode[] temp = children;
        if(temp != null) {
            children = new TreeNode[temp.length];
            for(int i = 0; i < temp.length; i++)
                children[i] = temp[i];
        } else {
            children = new TreeNode[1];
        }
        TreeNode node = new TreeNode();
        node.index = index;
        children[children.length - 1] = node;
        return node;
    }
}
