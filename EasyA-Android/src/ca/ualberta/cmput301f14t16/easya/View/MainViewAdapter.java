package ca.ualberta.cmput301f14t16.easya.View;

import java.text.SimpleDateFormat;
import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.QuestionList;
import ca.ualberta.cmput301f14t16.easya.R.id;
import ca.ualberta.cmput301f14t16.easya.R.layout;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author Cauani
 *
 */
public class MainViewAdapter extends ArrayAdapter<QuestionList> {
    private LayoutInflater inflater;

    public MainViewAdapter( Context context, List<QuestionList> questions)
    {
        super( context, R.layout.main_questions_fragment, R.id.question_list, questions );
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int id, View view, ViewGroup parent)
    {
        // Question item to display
        QuestionList qItem = (QuestionList)this.getItem(id);
        // The child views in each row.
        TextView qTitle, qAuthor, qAnswers, qUpVotes;

        // Create a new row for the list
        if ( view == null ){
            view = inflater.inflate(R.layout.main_questions_fragment, parent, false);

            qTitle = (TextView) view.findViewById( R.id.questionTitle);
            qAuthor= (TextView) view.findViewById( R.id.questionAuthor);
            qAnswers = (TextView) view.findViewById( R.id.questionComments);
            qUpVotes = (TextView) view.findViewById( R.id.questionUpvotes);

            view.setTag(new MainViewAdapterHolder(qTitle, qAuthor, qAnswers, qUpVotes));
            view.setOnClickListener(
                new View.OnClickListener()
                 {
                     public void onClick(View v)
                     {
                         Intent i = new Intent(getContext(), QuestionActivity.class);
                         // Get the object in the title tag
                         String qId = ((QuestionList)((MainViewAdapterHolder)v.getTag()).getTitle().getTag()).getId();
                         i.putExtra(MainActivity.QUESTION_KEY, qId);
                         //TODO: save this code :: i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         getContext().startActivity(i);
                     }
                 }
            );
        }
        else
        {
            MainViewAdapterHolder viewHolder = (MainViewAdapterHolder)view.getTag();
            qTitle = viewHolder.getTitle();
            qAuthor = viewHolder.getAuthor();
            qAnswers = viewHolder.getAnswers();
            qUpVotes = viewHolder.getUpVotes();
        }
    	//The title will hold the Question object for when we need it
        qTitle.setTag(qItem);
        
        //Formatting display date
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy hh:mm");
        String dateAsString = sdf.format(qItem.getDate());
        		
        qTitle.setText(qItem.getTitle());
        qAuthor.setText(qItem.getUsername() + " " + dateAsString);
        qAnswers.setText(qItem.getAnswers());
        qUpVotes.setText(qItem.getUpvotes());

        return view;
    }    
    
    protected static class MainViewAdapterHolder {
        private TextView qTitle, qAuthor, qAnswers, qUpVotes;
        
        public MainViewAdapterHolder( TextView t, TextView a, TextView an, TextView u) {
            this.qTitle = t ;
            this.qAuthor = a ;
            this.qAnswers= an ;
            this.qUpVotes = u ;
        }
        public TextView getTitle() {
            return qTitle;
        }
        public TextView getAuthor() {
            return qAuthor;
        }
        public TextView getAnswers() {
            return qAnswers;
        }
        public TextView getUpVotes() {
            return qUpVotes;
        }
    }
}
