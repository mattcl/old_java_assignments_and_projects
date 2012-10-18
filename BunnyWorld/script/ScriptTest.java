package script;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.BDataModel;
import data.BShape;


/**
 * 
 */

/**
 * @author Home Account
 *
 */
public class ScriptTest {
	private BDataModel data;
	private Script script;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		data = new BDataModel();
		script = new Script(data);
	}

	/**
	 * Test method for {@link Script#isValid(java.lang.String)}.
	 */
	@Test
	public void testIsValid() {
		assertTrue(Script.isValid("on click goto sam"));
		assertFalse(Script.isValid("on click beep sam"));
		assertFalse(Script.isValid("click goto sam"));
		assertFalse(Script.isValid("on goto sam"));
		assertFalse(Script.isValid("goto sam beep beep show sam"));
		assertTrue(Script.isValid("on click goto sam beep beep beep hide sam show sam"));
		assertTrue(Script.isValid("on enter beep play sam hide sam goto sam beep beep goto sam"));
		assertFalse(Script.isValid("on drop hide sam hide sam goto sam play sam show sam show sam"));
		BShape shape = new BShape();
		shape.setName("dog1");
		data.addShape(shape.getModel());
		assertTrue(Script.isValid("on drop dog1 hide sam"));
	}

	/**
	 * Test method for {@link Script#extractTrigger(java.lang.String)}.
	 */
	@Test
	public void testExtractTrigger() {
		assertEquals(Script.CLICK, Script.extractTrigger("on click goto sam beep beep beep hide sam show sam"));
		assertEquals(Script.ENTER, Script.extractTrigger("on enter beep play sam hide sam goto sam beep beep goto sam"));
		assertEquals(Script.DROP, Script.extractTrigger("on drop hide sam hide sam goto sam play sam show sam show sam"));
	}

}
