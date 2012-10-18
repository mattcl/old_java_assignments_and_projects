/**
 * 
 */
package bGUI;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import data.*;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.util.*;

/**
 * @author Matthew Chun-Lum
 *
 */
public class BEditorCatalog extends JPanel {
    private JTree tree;
    private BEditor editor;
    
    private DefaultTreeModel treeModel;
    
    private DefaultMutableTreeNode resources;
    
    private DefaultMutableTreeNode lastNodeSelected;
    
    boolean skip;
    
    /**
     * Constructor, takes in the editor to which this catalog belongs to
     * @param editor
     */
    public BEditorCatalog(BEditor editor) {
        this.editor = editor;
        
        skip = false;
        
        treeModel = new DefaultTreeModel(null);
        tree = new JTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                //handleSelectionEvent();
            }       
        });
        
        tree.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                handleSelectionEvent();
            }
        });
        
        tree.setCellRenderer(new BCatalogTreeCellRenderer());
        
        JScrollPane treeView = new JScrollPane(tree);
        treeView.setPreferredSize(new Dimension(200, 500));
        
        add(treeView);
        setBorder(BorderFactory.createTitledBorder("Catalog"));
    }
    
    /**
     * Loads the tree from the passed dataModel
     * @param dataModel
     */
    public void loadFromDataModel(BDataModel dataModel) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Document");
        
        resources = new DefaultMutableTreeNode("Resources");
        
        
        
        for(ResourceModel resourceModel : dataModel.getResourcesMap().values()) {
            DefaultMutableTreeNode resource = new DefaultMutableTreeNode(resourceModel);
            resources.add(resource);
        }
        
        for(BPageModel pageModel : dataModel.getPageModels().values()) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(pageModel);
            for(BShape shape : pageModel.getShapes()) {
                child.add(new DefaultMutableTreeNode(shape));
            }
            //root.add(child);
            root.insert(child, 0);
        }
        
        root.insert(resources, 0);
        
        treeModel.setRoot(root);
    }
    
    /**
     * Adds a new page to the current tree
     * @param pageModel
     */
    public void addNewPage(BPageModel pageModel) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if(root != null) {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(pageModel);
            treeModel.insertNodeInto(child, root, treeModel.getChildCount(root));
            tree.scrollPathToVisible(new TreePath(child.getPath()));
            setSelected(child);
        }
    }

    /**
     * Removes the page from the tree
     * @param pageModel
     */
    public void removePage(BPageModel pageModel) {
        DefaultMutableTreeNode node = getNodeForObject(pageModel);
        if(node != null) {
            treeModel.removeNodeFromParent(node);
            clearSelection();
        }
    }
    
    public BPageModel getNextPage(BPageModel currentPage) {
        DefaultMutableTreeNode node = getNodeForObject(currentPage);
        if(node == null) return null;
        DefaultMutableTreeNode next = node.getNextSibling();
        if(next != null) return (BPageModel) next.getUserObject();
        return null;
    }
    
    public BPageModel getPreviousPage(BPageModel currentPage) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        Enumeration<DefaultMutableTreeNode> children = (Enumeration<DefaultMutableTreeNode>) root.children();
        children.nextElement(); // get rid of resources
        DefaultMutableTreeNode prev = null;
        while(children.hasMoreElements()) {
            DefaultMutableTreeNode temp = children.nextElement();
            if(temp.getUserObject() == currentPage)
                if(prev == null)
                    return null;
                else
                    return (BPageModel) prev.getUserObject();
            
            prev = temp;
        }
        return null;
    
    }

    public void pageChanged(BPageModel pageModel) {
        DefaultMutableTreeNode node = getNodeForObject(pageModel);
        if(node != null) {
            treeModel.nodeChanged(node); 
            setSelected(node);
        }
    }
    
    /**
     * Adds a new shape to the current tree with the passed parent
     * @param shape
     * @param parent
     */
    public void addNewShape(BShape shape, BPageModel parent) {
        DefaultMutableTreeNode node = getNodeForObject(parent);
        if(node != null) { 
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(shape);
            treeModel.insertNodeInto(child, node, node.getChildCount());
            tree.scrollPathToVisible(new TreePath(child.getPath()));
            skip = true;
            setSelected(child);
        }
    }
    
    /**
     * Removes a shape from the current tree
     * @param shape
     */
    public void removeShape(BShape shape) {
        DefaultMutableTreeNode node = getNodeForObject(shape);
        if(node != null) {
            treeModel.removeNodeFromParent(node);
            clearSelection();
        }
    }
    
    /**
     * Instructs the tree to redraw on change
     * @param shape
     */
    public void shapeChanged(BShape shape) {
        DefaultMutableTreeNode node = getNodeForObject(shape);
        if(node != null) {
            treeModel.nodeChanged(node); 
            setSelected(node);
        }
    }
    
    public void addNewResource(ResourceModel resource) {
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(resource); 
        treeModel.insertNodeInto(child, resources, resources.getChildCount());
        tree.scrollPathToVisible(new TreePath(child.getPath()));
        setSelected(child);
    }
    
    public void removeResource(ResourceModel resource) {
        DefaultMutableTreeNode node = getNodeForObject(resource);
        if(node != null) {
            treeModel.removeNodeFromParent(node);
            clearSelection();
        }
    }
    
    public void setSelectedObject(Object obj, boolean flag) {
        skip = true;
        setSelectedObject(obj);
    }
    
    public void setSelectedObject(Object obj) {
        if(obj == null) {
            tree.clearSelection();
            return;
        }
        setSelected(getNodeForObject(obj));
    }
    
    public void setSelected(DefaultMutableTreeNode node) {
        if(node != null) {
            tree.setSelectionRow(tree.getRowForPath(new TreePath(node.getPath())));
            lastNodeSelected = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        }
    }
    
    public void clearSelection() {
        tree.clearSelection();
    }
    
    //------------- Private ------------ //
    
    private DefaultMutableTreeNode getNodeForObject(Object obj) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        if(root != null) {
            Enumeration<DefaultMutableTreeNode> enumeration = root.breadthFirstEnumeration();
            while(enumeration.hasMoreElements()) {
                DefaultMutableTreeNode node = enumeration.nextElement();
                if(node.getUserObject() == obj) {
                    return node;
                }
            }
        }
        return null;
    }
    
    /**
     * Handles selection of a node in the tree
     */
    private void handleSelectionEvent() {
        if(skip) {
            skip = false;
            return;
        }
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        
        if(node == null || node.equals(lastNodeSelected))
            return;
        
        lastNodeSelected = node;
        Object nodeObject = node.getUserObject();
        
        //Object nodeObject = e.getSource();
        
        if(nodeObject instanceof BPageModel) {
            editor.changePage((BPageModel) nodeObject);
        } else if (nodeObject instanceof BShape) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
            editor.changePage((BPageModel) parent.getUserObject());
            editor.changeSelectedShape((BShape) nodeObject);
        } else if (nodeObject instanceof ResourceModel) {
            editor.showPropertiesForResource(((ResourceModel) nodeObject).getName());
        }
    }
}