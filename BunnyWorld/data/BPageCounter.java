package data;


public class BPageCounter extends BCounter {

	public BPageCounter() {
		super();
	}
	
	/**
	 * Returns a created page name
	 * @return
	 */
	public String getPageName() {
		String pageName = "page" + count;
		count++;
		return(pageName);
	}
}
