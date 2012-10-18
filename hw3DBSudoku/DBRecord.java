import java.util.*;

/**
 * DBRecord.java -
 */

/**
 * Encapsulates an arbitrary number of <CODE>DBBinding</CODE> objects.
 * @author Matthew Chun-Lum
 */
public class DBRecord {
    public static enum queryType {AND, OR};
    
    Collection<DBBinding> record;
    boolean selected;
    
    /**
     * Constructor
     * @param descriptor the string descriptor for the record (i.e. <CODE>"foo:bar, bar:baz, ..."</CODE>)
     */
    public DBRecord(String descriptor) {
        //record = new ArrayList<DBBinding>(); // old
        record = new ChunkList<DBBinding>();  // new
        selected = false;
        StringTokenizer tokenizer = new StringTokenizer(descriptor, ",");
        while(tokenizer.hasMoreTokens())
            record.add(new DBBinding(tokenizer.nextToken()));
    }
    
    /**
     * Method for determining if a record matches a query based on the passed query
     * type. (<CODE>DBRecord.queryType.AND</CODE> or <CODE>DBRecord.queryType.OR</CODE>)
     * @param query the DBRecord to compare the receiver with
     * @param type the DBRecord.queryType (either <CODE>DBRecord.queryType.AND</CODE> or <CODE>DBRecord.queryType.OR</CODE>)
     * @return <CODE>true</CODE> if the receiver matches the query, <CODE>false</CODE> otherwise.
     */
    public boolean queryRecord(DBRecord query, queryType type) {
        switch(type) {
            case AND:return containsAllBindingsInRecord(query);
            case  OR:return containsAtLeastOneBindingInRecord(query);
            default :throw new RuntimeException("Invalid query type");
        }
    }
    
    /**
     * Method for determining if a record matches a query based on a string representation 
     * of the query and the passed query type. (<CODE>DBRecord.queryType.AND</CODE> or <CODE>DBRecord.queryType.OR</CODE>)
     * <p>
     * Overloaded for convenience.
     * @param query the string representation of the query (i.e. <CODE>"foo:bar"</CODE>).
     * @param type the DBRecord.queryType (either <CODE>DBRecord.queryType.AND</CODE> or <CODE>DBRecord.queryType.OR</CODE>)
     * @return <CODE>true</CODE> if the receiver matches the query, <CODE>false</CODE> otherwise.
     */
    public boolean queryRecord(String query, queryType type) {
        return queryRecord(new DBRecord(query), type);
    }
    
    /**
     * Determines if the record contains a certain binding
     * @param binding the binding of interest
     * @return <CODE>true</CODE> if the record contains the binding, <CODE>false</CODE> otherwise
     */
    public boolean containsBinding(DBBinding binding) {
        return record.contains(binding);
    }
    
    /**
     * Determines if the receiving record contains all of the bindings in the passed
     * record.
     * @param query the DBRecord with the bindings of interest
     * @return <CODE>true</CODE> if the receiver contains all of the bindings
     *  in the query record, <CODE>false</CODE> otherwise
     */
    public boolean containsAllBindingsInRecord(DBRecord query) {
        if(query.getNumBindings() == 0) return true;
        return record.containsAll(query.getBindings());
    }
    
    /**
     * Determines if the receiving record contains at least one of the bindings in
     * the passed record
     * @param query the DBRecord with the bindings of interest
     * @return <CODE>true</CODE> if the receiver contains at least one binding
     * in the passed record, <CODE>false</CODE> otherwise.
     */
    public boolean containsAtLeastOneBindingInRecord(DBRecord query) {
        if(query.getNumBindings() == 0) return true;
        for(DBBinding binding : query.getBindings())
            if(containsBinding(binding)) return true;
        return false;
    }
    
    /**
     * Gets the bindings contained in the record
     * @return the pointer to the internal collection of bindings
     */
    public Collection<DBBinding> getBindings() {
        return record;
    }
    
    /**
     * Convenience method for determining the number of bindings contained in a record.
     * @return the number of bindings contained in the record
     */
    public int getNumBindings() {
        return record.size();
    }
    
    /**
     * 
     * @return true if the record is selected
     */
    public boolean isSelected() {
        return selected;
    }
    
    /**
     * Sets the "selected" flag for the record
     * @param value the new value for the "selected" flag
     */
    public void setSelected(boolean value) {
        selected = value;
    }
    
    /**
     * @return the string representation of the record
     */
    public String toString() {
        String representation = (selected ? "*" : "");
        if(record.isEmpty()) return representation + "Record is empty";
        Iterator<DBBinding> itr = record.iterator();
        while(true) {
            representation += itr.next().toString();
            if(!itr.hasNext()) break;
            representation += ", ";
        }
        return representation;
    }
}
