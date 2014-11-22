package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.Date;

import android.os.SystemClock;

public final class Time {
	public static Date getDate(){
		SntpClient client = new SntpClient();
		 if (client.requestTime("pool.ntp.org", 3000)) {
		     long now = client.getNtpTime() + SystemClock.elapsedRealtime() - client.getNtpTimeReference();
		     return new Date(now);
		 }
		 return null;
	}
}
