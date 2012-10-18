/**
 * EnemyDataModel.java
 */
package rampancy_old.data;

import java.util.*;
import java.io.Serializable;

import rampancy_old.statistics.SuperNode;

/**
 * @author Matthew Chun-Lum
 *
 */
public class EnemyDataModel implements Serializable {

    public int version;
    public String enemyName;
    public TreeNode rootNode;
    
    public EnemyDataModel(String name, SuperNode[] nodes, int version) {
        enemyName = name;
        this.version = version;
        rootNode = new TreeNode();
        rootNode.index = -1;
        generateTree(nodes);
    }
    
    // ----------- Private Helpers ----------- //
    
    /*
     * Generates the tree from a an array of SuperNodes
     */
    private void generateTree(SuperNode[] nodes) {
        for(int i = 0; i < nodes.length; i++) {
            SuperNode node = nodes[i];
            updateNodes(rootNode, 0, node.path, node.value);
        }
    }
    
    private void updateNodes(TreeNode node, int depth, byte[] path, double value) {
        if(depth == path.length) {
            node.nodeValue = value;
            return;
        } 
        
        TreeNode next = node.getChildForIndex(path[depth]);
        if(next == null)
            next = node.addChild(path[depth]);
        
        updateNodes(next, depth + 1, path, value);
    }
}
