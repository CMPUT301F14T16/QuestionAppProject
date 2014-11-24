package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionListTask;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A subclass of {@link MasterActivity} specialized to display the application's
 * main page and list of questions.
 *
 * @author Cauani
 *
 */
public class MainActivity extends MasterActivity {
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((LinearLayout) findViewById(R.id.main_question_holder))
				.setVisibility(View.VISIBLE);
		position = 0;
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// Free up the thread
		Queue.getInstance().Stop();
		PMClient pm = new PMClient();
		pm.clearA(this);
		pm.clearQ(this);
		super.onDestroy();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#startUpdate()
	 */
	@Override
	protected void startUpdate() {
		((TextView) findViewById(R.id.drawer_username)).setText(MainModel
				.getInstance().getCurrentUser().getUsername());
		update(MainModel.getInstance().getAllCachedQuestions());
		(new getQuestionListTask(this, this)).execute();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#closeViewOnSwitch()
	 */
	@Override
	protected boolean closeViewOnSwitch() {
		return false;
	}
}