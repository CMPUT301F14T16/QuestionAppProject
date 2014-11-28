package ca.ualberta.cmput301f14t16.easya.View;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitQuestionTask;import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

/**
 * A subclass to the {@link SubmitActivity} class, providing a UI specializes to
 * submitting {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects.
 * 
 * @author Cauani
 *
 */
public class SubmitQuestionActivity extends SubmitMaster {	
	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit_question);
        imageview = (ImageView)findViewById(R.id.submit_question_imageView_pic);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setTitle("Post question");
		
		locationSubmit = ((ImageView)findViewById(R.id.submit_question_location));
		locationDisplay = ((TextView)findViewById(R.id.submit_question_location_display));
	}
		
	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.submit, menu);
		mMenu = menu;
		MenuItem aux = menu.findItem(R.id.menu_submit);
		aux.getActionView().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new submitQuestionTask(
	        			(Activity)v.getContext(), 
	        			((EditText)findViewById(R.id.submit_question_title)).getText().toString(),
	        			((EditText)findViewById(R.id.submit_question_body)).getText().toString(),
	        			bytebitmap,
	        			useLocation)).execute();
			}
		});			
		
		((EditText) findViewById(R.id.submit_question_title))
			.addTextChangedListener(watcher);
		((EditText) findViewById(R.id.submit_question_body))
			.addTextChangedListener(watcher);
		checkInputs();
		return true;
	}

	/**
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() {
		PMClient pm = new PMClient();
		pm.saveQTitle(((EditText) findViewById(R.id.submit_question_title))
				.getText().toString());
		pm.saveQBody(((EditText) findViewById(R.id.submit_question_body))
				.getText().toString());
		super.onPause();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.SecureActivity#onResume()
	 */
	@Override
	public void onResume() {
		PMClient pm = new PMClient();
		((EditText) findViewById(R.id.submit_question_title)).setText(pm
				.getQTitle());
		((EditText) findViewById(R.id.submit_question_body)).setText(pm
				.getQBody());
		setLocationMethods();
		super.onResume();
	}
	
	@Override
	public void checkInputs(){
		View v = mMenu.findItem(R.id.menu_submit).getActionView();
		if (TextUtils.isEmpty(((EditText) findViewById(R.id.submit_question_title))
				.getText().toString().trim())
				|| TextUtils
						.isEmpty(((EditText) findViewById(R.id.submit_question_body))
								.getText().toString().trim())) {
			if (v.isClickable()){
				v.setClickable(false);
				((ImageButton)v.findViewById(R.id.menu_submit_btn)).setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
			}
		} else {
			if (!v.isClickable()){
				v.setClickable(true);
				((ImageButton)v.findViewById(R.id.menu_submit_btn)).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
			}
		}
	}
}
