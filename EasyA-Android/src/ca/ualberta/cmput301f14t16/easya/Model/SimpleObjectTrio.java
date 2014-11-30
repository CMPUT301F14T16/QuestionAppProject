package ca.ualberta.cmput301f14t16.easya.Model;

/**
 * Used in displaying offline content. Inspired by tuple data structures, this
 * class imply stores three objects as a single object.
 * 
 * @author Cauani
 *
 * @param <X>
 * @param <Y>
 */
public class SimpleObjectTrio<X, Y, Z> {
	public final X value1;
	public final Y value2;
	public final Z value3;

	/**
	 * Creates a new SimpleObjectTrio containing the provided objects.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public SimpleObjectTrio(X x, Y y, Z z) {
		this.value1 = x;
		this.value2 = y;
		this.value3 = z;
	}
}
