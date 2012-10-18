/**
 * DBBinding.java - encapsulates a string key and string value
 */

/**
 * Encapsulates a string key and string value.
 * @author Matthew Chun-Lum
 */
public class DBBinding {
    String key;
    String value;

    /**
     * Constructor
     * @param key the key for the Binding's value. This method assumes the key and value
     * are in their final forms.
     * @param value the Binding's value
     */
    public DBBinding(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     * Constructor
     * @param string_descriptor the string that describes the binding (i.e <CODE>"foo:bar"</CODE>)
     * @throws runtime exception if the passed descriptor is invalid
     */
    public DBBinding(String string_descriptor) {
        String[] descriptor = string_descriptor.split(":");
        if(descriptor.length != 2) throw new RuntimeException("Invalid string descriptor");
        key = removeLeadingAndTrailingSpaces(descriptor[0]);
        value = removeLeadingAndTrailingSpaces(descriptor[1]);
    }
    
    /**
     * Copy constructor
     * @param binding the DBBinding instance to copy
     */
    public DBBinding(DBBinding binding) {
        key = binding.getKey();
        value = binding.getValue();
    }
    
    /**
     * Convenience method
     * @return the binding's key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Convenience method
     * @return the binding's value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * Compares two binding objects. Two bindings are considered equal if 
     * the keys are exactly the same, and the value of the receiver is equal
     * to (ignoring case) or contained in the value of the passed binding.
     * <p>
     * Example:
     * <p>
     *  <CODE>binding1</CODE> has key <CODE>"foo"</CODE> and value <CODE>"bar"</CODE>.<br>
     *  <CODE>binding2</CODE> has key <CODE>"foo"</CODE> and value <CODE>"bars"</CODE>.<br>
     * <p>
     *  <CODE>binding1.equals(binding2)</CODE> returns <CODE>true</CODE><br>
     *  <CODE>binding2.equals(binding1)</CODE> returns <CODE>false</CODE>
     * 
     * @param obj the binding to be compared with the receiver
     * @return <CODE>true</CODE> if the receiving bidning's key exactly matches the
     * key of the passed binding and if the value of the receiving object is equal to
     * (ignoring case) or contained within the value of the passed binding. Returns 
     * <CODE>false</CODE> otherwise.
     */
    @Override
    public final boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(super.equals(obj)) return true;
        if(!obj.getClass().equals(this.getClass())) return false;
        DBBinding bind = (DBBinding) obj;
        if(!bind.getKey().equals(getKey())) return false;
        if(bind.getValue().equalsIgnoreCase(getValue())) return true;
        return (bind.getValue().toLowerCase().contains(getValue().toLowerCase()));
    }

    /**
     * @return the string representation of the binding (i.e "foo:bar")
     */
    public String toString() {
        return key + ":" +value;
    }
    
    // ------------- Private --------------- //
    private String removeLeadingAndTrailingSpaces(String str) {
        str = str.trim();
        for(int i = 0; i < str.length(); i++)
            if(!Character.isWhitespace(str.charAt(i))) {
                str = str.substring(i);
                break;
            }
        return str;
    }
    
}
