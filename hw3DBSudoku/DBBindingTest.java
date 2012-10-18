import junit.framework.TestCase;


public class DBBindingTest extends TestCase {
    DBBinding binding;
    
    public void testEasyBinding() {
        binding = new DBBinding("foo", "bar");
        assertEquals("foo", binding.getKey());
        assertEquals("bar", binding.getValue());
        assertEquals("foo:bar", binding.toString());
    }
    
    public void testMediumBinding() {
        binding = new DBBinding("foo:bar");
        assertEquals("foo", binding.getKey());
        assertEquals("bar", binding.getValue());
        assertEquals("foo:bar", binding.toString());
    }
    
    public void testHardBinding() {
        binding = new DBBinding(" foo :  bar  bar");
        assertEquals("foo", binding.getKey());
        assertEquals("bar  bar", binding.getValue());
        assertEquals("foo:bar  bar", binding.toString());
    }
    
    public void testRidiculousBinding() {
        binding = new DBBinding(" foo bar 220     :  bar  bar foo again     ");
        assertEquals("foo bar 220", binding.getKey());
        assertEquals("bar  bar foo again", binding.getValue());
        assertEquals("foo bar 220:bar  bar foo again", binding.toString());
    }
    
    public void testEquals() {
        binding = new DBBinding("foo", "bar");
        DBBinding binding1 = new DBBinding("foo", "bar");
        DBBinding binding2 = new DBBinding("foo", "bars");
        DBBinding binding3 = new DBBinding("foo", "BARS");
        DBBinding binding4 = new DBBinding("FOO", "bar");
        DBBinding binding5 = new DBBinding("foo", "baz");
        DBBinding binding6 = new DBBinding("bar", "baz");
        
        assertTrue(binding.equals(binding));
        assertTrue(binding.equals(binding1));
        assertTrue(binding1.equals(binding));
        assertTrue(binding.equals(binding2));
        assertTrue(binding.equals(binding3));
        
        assertFalse(binding2.equals(binding));
        assertFalse(binding3.equals(binding));
        assertFalse(binding.equals(binding4));
        assertFalse(binding4.equals(binding));
        assertFalse(binding.equals(binding5));
        assertFalse(binding5.equals(binding));
        assertFalse(binding.equals(binding6));
        assertFalse(binding6.equals(binding));
    }
}
