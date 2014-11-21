package ca.ualberta.cmput301f14t16.easya.View;

import java.io.ByteArrayOutputStream;
import java.io.File;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitAnswerTask;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * 
 * @author Cauani
 *
 */
public class SubmitAnswerActivity extends SecureActivity {
    private ImageView imageview, addimage;
    private byte[] bytebitmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_answer);
		addimage = (ImageView)findViewById(R.id.submit_answer_picture_add);
        imageview = (ImageView)findViewById(R.id.submit_answer_imageView_pic);
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
	        	(new submitAnswerTask(
	        			this,
	        			(getIntent()).getStringExtra(GeneralHelper.AQUESTION_KEY),
	        			((EditText)findViewById(R.id.submit_answer_body)).getText().toString(),
	        			bytebitmap)).execute();
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
			//As a user I don't want the picture over 64kb
			if (lengthbmp > 64000) {
				Toast.makeText(getApplicationContext(), "Picture Over Size", Toast.LENGTH_SHORT).show();
    			return;
			}
			bytebitmap = Base64.encode(byteArray, 1);
			
			File file = new File(imagepath);
			long length = file.length();
			int lengthint=(int)length;

			
			imageview.setImageBitmap(bitmap);	      
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
