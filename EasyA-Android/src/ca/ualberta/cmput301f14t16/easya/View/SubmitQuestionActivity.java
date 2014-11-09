package ca.ualberta.cmput301f14t16.easya.View;

import java.io.File;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.PixelBitmap;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author Cauani
 *
 */
public class SubmitQuestionActivity extends Activity {
	private ProgressDialog pd;
    private ImageView imageview, addimage;
    private PixelBitmap pixelbitmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_question);
		addimage = (ImageView)findViewById(R.id.submit_question_picture_add);
        imageview = (ImageView)findViewById(R.id.submit_question_imageView_pic);
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
	        	(new submitQuestionTask(this)).execute();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    public void onPause(){
        PMClient pm = new PMClient();
        //TODO: pass this to the controller
        pm.saveQTitle(((EditText)findViewById(R.id.submit_question_title)).getText().toString());
        pm.saveQBody(((EditText)findViewById(R.id.submit_question_body)).getText().toString());
        super.onPause();
    }

    @Override
    public void onResume(){
    	PMClient pm = new PMClient();
    	((EditText)findViewById(R.id.submit_question_title)).setText(pm.getQTitle());
    	((EditText)findViewById(R.id.submit_question_body)).setText(pm.getQBody());
        super.onResume();
    }
    
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     
		if (requestCode == 1 && resultCode == RESULT_OK) {	          
			Uri selectedImageUri = data.getData();
			String imagepath = getPath(selectedImageUri);
			Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
			File file = new File(imagepath);
			long length = file.length();
			int lengthint=(int)length;
			pixelbitmap=new PixelBitmap(bitmap.getHeight(),bitmap.getHeight());
			pixelbitmap.getColors(bitmap);
			//Bitmap bitmap2=pixelbitmap.createBitmap();
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
	
    private class submitQuestionTask extends AsyncTask<Void, Void, Boolean> {
    	private NewQuestionController controller;
    	private Context ctx;
    	
    	public submitQuestionTask(Context ctx){
    		this.ctx = ctx;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		pd = new ProgressDialog(ctx);
			pd.setTitle("Submitting question...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}
    	
    	@Override
    	protected Boolean doInBackground(Void...voids) {
            try{
            	try{
	        		controller = 
	        				NewQuestionController.create(
	        						ctx, 
	        						((EditText)findViewById(R.id.submit_question_title)).getText().toString(), 
	        						((EditText)findViewById(R.id.submit_question_body)).getText().toString(), 
	        						pixelbitmap,
	        						MainModel.getInstance().getCurrentUser().getId());
	        		return controller.submit();
	        	}catch(Exception ex){

	        		System.out.println(ex.getMessage());
	        		return false;
	        		//TODO: Deal with things as: user didn't fill out everything
	        	}
            }catch(Exception ex){
            	//TODO: Deal with this
            	return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        	if (result){
    			PMClient pm = new PMClient();	        			
    			pm.clearQ(ctx);
        		((EditText)findViewById(R.id.submit_question_title)).setText("");
            	((EditText)findViewById(R.id.submit_question_body)).setText("");
    			if (controller.submitedOffline){
    				finish();
    				Toast.makeText(getApplicationContext(), "Your Question will be posted online when you connect to the internet!", Toast.LENGTH_LONG).show();
    			}else{
        			String aux = controller.getQuestionId();
        			Intent i = new Intent(ctx,QuestionActivity.class);
        			i.putExtra(GeneralHelper.QUESTION_KEY, aux);
        			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			startActivity(i);
        			finish();
    			}
    		}else{    			
    			finish();
    			Toast.makeText(getApplicationContext(), "Something bad happened, try posting your question again!", Toast.LENGTH_LONG).show();
    		}
        	
        	if (pd!=null) {
				pd.dismiss();
			}        	
        }
    }
}
