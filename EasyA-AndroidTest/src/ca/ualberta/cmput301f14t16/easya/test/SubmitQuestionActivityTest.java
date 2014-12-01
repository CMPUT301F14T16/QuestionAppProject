package ca.ualberta.cmput301f14t16.easya.test;

import java.io.ByteArrayOutputStream;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Base64;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.View.SubmitQuestionActivity;
import junit.framework.TestCase;

/**
 * 
 *  
 * @author Lingbo Tang,Klinton W Shmeid 
 *
 *
 */

//This tests covers the 
//use cases of AttatchPiture 
//for both question and answer


public class SubmitQuestionActivityTest extends ActivityInstrumentationTestCase2<SubmitQuestionActivity> {

	public SubmitQuestionActivityTest() {
		super(SubmitQuestionActivity.class);
	}
	

	public void testSubmitQuestionActivityTest() {
		
		//Initialize the Constructor parameters
		byte[] bytebitmap;
		double[] coordinates = new double[2];
		coordinates[0] = 52.45;
		coordinates[1] = 13.37;
		String location = "Berlin German";
		
		//Initialize test Activity
		Intent intent = new Intent();
		setActivityIntent(intent);
		SubmitQuestionActivity SQA = getActivity();
		
		Intent data = new Intent();
		setActivityIntent(data);
		data.setType("image/*");
		
		//Get the Image Path from local Uri
		//If there's no image files, throw the exception
		try {
			
			//Get Path
			Uri selectedImageUri = data.getData();
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = SQA.getContentResolver().query(selectedImageUri, projection, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String imagepath = cursor.getString(column_index);
			
			//Covert the path to bitmap
			Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			bytebitmap = Base64.encode(byteArray, 1);
			
			//Test if we can attacth the exact images
			Question q1 = new Question("What are you doing?","This is what I'm doing",bytebitmap,"lingbo@ualberta.ca");
			Answer a1 = new Answer("This is it",bytebitmap,"lingbo@ualberta.ca",coordinates,location);
			assertTrue(q1.getImage().equals(bytebitmap));
			assertTrue(a1.getImage().equals(bytebitmap));
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}