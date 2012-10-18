import junit.framework.TestCase;


public class DBRecordTest extends TestCase {
    private DBRecord record;
    private String record_descriptor;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        record_descriptor = "name:Alien, stars: Yaphet Kotto, stars:Sigourney Weaver, stars: Harry Dean Stanton";
        record = new DBRecord(record_descriptor);
    }
    
    public void testEasyRecordCreation() {
        assertEquals("name:Alien, stars:Yaphet Kotto, stars:Sigourney Weaver, stars:Harry Dean Stanton", record.toString());
    }
    
    public void testEmptyRecordCreation() {
        DBRecord empty = new DBRecord("");
    }
    
    public void testRecordContains() {
        assertTrue(record.containsBinding(new DBBinding("name", "Alien")));
        assertTrue(record.containsBinding(new DBBinding("name", "alien")));
        assertTrue(record.containsBinding(new DBBinding("name", "lie")));
        assertTrue(record.containsBinding(new DBBinding("stars", "Sigourney Weaver")));
        
        assertFalse(record.containsBinding(new DBBinding("name", "Aliens")));
        assertFalse(record.containsBinding(new DBBinding("names", "Alien")));
    }
    
    public void testRecordContainsAllBindings() {
        DBRecord query = new DBRecord("name:Alien, stars: Yaphet Kotto, stars:Sigourney Weaver, stars: Harry Dean Stanton");
        assertTrue(record.containsAllBindingsInRecord(query));
        assertTrue(record.queryRecord(query, DBRecord.queryType.AND));
        assertTrue(record.queryRecord("name:Alien, stars: Yaphet Kotto, stars:Sigourney Weaver, stars: Harry Dean Stanton", DBRecord.queryType.AND));
        
        query = new DBRecord("name:Ali, stars: Yaphet, stars:Weaver, stars:Dean");
        assertTrue(record.containsAllBindingsInRecord(query));
        assertFalse(query.containsAllBindingsInRecord(record));
        
        query = new DBRecord("name:Alien, stars: Yaphet Kotto, stars:Sigourney Weaver, stars: Harry Dean Stanton, stars:someone else");
        assertFalse(record.containsAllBindingsInRecord(query));
        assertTrue(query.containsAllBindingsInRecord(record));
    }
    
    public void testRecordContainsAtLeastOneBindingInRecord() {
        DBRecord query = new DBRecord("name:Alien, stars: Yaphet Kotto, stars:Sigourney Weaver, stars: Harry Dean Stanton");
        assertTrue(record.containsAtLeastOneBindingInRecord(query));
        
        query = new DBRecord("stars: Yaphet Kotto, stars:someone else");
        assertTrue(record.containsAtLeastOneBindingInRecord(query));
        assertTrue(query.containsAtLeastOneBindingInRecord(record));
        assertTrue(record.queryRecord(query, DBRecord.queryType.OR));
        assertTrue(record.queryRecord("stars: Yaphet Kotto, stars:someone else", DBRecord.queryType.OR));
        
        query = new DBRecord("name: What, stars:someone else");
        assertFalse(record.containsAtLeastOneBindingInRecord(query));
        assertFalse(query.containsAtLeastOneBindingInRecord(record));
    }
    
    public void testRecordSelectedFlag() {
        assertFalse(record.isSelected());
        record.setSelected(true);
        assertTrue(record.isSelected());
        record.setSelected(false);
        assertFalse(record.isSelected());
    }
}
