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

public class HttpHelper {
	
	//private static final String DEBUG_TAG = "HttpHelper";
	
	/**
	 * Submits a GET request to a given URL. 
	 * Given a URL, establishes an HttpUrlConnection and retrieves
	 * the web page content as a InputStream, which it returns as 
	 * a string.
	 * 
	 * Reference: <a href="http://developer.android.com/training/basics/network-ops/connecting.html">http://developer.android.com/training/basics/network-ops/connecting.html</a> On Oct 23, 2014
	 * 
	 * @param myurl			The url to submit the get request to.
	 * @return				The response as a string.
	 * @throws IOException	Exception thrown on any failure to read from url.
	 */
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
	        //int response = conn.getResponseCode();
	        
	        // Log.d(DEBUG_TAG, "The response is: " + response);
	        
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readResponse(is);
	        // Log.d(DEBUG_TAG, contentAsString);
	        return contentAsString;
	        
	    // Makes sure that the InputStream is closed after the app is
	    // finished using it.
	    } finally {
	        if (is != null) {
	            is.close();
	        } 
	    }
	}
	
	/**
	 * Submits a PUT request to a given url and writes the given data through the request.
	 * 
	 * Reference: <a href="http://developer.android.com/reference/java/net/HttpURLConnection.html">http://developer.android.com/reference/java/net/HttpURLConnection.html</a> On Oct 23, 2014
	 * 
	 * @param myurl			The url to submit to.
	 * @param jsonData		The data to submit to the url via POST request.
	 * @return				The response as a string.
	 * @throws IOException	Expception thrown on any failure to write to url.
	 */
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
	        //int response = conn.getResponseCode();
	        // Log.d(DEBUG_TAG, "The response is for post is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readResponse(is);
	        // Log.d(DEBUG_TAG, contentAsString);
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
	
	/**
	 * Submits a GET request to a given url passing in additional data with the request.
	 * 
	 * @param myurl			The url to submit to.
	 * @param jsonData		The data to pass along with the GET request.
	 * @return				The response as a string.
	 * @throws IOException	Exception thrown on any failure to read from or write to url.
	 */
	public static String getFromUrlWithData(String myurl, String jsonData) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		HttpURLConnection conn = null;
		try {
	        URL url = new URL(myurl);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(5000 /* milliseconds */);
	        conn.setConnectTimeout(3000 /* milliseconds */);
	        conn.setRequestMethod("GET");
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
	        //int response = conn.getResponseCode();
	        // Log.d(DEBUG_TAG, "The response is for post is: " + response);
	        is = conn.getInputStream();

	        // Convert the InputStream into a string
	        String contentAsString = readResponse(is);
	        // Log.d(DEBUG_TAG, contentAsString);
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
	
	/**
	 * Converts the input stream from a url connection into a response string.
	 * 
	 * Reference: <a href="http://stackoverflow.com/questions/11766878/sending-files-using-post-with-httpurlconnection">http://stackoverflow.com/questions/11766878/sending-files-using-post-with-httpurlconnection</a> on Oct 23, 2014, User Mihai Todor
	 * 
	 * @param stream						The stream to read from.
	 * @return								The response as a string.
	 * @throws IOException					Exception thrown on any failure to read from stream.
	 * @throws UnsupportedEncodingException	Expceptino thrown on any non-recognized encoding type. 
	 */
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
