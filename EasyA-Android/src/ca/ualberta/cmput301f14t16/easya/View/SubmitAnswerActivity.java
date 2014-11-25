package ca.ualberta.cmput301f14t16.easya.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;


import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitAnswerTask;
import ca.ualberta.cmput301f14t16.easya.Model.GPSTracker;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.GeoCoder;
import ca.ualberta.cmput301f14t16.easya.Model.Location;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * A subclass to the {@link SubmitActivity} class, providing a UI specializes to
 * submitting {@link ca.ualberta.cmput301f14t16.easya.Model.Answer} objects.
 * 
 * @author Cauani
 *
 */
public class SubmitAnswerActivity extends SecureActivity {
	private ImageView imageview;
	private byte[] bytebitmap;
    private static final int SCALED_IMAGE_WIDTH = 600;
    private boolean useLocation = false;
    
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_answer);
        imageview = (ImageView)findViewById(R.id.submit_answer_imageView_pic);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		setLocationMethods();
	}
	
	private void setLocationMethods(){
		if (!Location.isLocationEnabled()){
			((ImageView)findViewById(R.id.submit_answer_location)).setVisibility(View.GONE);
			((TextView)findViewById(R.id.submit_answer_location_display)).setVisibility(View.GONE);
		}else{
			((ImageView)findViewById(R.id.submit_answer_location)).setVisibility(View.VISIBLE);
			TextView tv = (TextView)findViewById(R.id.submit_answer_location_display);
			tv.setVisibility(View.VISIBLE);
			tv.setText(Location.getLocationName());
		}
	}

	public void onAttachPictureClick(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setType("image/*");
		startActivityForResult(intent, 1);
	}
	
	public void onLocationClick(View v) {
		this.useLocation = !this.useLocation;
		int aux = this.useLocation ? R.drawable.ic_action_place_selected : R.drawable.ic_action_place ;
		((ImageView)findViewById(R.id.submit_answer_location)).setImageResource(aux);
	}
	
	public void onLocationSettingsClick(View v) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}
	
	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.submit, menu);
		return true;
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case android.R.id.home:
				onBackPressed();
				return true;
	        case R.id.menu_submit:
	        	(new submitAnswerTask(
	        			this,
	        			(getIntent()).getStringExtra(GeneralHelper.AQUESTION_KEY),
	        			((EditText)findViewById(R.id.submit_answer_body)).getText().toString(),
	        			this.bytebitmap,
	        			this.useLocation)).execute();
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
		pm.saveABody(((EditText) findViewById(R.id.submit_answer_body))
				.getText().toString());
		super.onPause();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.SecureActivity#onResume()
	 */
	@Override
	public void onResume() {
		PMClient pm = new PMClient();
		((EditText) findViewById(R.id.submit_answer_body)).setText(pm
				.getABody());
		setLocationMethods();
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

	/**
	 * @param uri
	 * @return The path of the image being submitted.
	 */
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}
