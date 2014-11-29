package ca.ualberta.cmput301f14t16.easya.Model;

/**
 * Used to enable offline content to be displayed.
 * Design inspired in tuples.
 * @author Cauani
 *
 * @param <X>
 * @param <Y>
 */
public class SimpleObjectTrio<X, Y, Z> {
	public final X value1;
	public final Y value2;
	public final Z value3;
	
	public SimpleObjectTrio(X x, Y y, Z z){
		this.value1 = x;
		this.value2 = y;
		this.value3 = z;
	}
}
