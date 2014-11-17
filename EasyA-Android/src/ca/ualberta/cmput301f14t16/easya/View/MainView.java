package ca.ualberta.cmput301f14t16.easya.View;

public interface MainView<M> {
	public void update();
	public void update(M lst);
	
	public void stopAnimateSync();
}
