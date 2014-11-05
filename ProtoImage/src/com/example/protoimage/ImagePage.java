package com.example.protoimage;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

//All referenced from http://stackoverflow.com/questions/13591944/load-images-in-a-gallery-and-elasticsearch

public class ImagePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_page);
		
		GridView gridView = (GridView) findViewById(R.id.GridLayout);
		// Instance of ImageAdapter Class
	    ImageAdapter imgAdapter =new ImageAdapter(this);
	    imgAdapter.addmThumbIds("http://i724.photobucket.com/albums/ww246/bnhenson/squishy.jpg");
	    imgAdapter.addmThumbIds("http://xc0.xanga.com/08fe2b7a30c37281496919/m224298958.jpg");
	    imgAdapter.addmThumbIds("http://cdn4.teen.com/wp-content/uploads/2012/07/paranorman-zayn-malik-tweets.jpg");

	        try {
	            eSearchElastic.ESE(imgAdapter);
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }   
	    gridView.setAdapter(imgAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
