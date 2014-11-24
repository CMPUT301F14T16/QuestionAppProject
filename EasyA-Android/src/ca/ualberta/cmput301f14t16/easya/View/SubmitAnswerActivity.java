package ca.ualberta.cmput301f14t16.easya.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;


import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitAnswerTask;
import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.geoCoder;
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
 * 
 * @author Cauani
 *
 */
public class SubmitAnswerActivity extends SecureActivity {
    private ImageView imageview, addimage;
    private byte[] bytebitmap;
    private static final int SCALED_IMAGE_WIDTH = 600;
    private GPSTracker gps;
    private double[] coordinate={0.0,0.0};
    private boolean fromGPS=false;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_answer);
		addimage = (ImageView)findViewById(R.id.submit_answer_picture_add);
        imageview = (ImageView)findViewById(R.id.submit_answer_imageView_pic);
		CheckBox cb= (CheckBox) findViewById(R.id.gps);
		cb.setOnCheckedChangeListener(null);
		//check.setOnCheckedChangeListener(new TodoCheckboxListener(position));

		cb.setChecked(false);
		cb.setOnCheckedChangeListener(new TodoCheckboxListerner());
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        addimage.setOnClickListener(new OnClickListener() {          	  
            @Override  
            public void onClick(View v) {  
   			 Intent intent = new Intent(Intent.ACTION_PICK, null);
		     intent.setType("image/*");
		     startActivityForResult(intent,1);
            }  
        });  
	}		

	
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
	        		coordinate = geoCoder.toLatLong(this, address);
	        	}
	        	(new submitAnswerTask(
	        			this,
	        			(getIntent()).getStringExtra(GeneralHelper.AQUESTION_KEY),
	        			((EditText)findViewById(R.id.submit_answer_body)).getText().toString(),
	        			bytebitmap,
	        			coordinate)).execute();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onPause(){
        PMClient pm = new PMClient();
        pm.saveABody(((EditText)findViewById(R.id.submit_answer_body)).getText().toString());
        super.onPause();
    }

    @Override
    public void onResume(){
    	PMClient pm = new PMClient();
    	((EditText)findViewById(R.id.submit_answer_body)).setText(pm.getABody());
        super.onResume();
    }
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
		if (requestCode == 1 && resultCode == RESULT_OK) {	          
			Uri selectedImageUri = data.getData();
			String imagepath = getPath(selectedImageUri);
			Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			long lengthbmp = byteArray.length;
			
			if (lengthbmp > 64000) {
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();
				int inH = h*SCALED_IMAGE_WIDTH/w;
	    		Bitmap resizedBmp = Bitmap.createScaledBitmap(bitmap, SCALED_IMAGE_WIDTH, inH, true);
	    		stream = new ByteArrayOutputStream();
				resizedBmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				byteArray = stream.toByteArray();
				lengthbmp = byteArray.length;
	    		
				if (lengthbmp > 64000) {
					stream = new ByteArrayOutputStream();
					resizedBmp.compress(Bitmap.CompressFormat.JPEG, 60, stream);
					byteArray = stream.toByteArray();
					lengthbmp = byteArray.length;
					
					if (lengthbmp > 64000) { 
						Toast.makeText(getApplicationContext(), "Picture Still Too Big After Resizing", Toast.LENGTH_SHORT).show();
						return;
					}
				}
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
    	gps = new GPSTracker(SubmitAnswerActivity.this);
    	coordinate = new double[2];
    	if (gps.canGetLocation()){
    		double latitude = gps.getLatitude();
    		double longitude = gps.getLongitude();
    		coordinate[0] = latitude;
    		coordinate[1] = longitude;

    		//Toast.makeText(getApplicationContext(), latitude+"\n"+longitude+"Able to use geocoder: ", Toast.LENGTH_LONG).show();
    		
    	}
    	else{
    		gps.showSettingsAlert();
    	}
    }
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
