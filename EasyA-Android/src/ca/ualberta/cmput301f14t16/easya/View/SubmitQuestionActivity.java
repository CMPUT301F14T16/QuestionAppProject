package ca.ualberta.cmput301f14t16.easya.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.Model.GeoCoder;

import java.io.InputStream;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * A subclass to the {@link SubmitActivity} class, providing a UI specializes to
 * submitting {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects.
 * 
 * @author Cauani
 *
 */
public class SubmitQuestionActivity extends SecureActivity {
	private static final int SCALED_IMAGE_WIDTH = 600;
	private ImageView imageview, addimage;
	private GPSTracker gps;
	private byte[] bytebitmap;
    private double[] coordinate={0.0,0.0};
    private boolean fromGPS=false;

    
    

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_question);
		addimage = (ImageView)findViewById(R.id.submit_question_picture_add);
        imageview = (ImageView)findViewById(R.id.submit_question_imageView_pic);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_submit:
	        	String address=((EditText)findViewById(R.id.address)).getText().toString();
	        	if (fromGPS){
	        		findlocation();
	        	}
	        	else if (!address.equals("")){
	        		coordinate = GeoCoder.toLatLong(address);
	        	}
	        	(new submitQuestionTask(
	        			this, 
	        			((EditText)findViewById(R.id.submit_question_title)).getText().toString(),
	        			((EditText)findViewById(R.id.submit_question_body)).getText().toString(),
	        			bytebitmap,
	        			coordinate)).execute();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}

	/**
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() {
		PMClient pm = new PMClient();
		pm.saveQTitle(((EditText) findViewById(R.id.submit_question_title))
				.getText().toString());
		pm.saveQBody(((EditText) findViewById(R.id.submit_question_body))
				.getText().toString());
		super.onPause();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.SecureActivity#onResume()
	 */
	@Override
	public void onResume() {
		PMClient pm = new PMClient();
		((EditText) findViewById(R.id.submit_question_title)).setText(pm
				.getQTitle());
		((EditText) findViewById(R.id.submit_question_body)).setText(pm
				.getQBody());
		super.onResume();
	}

	/**
	 * @see android.app.Activity#onActivityResult(int, int,
	 *      android.content.Intent)
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1 && resultCode == RESULT_OK) {
			Uri selectedImageUri = data.getData();
			
			String imagepath = getPath(selectedImageUri);
			Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
			long lengthbmp = bitmap.getRowBytes() * bitmap.getHeight();
			
			ByteArrayOutputStream stream;
			byte[] byteArray;
			
			if (lengthbmp > 64000) {
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();
				int inH = h*SCALED_IMAGE_WIDTH/w;
	    		Bitmap resizedBmp = Bitmap.createScaledBitmap(bitmap, SCALED_IMAGE_WIDTH, inH, true);
	    		stream = new ByteArrayOutputStream();
				resizedBmp.compress(Bitmap.CompressFormat.JPEG, 60, stream);
				byteArray = stream.toByteArray();
				lengthbmp = byteArray.length;
					
				if (lengthbmp > 64000) {
					Toast.makeText(getApplicationContext(), "Picture Still Too Big After Resizing", Toast.LENGTH_SHORT).show();
				return;
				}
				
			} else {
				stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byteArray = stream.toByteArray();
			}
			
			// Attach image to picture
			bytebitmap = Base64.encode(byteArray, 1);

			// Display attached image
			byte[] decodedBytes = Base64.decode(bytebitmap, 1);
			InputStream is = new ByteArrayInputStream(decodedBytes);
			Bitmap bmp = BitmapFactory.decodeStream(is);
			imageview.setImageBitmap(bmp);
		}
	}

	public class TodoCheckboxListerner implements OnCheckedChangeListener{

		public void onCheckedChanged(CompoundButton buttonView, boolean checked){
			if (checked){
				fromGPS=true;
			}
			else{
				fromGPS=false;
			}
			
		}

		
	}	
    public void findlocation(){
    	gps = new GPSTracker(SubmitQuestionActivity.this);
    	coordinate = new double[2];
    	if (gps.canGetLocation()){
    		double latitude = gps.getLatitude();
    		double longitude = gps.getLongitude();
    		coordinate[0] = latitude;
    		coordinate[1] = longitude;
    		//double lat=-115.23;
    		//double lon=63.78;
    		/*
    		boolean geo= Geocoder.isPresent();
    		String theaddress ="Alberta";
    		double[] latlong = geoCoder.toLatLong(this, theaddress);
    		String address = geoCoder.toAdress(this, latlong[0],latlong[1]);
    		Toast.makeText(getApplicationContext(), address+ geo, Toast.LENGTH_LONG).show();
    		*/
    	}
    	else{
    		gps.showSettingsAlert();
    	}
    	
    	
    }

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
