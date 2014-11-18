package ca.ualberta.cmput301f14t16.easya.test;

import junit.framework.TestCase;

public class AttatchPictureTest extends TestCase {
  // To test all the attached pictures in all
	// class type of object.
	public void testAttatchPictureTest () {
		
		PixelBitmap pixelBitmap = null;
		Intent data = new Intent();
		Uri selectedImageUri = data.getData();
		String imagepath;
		String[] projection = { MediaStore.Images.Media.DATA};
		/*Cursor cursor = getContentResolver().query(selectedImageUri,projection,null,null,null);
		if (cursor.moveToFirst()){
			int column_index = cursor.getColumnIndexThrow(Media.Store.Images.Media.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;*/
		Question q1 = new Question("What are you doing?","This is what I'm doing",pixelBitmap,"lingbo@ualberta.ca",14.55,23.64);
		
		
		
	}
}
