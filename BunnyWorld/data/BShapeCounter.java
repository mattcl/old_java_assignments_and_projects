package data;


public class BShapeCounter extends BCounter {

	public BShapeCounter() {
		super();
	}
	
	/**
	 * Returns a created shape name
	 * @return
	 */
	public String getShapeName() {
		String shapeName = "shape" + count;
		count++;
		return(shapeName);
	}
}
