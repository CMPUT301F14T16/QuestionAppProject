package com.example.protoimage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;

public class eSearchElastic {
	
	 public static void ESE (final ImageAdapter imgAdapter)throws ClientProtocolException, IOException {

		 AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){

		     @Override
		     protected Void doInBackground(Void... params) {
		         String server = "esearch.gumgum.com";
		         String index = "images";
		         String type = "images_schema_1";
		         StringEntity query = null;
		         try {
		             query = new StringEntity("{\"sort\" : [ {\"confidence_level\" : {\"order\" : \"desc\"} }],\"from\" : 0, \"size\" : 10,\"query\" : {\"text_phrase\" : { \"keyword\" : \"finding nemo\"}},\"filter\" : {\"numeric_range\" : {\"confidence_level\" : { \"from\" : 10, \"to\" : 100, \"include_lower\" : true, \"include_upper\" : true}}}}'");
		         } catch (UnsupportedEncodingException e) {
		             // TODO Auto-generated catch block
		             e.printStackTrace();
		         }
		         int i=0;
		         HttpClient httpclient = new DefaultHttpClient();
		         StringBuilder urlStr = new StringBuilder("http://");
		          urlStr.append(server)
		                 .append("/" + index)
		                 .append("/" + type + "/_search");
		          System.out.println(urlStr.toString());
		          while (i < 5) {
		             HttpPost httpost = new HttpPost(urlStr.toString());
		             httpost.setEntity((HttpEntity)query);

		          httpost.setHeader("Accept", "application/json");
		             httpost.setHeader("Content-type", "application/json");

		              HttpResponse response = null;
		             try {
		                 response = httpclient.execute(httpost);
		             } catch (ClientProtocolException e) {
		                 // TODO Auto-generated catch block
		                 e.printStackTrace();
		             } catch (IOException e) {
		                 // TODO Auto-generated catch block
		                 e.printStackTrace();
		             }

		             String answer = null;
		             try {
		                 answer = org.apache.http.util.EntityUtils.toString(response.getEntity());
		             } catch (ParseException e) {
		                 // TODO Auto-generated catch block
		                 e.printStackTrace();
		             } catch (IOException e) {
		                 // TODO Auto-generated catch block
		                 e.printStackTrace();
		             }
		             System.out.println(answer);
		             i++;
		             
		             JSONObject answerJson = null;
					 try {
						 answerJson = new JSONObject(answer);
					 } catch (JSONException e1) {
						 // TODO Auto-generated catch block
						 e1.printStackTrace();
					 }
		             String urlImage;
		             try {
		            	 urlImage = answerJson.getString("hits");
		            	 JSONObject answerJson1 = new JSONObject(urlImage);
		            	 String urlImage1 = answerJson1.getString("hits").replace("[", "").replace("]","");
		            	 JSONObject answerJson2 = new JSONObject(urlImage1);
		            	 String urlImage2 = answerJson2.getString("_source");
		            	 JSONObject answerJson3 = new JSONObject(urlImage2);
		            	 String urlImage3 = answerJson3.getString("url");
		            	 System.out.println(urlImage3);
		            	 imgAdapter.addmThumbIds(urlImage3);
		             }catch (JSONException e) {
		            	 e.printStackTrace();
		             }
		             
		          }
		         return null;
		     }

		 };
		 task.execute();
	 }
}
