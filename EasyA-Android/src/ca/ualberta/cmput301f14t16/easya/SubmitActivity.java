package ca.ualberta.cmput301f14t16.easya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

public class SubmitActivity extends Activity{
	private static final int Phone = 1; 
	private static final int TakeAPictuer = 2; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit);
	}
	
	
	public void picturePath(){
		new AlertDialog.Builder(this).setTitle("Take a pictuer of select from phone")
		.setNegativeButton("Take a pictuer", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, Phone);
				
			}
		}).setPositiveButton("From Phone", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent,TakeAPictuer);
			}
		}).show();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode){
		case Phone:
			
			break;
		case TakeAPictuer:
			
			break;
		}
	}
	
	

}
