package ca.ualberta.cmput301f14t16.easya.View;

/**
 * Simple Interface dictating basic aspects of views in this project.
 *
 * @param <M>
 */
public interface MainView<M> {
	public void update();

	public void update(M lst);

	public void stopAnimateSync();
}
