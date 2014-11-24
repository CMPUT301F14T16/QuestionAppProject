package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A subclass of {@link MasterActivity} specialized to display the user's
 * submitted questions.
 */
public class MyQuestionActivity extends MasterActivity {
	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ((LinearLayout) findViewById(R.id.main_question_holder))
				.getLayoutParams();
		lp.height = 0;
		lp.setMargins(0, 0, 0, 0);
		((LinearLayout) findViewById(R.id.main_question_holder))
				.setLayoutParams(lp);
		position = 2;
		getActionBar().setTitle("My questions");
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#startUpdate()
	 */
	@Override
	protected void startUpdate() {
		((TextView) findViewById(R.id.drawer_username)).setText(MainModel
				.getInstance().getCurrentUser().getUsername());
		try {
			update(MainModel.getInstance().getAllUserQuestions());
		} catch (NoContentAvailableException e) {
			// TODO: display the nocontentavailable screen
		}
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MasterActivity#update(java.util.List)
	 */
	@Override
	public void update(List<QuestionList> lst) {
		displayedQuestions = Sort.dateSort(true, lst);
		bindAdapter();
		stopAnimateSync();
	}
}
