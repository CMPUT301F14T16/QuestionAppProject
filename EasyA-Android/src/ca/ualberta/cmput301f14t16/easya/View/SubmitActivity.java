package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * A subclass of {@link Activity} that provides the basic functionality allowing
 * the user to submit a question or an answer.
 *
 * @author Cauani
 *
 */
public class SubmitActivity extends SecureActivity {
	private static final int Phone = 1;
	private static final int TakeAPicture = 2;
	private String picturePath = null;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
	}

	/**
	 * 
	 */
	public void picturePath() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setType("image/*");
		startActivityForResult(intent, TakeAPicture);

	}

	/**
	 * @see android.app.Activity#onActivityResult(int, int,
	 *      android.content.Intent)
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Phone:
			if (resultCode == RESULT_OK) {
				Uri selectedImageUri = data.getData();
				picturePath = getPath(selectedImageUri);
				// Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
				// imageview.setImageBitmap(bitmap);
				// picturePath.setText("Uploading file path:" +picturePath);
			}
			break;
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
