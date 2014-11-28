package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.favouriteTask;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

/**
 * An {@link android.app.Activity} subclass that displays a question page. It
 * provides a UI that allows the user to view a question, its replies and
 * answers, open its answers, favourite it, reply to it, etc.
 * 
 * @author Cauani
 *
 */
public class QuestionActivity extends SecureActivity implements
		MainView<Question> {
	private Question question;
	private String qId;
	public Menu menu;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle("Question");
		MainModel.getInstance().addView(this);
		this.qId = (getIntent()).getStringExtra(GeneralHelper.QUESTION_KEY);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		this.menu = menu;
		update();
		return true;
	}

	/**
	 * Sets a new {@link QuestionViewAdapter} For this action using the provided
	 * {@link Question}.
	 * 
	 * @param q
	 *            The {@link Question} from which the
	 *            {@link QuestionViewAdapter} will be created.
	 */
	private void SetAdapter(Question q) {
		question = q;
		MenuItem m = menu.findItem(R.id.menu_question_favourite);
		if (q.checkFavourite(MainModel.getInstance().getCurrentUser())) {
			m.setIcon(R.drawable.ic_action_important);
		} else {
			m.setIcon(R.drawable.ic_action_not_important);
		}
		QuestionViewAdapter adapter = new QuestionViewAdapter(this, question,
				(LinearLayout) findViewById(R.id.question_scrollview_container));
		adapter.build();
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_question_favourite:
			setFavourite();
			break;
		}
		return true;
	}

	/**
	 * Handles an action to set the displayed {@link Question} as a favourite.
	 */
	public void setFavourite() {
		(new favouriteTask(this, this.question)).execute();
	}

	public void AddNewAnswer(View v) {
		if (question != null) {
			Intent i = new Intent(v.getContext(), SubmitAnswerActivity.class);
			i.putExtra(GeneralHelper.AQUESTION_KEY, question.getId());
			this.startActivity(i);
		}
	}

	/**
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		MainModel.getInstance().deleteView(this);
		super.onDestroy();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#update()
	 */
	@Override
	public void update() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				startUpdate();
			}
		});
	}

	/**
	 * 
	 */
	private void startUpdate() {
		(new getQuestionTask(this, this, this.qId)).execute();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#update(java.lang.Object)
	 */
	@Override
	public void update(Question q) {
		SetAdapter(q);
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#stopAnimateSync()
	 */
	@Override
	public void stopAnimateSync() {
	}
}
