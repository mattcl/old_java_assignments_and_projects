import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.awt.*;
/**
 * 
 */

/**
 * @author admin
 *
 */
public class WhiteboardTableModel extends AbstractTableModel implements ModelListener {
    public static final String[] DEFAULT_COLUMNS = {"X", "Y", "Width", "Height", "ID"};
    
    private ArrayList<DShapeModel> models;
   
    
    /**
     * 
     */
    public WhiteboardTableModel() {
        super();
        models = new ArrayList<DShapeModel>();
    }
    
    /**
     * Adds the specified model
     * @param model
     */
    public void addModel(DShapeModel model) {
        models.add(0, model);
        model.addListener(this);
        fireTableDataChanged();
    }

    /**
     * Removes the specified model
     */
    public void removeModel(DShapeModel model) {
        model.removeListener(this);
        models.remove(model);
        fireTableDataChanged();
    }
    
    /**
     * Moves the specified model to the last position in the table
     * @param model
     */
    public void moveModelToBack(DShapeModel model) {
        if(!models.isEmpty() && models.remove(model))
            models.add(model);
        fireTableDataChanged();
    }
    
    /**
     * Moves the specified model to the first position in the table
     * @param model
     */
    public void moveModelToFront(DShapeModel model) {
        if(!models.isEmpty() && models.remove(model))
            models.add(0, model);
        fireTableDataChanged();
    }
    
    public void clear() {
        models.clear();
        fireTableDataChanged();
    }
    
    /**
     * Returns the row index for a given model
     * @param model
     * @return the row  index
     */
    public int getRowForModel(DShapeModel model) {
        return models.indexOf(model);
    }
    
    /**
     * Custom column names
     */
    @Override
    public String getColumnName(int col) {
        return DEFAULT_COLUMNS[col];
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return DEFAULT_COLUMNS.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return models.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rectangle bounds = models.get(rowIndex).getBounds();
        switch(columnIndex) {
            case 0:  return bounds.x;
            case 1:  return bounds.y;
            case 2:  return bounds.width;
            case 3:  return bounds.height;
            case 4:  return models.get(rowIndex).getID();
            default: return null;
        }
    }

    /**
     * Make sure none of the cells are editable
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    /* (non-Javadoc)
     * @see ModelListener#modelChanged(DShapeModel)
     */
    public void modelChanged(DShapeModel model) {
        int index = models.indexOf(model);
        fireTableRowsUpdated(index, index);
    }

}
