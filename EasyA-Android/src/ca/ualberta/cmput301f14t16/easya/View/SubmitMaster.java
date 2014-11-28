package ca.ualberta.cmput301f14t16.easya.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Model.Location;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class SubmitMaster extends SecureActivity {
	protected static final int SCALED_IMAGE_WIDTH = 600;
	public byte[] bytebitmap;
	public ImageView imageview;
	public boolean useLocation = false;
	public ImageView locationSubmit;
	public TextView locationDisplay;
	public Menu mMenu;
		
	public void onLocationClick(View v) {
		this.useLocation = !this.useLocation;
		int aux = this.useLocation ? R.drawable.ic_action_place_selected : R.drawable.ic_action_place ;
		locationSubmit.setImageResource(aux);
	}

	protected void setLocationMethods(){
		if (!Location.isLocationEnabled()){
			locationSubmit.setVisibility(View.GONE);
			locationDisplay.setVisibility(View.GONE);
		}else{
			locationSubmit.setVisibility(View.VISIBLE);
			locationDisplay.setVisibility(View.VISIBLE);
			locationDisplay.setText(Location.getLocationName());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		return true;
	}

	public void onLocationSettingsClick(View v) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}

	public void onAttachPictureClick(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setType("image/*");
		startActivityForResult(intent, 1);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case android.R.id.home:
				onBackPressed();
				return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onPause(){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		super.onPause();
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

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	protected final TextWatcher watcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		public void afterTextChanged(Editable s) {
			checkInputs();
		}
	};
	
	public abstract void checkInputs();
}
