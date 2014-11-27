package ca.ualberta.cmput301f14t16.easya.View;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.saveToDeviceTask;
import ca.ualberta.cmput301f14t16.easya.Model.GeneralHelper;
import ca.ualberta.cmput301f14t16.easya.Model.GeoCoder;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * An extension of the view adapter class, used for displaying lists of
 * {@link ca.ualberta.cmput301f14t16.easya.Model.Question} objects.
 * 
 * @author Cauani
 *
 */
public class MainViewAdapter extends ArrayAdapter<QuestionList> {
	private LayoutInflater inflater;

	/**
	 * Creates a new MainViewAdapter.
	 * 
	 * @param context
	 * @param questions
	 *            The questions to be displayed by the adapter.
	 */
	public MainViewAdapter(Context context, List<QuestionList> questions) {
		super(context, R.layout.main_questions_fragment, R.id.question_list,
				questions);
		inflater = LayoutInflater.from(context);
	}
	
	/**
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(int id, View view, ViewGroup parent) {
		// Question item to display
		QuestionList qItem = (QuestionList) this.getItem(id);
		// The child views in each row.
		TextView qTitle, qAuthor, qAnswers, qUpVotes, qLocation;

		// Create a new row for the list
		if (view == null) {
			view = inflater.inflate(R.layout.main_questions_fragment, parent,
					false);

			qTitle = (TextView) view.findViewById(R.id.questionTitle);
			qAuthor = (TextView) view.findViewById(R.id.questionAuthor);
			qAnswers = (TextView) view.findViewById(R.id.questionComments);
			qUpVotes = (TextView) view.findViewById(R.id.questionUpvotes);
			qLocation = (TextView) view.findViewById(R.id.questionLocation);

			view.setTag(new MainViewAdapterHolder(qTitle, qAuthor, qAnswers,
					qUpVotes, qLocation));
			view.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getContext(), QuestionActivity.class);
					// Get the object in the title tag
					String qId = ((QuestionList) ((MainViewAdapterHolder) v
							.getTag()).getTitle().getTag()).getId();
					i.putExtra(GeneralHelper.QUESTION_KEY, qId);
					getContext().startActivity(i);
				}
			});
			view.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(final View v) {
					new AlertDialog.Builder(v.getContext())
							.setItems(new String[] { "Save to device" },
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											String qId = ((QuestionList) ((MainViewAdapterHolder) v
													.getTag()).getTitle()
													.getTag()).getId();
											try {
												(new saveToDeviceTask(qId))
														.execute();
											} catch (Exception ex) {
												// TODO deal with more than one
												// activity open
											}
										}
									}).create().show();
					return true;
				}
			});
			// TODO: implement favourite question in long click
		} else {
			MainViewAdapterHolder viewHolder = (MainViewAdapterHolder) view
					.getTag();
			qTitle = viewHolder.getTitle();
			qAuthor = viewHolder.getAuthor();
			qAnswers = viewHolder.getAnswers();
			qUpVotes = viewHolder.getUpVotes();
			qLocation = viewHolder.getLocation();
		}
		// The title will hold the Question object for when we need it
		qTitle.setTag(qItem);

		qTitle.setText(qItem.getTitle());
		qAuthor.setText(qItem.getAuthorDate());
		qAnswers.setText(qItem.getAnswers());
		qUpVotes.setText(qItem.getUpvotes());
		qLocation.setText(qItem.getLocation());
		if (qLocation.getText().equals(""))
			qLocation.setVisibility(View.GONE);		
		
		return view;
	}

	/**
	 * A container for the tags stored in a view object.
	 *
	 */
	protected static class MainViewAdapterHolder {
		private TextView qTitle, qAuthor, qAnswers, qUpVotes, qLocation;

		/**
		 * Creates a new MainViewAdapterHolder.
		 * 
		 * @param t
		 * @param a
		 * @param an
		 * @param u
		 */
		public MainViewAdapterHolder(TextView t, TextView a, TextView an,
				TextView u, TextView l) {
			this.qTitle = t;
			this.qAuthor = a;
			this.qAnswers = an;
			this.qUpVotes = u;
			this.qLocation = l;
		}

		/**
		 * @return {@link MainViewAdapterHolder#qTitle}
		 */
		public TextView getTitle() {
			return qTitle;
		}

		/**
		 * @return {@link MainViewAdapterHolder#qAuthor}
		 */
		public TextView getAuthor() {
			return qAuthor;
		}

		/**
		 * @return {@link MainViewAdapterHolder#qAnswers}
		 */
		public TextView getAnswers() {
			return qAnswers;
		}

		/**
		 * @return {@link MainViewAdapterHolder#qUpVotes}
		 */
		public TextView getUpVotes() {
			return qUpVotes;
		}
		
		public TextView getLocation(){
			return qLocation;
		}
	}
}
