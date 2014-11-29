package ca.ualberta.cmput301f14t16.easya.Model.Data;

import android.app.Application;
import android.content.Context;

/**
 * A global way to access the {@link Context} of the application.
 */
public class ContextProvider extends Application {
	private static Context ctx;

	/**
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		ctx = getApplicationContext();
	}

	/**
	 * @return The current Context of the application.
	 */
	public static Context get() {
		return ctx;
	}
}
