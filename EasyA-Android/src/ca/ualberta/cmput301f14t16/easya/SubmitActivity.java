package ca.ualberta.cmput301f14t16.easya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class SubmitActivity extends Activity{
	private static final int Phone = 1; 
	private static final int TakeAPictuer = 2; 
    private String picturePath=null;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
	}
	
	
	public void picturePath(){
		Intent intent = new Intent(Intent.ACTION_PICK, null);
	    intent.setType("image/*");
		startActivityForResult(intent,TakeAPictuer);

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode){
		case Phone:
			if (resultCode == RESULT_OK) {
				Uri selectedImageUri = data.getData();
				picturePath = getPath(selectedImageUri);
				//Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
				//imageview.setImageBitmap(bitmap);
				//picturePath.setText("Uploading file path:" +picturePath);
			}
			break;
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
