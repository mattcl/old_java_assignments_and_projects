import java.io.File;
import java.util.*;

/**
 * 
 */

/**
 * Encapsulates a collection of DBRecord instances
 * @author Matthew Chun-Lum
 *
 */
public class DBTable {
    private Collection<DBRecord> table;
    private int numSelectedRecords;
    private boolean needRecomputeSelected;
    
    /**
     * Constructor
     * @param filename the name of the .txt representation of the table
     */
    public DBTable() {
        //table = new ArrayList<DBRecord>(); // old
        table = new ChunkList<DBRecord>(); // new
        numSelectedRecords = 0;
        needRecomputeSelected = true;
    }
    
    /**
     * Reads in a file to the database
     * @param filename the name of the file to read
     * @throws FileNotFoundException
     */
    public void loadTableFromFile(String filename) {
        table.clear();
        needRecomputeSelected = true;
        parseFileIntoTable(filename);
    }
    
    /**
     * Performs the query on the table, setting all matching records to "selected"
     * @param query the query string
     * @param type the <CODE>DBRecord.queryType</CODE> selector for the query (<CODE>AND</CODE> or <CODE>OR</CODE>)
     * @return <CODE>true</CODE> if the query was successful, <CODE>false</CODE> otherwise
     */
    public boolean queryTable(String query, DBRecord.queryType type) {
        query = query.trim();
        if(!validQuery(query)) return false;
        for(DBRecord record : table)
            if(record.queryRecord(query, type)) {
                record.setSelected(true);
                needRecomputeSelected = true;
            }
        return true;
    }
    
    /**
     * 
     * @return the number of records in the table
     */
    public int getNumRecords() {
        return table.size();
    }
    
    /**
     * 
     * @return the number of selected records in the table
     */
    public int getNumSelectedRecords() {
        if(!needRecomputeSelected) return numSelectedRecords;
        int num = 0;
        for(DBRecord record : table) 
            if(record.isSelected())
                num++;
        needRecomputeSelected = false;
        return num;
    }
    
    /**
     * Clears the selected flags for all of the records.
     */
    public void clearSelected() {
        for(DBRecord record : table)
            record.setSelected(false);
    }
    
    /**
     * Removes all of the records matching the passed selected flag
     */
    public void deleteRecordsWithSelectedFlag(boolean flag) {
        Iterator<DBRecord> itr = table.iterator();
        while(itr.hasNext())
            if(itr.next().isSelected() == flag)
                itr.remove();
    }
    
    /**
     * Removes all of the records in the table
     */
    public void deleteAllRecords() {
        table.clear();
    }
    
    /**
     * @return string representation of the table
     */
    public String toString() {
        String db = "";
        for(DBRecord record : table)
            db += (db == "" ? "" : "\n") + record.toString();
        return db;
    }
    
    // ----------- Private -------------- //
    /*
     * parses in the file specified by filename. Throws a runtime
     * exception of the file isn't found
     */
    private void parseFileIntoTable(String filename) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(filename));
            while(scanner.hasNextLine()) {
                table.add(new DBRecord(scanner.nextLine()));
            }
            scanner.close();
        } catch (Exception e){
            throw new RuntimeException(e.toString());
        }
    }
    
    /*
     * validates a query string
     */
    private boolean validQuery(String query) {
        try {
            DBRecord testEecord = new DBRecord(query);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
}
