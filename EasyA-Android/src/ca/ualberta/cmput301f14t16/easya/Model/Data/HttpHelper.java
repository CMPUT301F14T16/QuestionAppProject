/**
 * This file references:
 * https://github.com/rayzhangcl/ESDemo/blob/master/ESDemo/src/ca/ualberta/cs/CMPUT301/chenlei/ESClient.java
 * On Oct 23, 2014
 */

package ca.ualberta.cmput301f14t16.easya.Model.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.util.Log;

public class HttpHelper {
	
	private static final String DEBUG_TAG = "HttpHelper";
	
	// This method referenced from http://developer.android.com/training/basics/network-ops/connecting.html On Oct 23, 2014
	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	public static String getFromUrl(String myurl) throws IOException {
	    InputStream is = null;
	    try {
	        URL url = new URL(myurl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(5000 /* milliseconds */);
	        conn.setConnectTimeout(3000 /* milliseconds */);
	        conn.setRequestMethod("GET");
	        conn.setDoInput(true);
	        // Starts the query
	        conn.connect();
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readResponse(is);
	        Log.d(DEBUG_TAG, contentAsString);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	// http://developer.android.com/reference/java/net/HttpURLConnection.html On Oct 23, 2014
	public static String putToUrl(String myurl, String jsonData) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		HttpURLConnection conn = null;
		try {
	        URL url = new URL(myurl);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(5000 /* milliseconds */);
	        conn.setConnectTimeout(3000 /* milliseconds */);
	        conn.setRequestMethod("POST");
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setRequestProperty("Content-Type","application/json");
	        conn.connect();
	        
	        // Send request
	        // http://stackoverflow.com/questions/20020902/android-httpurlconnection-how-to-set-post-data-in-http-body On Oct 23, 2014
	        byte[] outputInBytes = jsonData.getBytes("UTF-8");
	        os = conn.getOutputStream();
	        os.write(outputInBytes);
	        os.close();
	     
	        // Get Response
	        int response = conn.getResponseCode();
	        Log.d(DEBUG_TAG, "The response is for post is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readResponse(is);
	        Log.d(DEBUG_TAG, contentAsString);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	        if (os != null) {
	        	os.close();
	        }
	        if (conn != null) {
	        	conn.disconnect();
	        }
	    }
	}
	
	// This method from http://stackoverflow.com/questions/11766878/sending-files-using-post-with-httpurlconnection on Oct 23, 2014, User Mihai Todor
	private static String readResponse(InputStream stream) throws IOException, UnsupportedEncodingException {
		BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(stream));
		String line = "";
		StringBuilder stringBuilder = new StringBuilder();
		while ((line = responseStreamReader.readLine()) != null)
		{
		    stringBuilder.append(line).append("\n");
		}
		responseStreamReader.close();

		String response = stringBuilder.toString();
		return response;
	}
}
