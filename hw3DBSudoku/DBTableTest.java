import junit.framework.TestCase;


public class DBTableTest extends TestCase {
    private DBTable db;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        db = new DBTable();
    }
    
    public void testTableConstruction() {
        assertEquals(0, db.getNumRecords());
        assertEquals(0, db.getNumSelectedRecords());
    }
    
    public void testReadFile() {
        db.loadTableFromFile("movies.txt");
        assertEquals(5, db.getNumRecords());
        assertEquals(0, db.getNumSelectedRecords());
    }
    
    public void testFindAnd() {
        db.loadTableFromFile("movies.txt");
        db.queryTable("stars: Yaphet Kotto, stars: Harry Dean Stanton", DBRecord.queryType.AND);
        assertEquals(1, db.getNumSelectedRecords());
        
    }
    
    public void testFindOr() {
        db.loadTableFromFile("movies.txt");
        db.queryTable("stars: Yaphet Kotto, stars: Harry Dean Stanton", DBRecord.queryType.OR);
        assertEquals(3, db.getNumSelectedRecords());
    }
    
    public void testNotFound() {
        
    }
    
    public void testInvalidFind() {
        db.loadTableFromFile("movies.txt");
        db.queryTable("ajdfklaj ,,,,,,,::,,,,,", DBRecord.queryType.AND);
        assertEquals(0, db.getNumSelectedRecords());
    }
}
