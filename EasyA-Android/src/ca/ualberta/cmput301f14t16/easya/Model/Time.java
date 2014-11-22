package ca.ualberta.cmput301f14t16.easya.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.os.SystemClock;

public final class Time {
	public static final Calendar getDate(){
		SntpClient client = new SntpClient();
		 if (client.requestTime("pool.ntp.org", 3000)) {
		     long now = client.getNtpTime() + SystemClock.elapsedRealtime() - client.getNtpTimeReference();
		     Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("GMT-0"));		     
		     cl.setTimeInMillis(now);
		     return cl;
		 }
		 return null;
	}
	
	private static String timezone = "";
	
	public static final String getTimezone(){
		if (timezone.equals("")){
		    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
		    String timeZone = new SimpleDateFormat("Z").format(calendar.getTime());
		    timezone = "GMT" + (timeZone.substring(0, 3) + ":"+ timeZone.substring(3, 5));
		}
		return timezone;
	}
}
