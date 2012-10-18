import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import java.util.*;
/**
 * 
 */

/**
 * @author admin
 *
 */
public class CrackerTableModel extends DefaultTableModel {
    public static int[] columnWidths = {120, 120, 300, 110};
    private static String[] columnNames = {"Username", "Password", "Hash", "Time"};
    
    public static int defaultNumRows = 20;
    
    private ArrayList<User> users;
    
    private boolean editable;
    
    /**
     * 
     */
    public CrackerTableModel() {
        super();
        users = new ArrayList<User>();
        editable = true;
    }

    public void setEditable(boolean val) {
        editable = val;
    }
    
    public void setPassword(String password, String hash, long time) {
        for(User user : users) {
            if(user.getHash().equals(hash)) {
                user.setPassword(password);
                user.setTimeForCrack(time);
                this.fireTableDataChanged();
                break;
            }
        }
    }
    
    public void addRow(User user) {
        users.add(user);
        this.fireTableDataChanged();
    }


    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        if(users == null) return 0;
        return users.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        if(user == null) return null;
        switch(columnIndex) {
            case 0: return user.getUsername();
            case 1: return user.getPassword();
            case 2: return user.getHash();
            case 3: return (user.isCracked() ? user.getTimeForCrack() : "");
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return editable;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if(rowIndex >= users.size()) {
            users.add(new User());
        }
        User user = users.get(rowIndex);
        switch(columnIndex) {
            case 0: 
                user.setUsername((String) value);
                break;
            case 1: 
                user.setPassword((String) value);
                break;
            case 2: 
                user.setHash((String) value);
                break;
            case 3: 
                user.setTimeForCrack((Long) value);
                break;
        }
    } 
    
    

}
