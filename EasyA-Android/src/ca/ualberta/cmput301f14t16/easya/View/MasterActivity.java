package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;
import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.changeUsernameTask;
import ca.ualberta.cmput301f14t16.easya.Model.InternetCheck;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.Model.Queue;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import ca.ualberta.cmput301f14t16.easya.Model.Sort;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Provides a general implementation of an {@link android.app.Activity}
 * subclass. This class is not intended to be used, but rather implements many
 * basic methods that will be used by potential subclasses.
 * 
 * In general, this {@link android.app.Activity} is designed to display a list
 * of question. Effectivly, this is the base for the app's main page display.
 * 
 * @author Cauani
 *
 */
public abstract class MasterActivity extends SecureActivity implements
		MainView<List<QuestionList>> {
	private DrawerLayout mDrawerLayout;
	protected ActionBarDrawerToggle mDrawerToggle;
	private LinearLayout mDrawerList;
	public List<QuestionList> displayedQuestions;
	private final static int UPDATEBANNER = 250000025;
	protected int position;
	public boolean syncInProgress = false;
	public Menu menu;
	public static SortEnum sorter = SortEnum.NEWEST;
	private TextView sortHeader;
	private boolean showingBanner = false;
	protected boolean updateCalled = false;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_master);
		MainModel.getInstance().addView(this);
		((ListView) findViewById(R.id.drawer_menu_options))
				.setOnItemClickListener(folderClickListener);
		createDrawerMenu();
		Queue.getInstance();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    searchQA(intent);
		setIntent(null);
	}

	private void searchQA(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			Intent i = new Intent(this, SearchActivity.class);
			i.putExtra(SearchManager.QUERY, query);
			startActivity(i);
	    }
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if (this.menu == null){ 
			this.menu = menu;
			update();
		}
		hideIconsFromACBar();
		return true;
	}

	/**
	 * Toggles the visibility of the menu options in the action bar.
	 */
	private void hideIconsFromACBar() {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_search).setVisible(!drawerOpen && !(position==4));
		menu.findItem(R.id.menu_sync).setVisible(!drawerOpen);
		menu.findItem(R.id.menu_sort).setVisible(!drawerOpen);
	}

	/**
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	/**
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Handles an option being selected from the options menu. Most options
	 * involve sorting the list of questions displayed in different ways. To do
	 * this, the stored list of questions is sorted with the relevant method
	 * found in the {@link ca.ualberta.cmput301f14t16.easya.Model.Sort} class,
	 * and the display adapter is then refreshed.
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_search:			
			onSearchRequested();
			return true;
		case R.id.menu_sort:			
			buildSortDialog(this);
			return true;
		case R.id.menu_sync:
			if (!this.syncInProgress) {
				InternetCheck.forceCheckForInternet();
					Queue.getInstance();
				update();
				if (!InternetCheck.haveInternet())
					Toast.makeText(this, 
							"We can't connect to the internet right now, check your internet connectivity and try again!", 
							Toast.LENGTH_LONG).show();
			}
			return true;
		case R.id.menu_settings:
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Listener used by the activity to handle clicks on the Home/Favourites/My
	 * Questions/Saved Questions menu.
	 */
	private OnItemClickListener folderClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
			switch (pos) {
			case ListView.INVALID_POSITION:
				break;
			case 0: // Home
				if (!(position == 0)) {
					Intent i = new Intent(v.getContext(), MainActivity.class);
					startActivity(i);
				}
				break;
			case 1: // Favourites
				if (!(position == 1)) {
					Intent i = new Intent(v.getContext(),
							FavouritesActivity.class);
					startActivity(i);
				}
				break;
			case 2: // My questions
				if (!(position == 2)) {
					Intent i = new Intent(v.getContext(),
							MyQuestionActivity.class);
					startActivity(i);
				}
				break;
			case 3: // Saved questions
				if (!(position == 3)) {
					Intent i = new Intent(v.getContext(), SavedActivity.class);
					startActivity(i);
				}
				break;
			case 4: // logout
				if (!(position == 4)) {
					PMClient pm = new PMClient();
					pm.removeUser();
					MainModel.getInstance().removeMainUser();
					//MainModel.wipeData();
					Intent i = new Intent(v.getContext(), WelcomeActivity.class);
					startActivity(i);
				}
				break;
			}
			if (closeViewOnSwitch())
				finish();
			else if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
	};

	/**
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause() {
		super.onPause();
		MainModel.getInstance().killNotify();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if (this.updateCalled)
			update();
		this.updateCalled = false;
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
	 * Sets the {@link ArrayAdapter} used by this {@link android.app.Activity}'s
	 * question list to a newly created adapter using the list of
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects provided
	 * as content.
	 * 
	 * @param lst
	 *            Will be used as the content for the new ArrayAdapter
	 *            generated.
	 */
	private void SetAdapter(List<QuestionList> lst) {
		ListView ls = (ListView) findViewById(R.id.question_list);		
		ls.setAdapter(null);
		ls.removeHeaderView(sortHeader);
		ArrayAdapter<QuestionList> adapter = new MainViewAdapter(this, lst);
		ls.addHeaderView(getSortHeader());
		ls.setAdapter(adapter);
	}
	
	private View getSortHeader(){
		sortHeader = (TextView)getLayoutInflater().inflate(R.layout.sort_fragment, null, false);
		try{
			sortHeader.setText("Displaying " + displayedQuestions.size() + " question(s). Sorting by " + sorter.getDisplayName() + ".");
		}catch(Exception ex){
			sortHeader.setText("");
		}
		return sortHeader;
	}

	/**
	 * Listener method that handles the onClick for the new question button.
	 * Starts a new {@link SubmitQuestionActivity}.
	 * 
	 * @param v
	 */
	public void AddNewQuestion(View v) {
		Intent i = new Intent(this, SubmitQuestionActivity.class);
		this.startActivity(i);
	}

	/**
	 * Listener method that handles the onClick for the change username button.
	 * Calls {@link MasterActivity#changeUsernameDialog}.
	 * 
	 * @param v
	 */
	public void changeUsername(View v) {
		changeUsernameDialog(v.getContext());
	}

	/**
	 * Creates a new alert dialog to allow the user to submit a username change.
	 * 
	 * @param ctx
	 */
	public void changeUsernameDialog(Context ctx) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.change_username_fragment, null);
		((EditText) v.findViewById(R.id.change_username_username))
				.setText(MainModel.getInstance().getCurrentUser().getUsername());
		builder.setView(v)
				.setPositiveButton("Change",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								(new changeUsernameTask(((Dialog) dialog)
										.getContext(), dialog)).execute();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		Dialog d = builder.create();
		d.setOwnerActivity(this);
		d.show();
	}

	/**
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#update()
	 */
	@Override
	public void update() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				System.out.println("This view is being updated, yeah!");
				updateCalled = true;
				animateSync();
				startUpdate();
			}
		});
	}

	protected abstract void startUpdate();

	/**
	 * Displays a banner showing the text "New content found!".
	 */
	private void DisplayBanner() {
		RelativeLayout conteiner = (RelativeLayout) findViewById(R.id.main_rl);
		if (!showingBanner) {
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			lp.setMargins(0, (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
							.getDisplayMetrics()), 0, 0);
			TextView banner = new TextView(this);
			banner.setId(UPDATEBANNER);
			banner.setHeight((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 30, getResources()
							.getDisplayMetrics()));
			banner.setWidth((int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 200, getResources()
							.getDisplayMetrics()));
			banner.setText("New content found!");
			banner.setTextColor(Color.WHITE);
			banner.setOnClickListener(onclickUpdateListener);
			banner.setBackgroundResource(R.drawable.banner_background);
			banner.setGravity(Gravity.CENTER);
			conteiner.addView(banner, lp);
			showingBanner = true;
		}
	}

	/**
	 * Listener that handles clicks on a banner created by
	 * {@link MasterActivity#DisplayBanner} and closes it.
	 */
	private OnClickListener onclickUpdateListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			bindAdapter();
			((RelativeLayout) v.getParent()).removeView(v);
			showingBanner = false;
		}
	};

	/**
	 * Calls {@link MasterActivity#SetAdapter} with
	 * {@link MasterActivity#displayedQuestions}
	 */
	protected void bindAdapter() {
		SetAdapter(displayedQuestions);
	}

	/**
	 * Refreshes the list of
	 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects
	 * displayed.
	 * 
	 * @see ca.ualberta.cmput301f14t16.easya.View.MainView#update(Object)
	 */
	@Override
	public void update(List<QuestionList> lst) {
		System.out.println("I got in, now let's see what's what.");
		if (displayedQuestions == null || displayedQuestions.size() <= 0 || lst.size() <= displayedQuestions.size()) {
			ListView listView = ((ListView) findViewById(R.id.question_list));
			int listPos = listView.getFirstVisiblePosition();
			View view = listView.getChildAt(0);
			int top = (view == null) ? 0 : view.getTop();
			displayedQuestions = Sort.sort(lst, sorter);
			bindAdapter();
			listView.setSelectionFromTop(listPos, top);
			listView.requestFocus();
			System.out.println("Refreshed without banner");
		} else {
			System.out.println("Showing the banner!");
			DisplayBanner();
			displayedQuestions = Sort.sort(lst, sorter);
		}
	}

	/**
	 * Creates a new drawer menu for this {@link android.app.Activity}
	 */
	public void createDrawerMenu() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (LinearLayout) findViewById(R.id.drawer_menu);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/* Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				// getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/* Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
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
		} catch (Exception ex) {} // Let it go...
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
	
	public void buildSortDialog(Context ctx){
		new AlertDialog.Builder(ctx)
		.setTitle("Sort questions by:")
		.setItems(SortEnum.getList(),
		new DialogInterface.OnClickListener() {
			public void onClick(
					DialogInterface dialog,
					int which) {
				SortEnum actual = SortEnum.values()[which]; 
				displayedQuestions = Sort.sort(displayedQuestions, actual);
				sorter = actual;
				SetAdapter(displayedQuestions);						
			}
		}).create().show();
	}
	
	/**
	 * @return True
	 */
	protected boolean closeViewOnSwitch() {
		return true;
	}
}