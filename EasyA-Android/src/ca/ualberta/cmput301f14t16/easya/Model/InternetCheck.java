package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ca.ualberta.cmput301f14t16.easya.Model.Data.ContextProvider;

public final class InternetCheck {
	/**
	 * True if an active Internet has been found.
	 */
	private static boolean internetStatus;
	/**
	 * The thread will not attempt to check for an active Internet connection
	 * unless at least this amount of time has passed since the last check.
	 */
	private final static int CHECK_FOR_INTERNET = 300000; // in milliseconds 5 minutes
	/**
	 * The time at which the last check for an active Internet connection was
	 * made.
	 */
	private static Date lastCheck;
	
	/**
	 * @return True if an active Internet connection is found.
	 */
	private static final boolean checkInternet(){
		try {
			ConnectivityManager cm = (ConnectivityManager) ContextProvider
					.get().getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
			return activeNetwork != null
					&& activeNetwork.isConnectedOrConnecting();
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Will call {@link InternetCheck#haveInternet()} as though enough time
	 * has passed for it to check the connection, regardless of whether or not
	 * enough time has actually elapsed.
	 * 
	 * @return The result of {@link InternetCheck#internetStatus}
	 */
	public static final boolean forceCheckForInternet(){
		lastCheck = null;
		return haveInternet();
	}
	
	/**
	 * Will call {@link InternetCheck#checkInternet()} if enough time has passed
	 * since the last check. It will assign the results to
	 * {@link InternetCheck#internetStatus}.
	 * 
	 * @return {@link InternetCheck#internetStatus} after the method has completed its
	 *         function.
	 */
	public static final boolean haveInternet(){
		boolean auxTimer = (lastCheck != null && ((new Date()).getTime() - lastCheck.getTime()) >= CHECK_FOR_INTERNET);
		if (lastCheck == null || auxTimer) {
			lastCheck = new Date();
			internetStatus = checkInternet();
			if (auxTimer && internetStatus)
				MainModel.getInstance().notifyViews();
		}
		return internetStatus;
	}
}
