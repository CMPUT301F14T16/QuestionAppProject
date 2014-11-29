package ca.ualberta.cmput301f14t16.easya.View;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.favouriteTask;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.getQuestionTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.InternetCheck;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Data.Cache;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
	public static Question question;
	private String qId;
	public Menu menu;
	private boolean syncInProgress;
	private boolean favouriteSet;
	public static SortEnum sorter = SortEnum.NEWEST;

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
		question = null;
		this.qId = (getIntent()).getStringExtra(GeneralHelper.QUESTION_KEY);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		case R.id.menu_sync:
			this.question = null;
			update();
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
		if (!this.favouriteSet){
			this.favouriteSet = true;
			MenuItem m = menu.findItem(R.id.menu_question_favourite);
			if (!question.checkFavourite(MainModel.getInstance().getCurrentUser())) {
				m.setIcon(R.drawable.ic_action_important);
			} else {
				m.setIcon(R.drawable.ic_action_not_important);
			}
			(new favouriteTask(this, this.question)).execute();
		}
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
		if (question != null){
			try{
				update(Cache.getInstance().getQuestionFromCache(question.getId()));
			}catch(NoContentAvailableException ex){
				//TODO: show no content
			}
		}
		if (question != null)
			animateSync();
		(new getQuestionTask(this, this, this.qId, question == null)).execute();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#update(java.lang.Object)
	 */
	@Override
	public void update(Question q) {
		SetAdapter(q);
		stopAnimateSync();
		favouriteSet = false;
	}

	/**
	 * Starts a spinner animation to show when something is loading.
	 */
	public void animateSync() {
		this.syncInProgress = true;
		try {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			ImageView imgV = (ImageView) inflater.inflate(
					R.layout.refresh_asset, null);
			Animation anim = AnimationUtils.loadAnimation(this,
					R.anim.refresh_asset);
			anim.setRepeatCount(Animation.INFINITE);
			imgV.startAnimation(anim);
			MenuItem mi = ((MenuItem) menu.findItem(R.id.menu_sync));
			mi.setActionView(imgV);
		} catch (Exception ex) {}
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#stopAnimateSync()
	 */
	public void stopAnimateSync() {
		this.syncInProgress = false;
		MenuItem m = menu.findItem(R.id.menu_sync);
		if (m.getActionView() != null) {
			m.getActionView().clearAnimation();
			m.setActionView(null);
		}
	}
}
