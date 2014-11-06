package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 
 * @author Cauani
 *
 */
public class QuestionViewAdapter {
	private LayoutInflater inflater;
	private LinearLayout container;
	private Question q;

    public QuestionViewAdapter( Context context, Question q, LinearLayout v) {        
        this.inflater = LayoutInflater.from(context);
        this.q = q;
        this.container = v;
    }
    
    private OnEditorActionListener newReply = new OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if ((event.getAction() == KeyEvent.ACTION_DOWN) && (actionId == KeyEvent.KEYCODE_ENTER)) {
	            //TODO: pass to the controller to add a new Reply
				return true;
	        }
	        return false;
		}        
    };
    
    private OnClickListener upVote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            try{
            	//TODO: pass all this to the controller
	        	Topic t = (Topic)v.getTag();
	            t.setUpvote(MainActivity.mm.getCurrentUser());
            }catch(Exception ex){
            	//TODO: do something with the exception
            }
        }
    };    

    public void build() {
    	try{
	    	inflateQuestion();
	    	for (Answer a : q.getAnswers()){
	    		inflateAnswer(a);
	    	}
    	}catch(NoContentAvailableException ex){
    		//TODO: display the NoContentAvailable screen
    	}
    }
    
    private void inflateQuestion() throws NoContentAvailableException{
    	View v = inflater.inflate(R.layout.question_question_fragment, (ViewGroup)container, false);
    	    	
    	TextView title, body, upvotescount, authordate;
    	LinearLayout replies;
    	
    	title = (TextView)v.findViewById(R.id.question_fragment_title);
    	body = (TextView)v.findViewById(R.id.question_fragment_body);
    	authordate = (TextView)v.findViewById(R.id.question_fragment_authorDate);
    	upvotescount = (TextView)v.findViewById(R.id.question_fragment_upvoteText);
    	
    	replies = (LinearLayout)v.findViewById(R.id.question_fragment_replies_list);
    	
    	replies = inflateReplies(replies, q.getReplies());
    	
    	title.setText(q.getTitle());
    	body.setText(q.getBody());
    	authordate.setText(q.getAuthorDate());
    	upvotescount.setText(q.getUpVoteCountString());
    	
    	((EditText)v.findViewById(R.id.question_fragment_submitReplyEdt)).setTag(0);
    	((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setTag(q);
    	((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setOnClickListener(upVote);
    	try{
	    	if (q.checkUpVote(MainActivity.mm.getCurrentUser())){
	    		//TODO: set a different image for when the user have already upvoted the topic
	    		//((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setImageDrawable(R.drawable.)
	    	}
    	}catch(NoContentAvailableException ex){
    		//TODO: remove the try..catch block once the user account creation have been addressed
    	}
    	
    	container.addView(v);
    }
    
    private void inflateAnswer(Answer a) throws NoContentAvailableException{
    	View v = inflater.inflate(R.layout.question_answer_fragment, (ViewGroup)container, false);
    	    	
    	TextView body, upvotescount, authordate;
    	LinearLayout replies;
    	
    	body = (TextView)v.findViewById(R.id.answer_fragment_body);
    	authordate = (TextView)v.findViewById(R.id.answer_fragment_authorDate);
    	upvotescount = (TextView)v.findViewById(R.id.answer_fragment_upvoteText);
    	
    	
    	replies = (LinearLayout)v.findViewById(R.id.answer_fragment_replies_list);
    	
    	replies = inflateReplies(replies, q.getReplies());
    	
    	body.setText(a.getBody());
    	authordate.setText(a.getAuthorDate());
    	upvotescount.setText(a.getUpVoteCountString());
    	
    	((EditText)v.findViewById(R.id.answer_fragment_submitReplyEdt)).setTag(a.getId());
    	((ImageButton)v.findViewById(R.id.answer_fragment_upvoteBtn)).setTag(a);
    	((ImageButton)v.findViewById(R.id.answer_fragment_upvoteBtn)).setOnClickListener(upVote);
    	
    	try{
	    	if (q.checkUpVote(MainActivity.mm.getCurrentUser())){
	    		//TODO: set a different image for when the user have already upvoted the topic
	    		//((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setImageDrawable(R.drawable.)
	    	}
    	}catch(NoContentAvailableException ex){
    		//TODO: remove the try..catch block once the user account creation have been addressed
    	}
    	
    	container.addView(v);
    }
    
    private LinearLayout inflateReplies(LinearLayout ll, List<Reply> replies){
    	for (Reply r : replies){
    		View v = inflater.inflate(R.layout.question_reply_fragment, (ViewGroup)container, false);
    		((TextView)v.findViewById(R.id.reply_fragment_body)).setText(r.getBody());
    		((TextView)v.findViewById(R.id.reply_fragment_authorDate)).setText(r.getAuthorDate());
    		ll.addView(v);
    	}
    	return ll;
    }       
}
