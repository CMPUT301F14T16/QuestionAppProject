package ca.ualberta.cmput301f14t16.easya.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import junit.framework.TestCase;
import ca.ualberta.cmput301f14t16.easya.View.*;

public class AttatchPictureTest extends TestCase {
  // To test all the attached pictures in all
	// class type of object.
	public void testAttatchPictureTest () {
		
		byte[] bytebitmap;
		Intent data = new Intent();
		Uri selectedImageUri = data.getData();
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String imagepath = cursor.getString(column_index);
		Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		bytebitmap = Base64.encode(byteArray, 1);
		Question q1 = new Question("What are you doing?","This is what I'm doing",bytebitmap,"lingbo@ualberta.ca");
		
		
		
	}
}
