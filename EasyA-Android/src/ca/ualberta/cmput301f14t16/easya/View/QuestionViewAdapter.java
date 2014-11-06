package ca.ualberta.cmput301f14t16.easya.View;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.NewQuestionController;
import ca.ualberta.cmput301f14t16.easya.Controller.NewReplyController;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import ca.ualberta.cmput301f14t16.easya.Model.Topic;
import ca.ualberta.cmput301f14t16.easya.Model.User;
import ca.ualberta.cmput301f14t16.easya.Model.Data.PMClient;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnKeyListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
	private ProgressDialog pd;

    public QuestionViewAdapter( Context context, Question q, LinearLayout v) {        
        this.inflater = LayoutInflater.from(context);
        this.q = q;
        this.container = v;
    }
    
    private OnEditorActionListener keyAction = new OnEditorActionListener() {
    	@Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND &&
                    (event!= null && event.getAction() == KeyEvent.ACTION_DOWN)) {
            	Context ctx = v.getContext();
            	BasicNameValuePair vp = (BasicNameValuePair)v.getTag();
            	String body = v.getText().toString();
            	(new submitReplyTask(ctx, vp, body)).execute();
                return false;
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
	        	// TODO: don't set upvote here, upvote is set in ESClient
	        	// TODO: dont' allow upvotes without internet connection? Or we have to create new pending type...
	            t.setUpvote(MainActivity.mm.getCurrentUser().getId());
	           
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
    	EditText addReply;
    	
    	title = (TextView)v.findViewById(R.id.question_fragment_title);
    	body = (TextView)v.findViewById(R.id.question_fragment_body);
    	authordate = (TextView)v.findViewById(R.id.question_fragment_authorDate);
    	upvotescount = (TextView)v.findViewById(R.id.question_fragment_upvoteText);
    	addReply = ((EditText)v.findViewById(R.id.question_fragment_submitReplyEdt));
    	
    	replies = (LinearLayout)v.findViewById(R.id.question_fragment_replies_list);    	
    	replies = inflateReplies(replies, q.getReplies());
    	addReply.setTag(new BasicNameValuePair(q.getId(), null) );
    	addReply.setOnEditorActionListener(keyAction);
    	addReply.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);
    	
    	title.setText(q.getTitle());
    	body.setText(q.getBody());
    	authordate.setText(q.getAuthorDate());
    	upvotescount.setText(q.getUpVoteCountString());
    	
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
    	EditText addReply;
    	    	
    	body = (TextView)v.findViewById(R.id.answer_fragment_body);
    	authordate = (TextView)v.findViewById(R.id.answer_fragment_authorDate);
    	upvotescount = (TextView)v.findViewById(R.id.answer_fragment_upvoteText);
    	addReply = ((EditText)v.findViewById(R.id.answer_fragment_submitReplyEdt)); 
    	    	
    	replies = (LinearLayout)v.findViewById(R.id.answer_fragment_replies_list);
    	replies = inflateReplies(replies, q.getReplies());
    	addReply.setTag(new BasicNameValuePair(q.getId(), a.getId()) );
    	addReply.setOnEditorActionListener(keyAction);
    	addReply.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);
    	
    	body.setText(a.getBody());
    	authordate.setText(a.getAuthorDate());
    	upvotescount.setText(a.getUpVoteCountString());
    	
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
    

    private class submitReplyTask extends AsyncTask<Void, Void, Boolean> {
    	private NewReplyController controller;
    	private Context ctx;
    	private String qId;
    	private String aId;
    	private String body;
    	
    	public submitReplyTask(Context ctx, BasicNameValuePair vp, String body){
    		this.ctx = ctx;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		pd = new ProgressDialog(ctx);
			pd.setTitle("Submitting reply...");
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
	        				NewReplyController.create(
	        						ctx, 
	        						qId, 
	        						aId,
	        						body,
	        						//MainActivity.mm.getCurrentUser().getId());
	        						(new User("creide", "juvenaldo")).getId());
	        						//TODO: remove this bogus user
	        		
	        		return controller.submit();	        		
	        	}catch(Exception ex){
	        		System.out.println(ex.getMessage());
	        		return false;
	        		//Deal with things as: user didn't fill out everything
	        	}
            }catch(Exception ex){
            	//Deal with this
            	return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        	if (result){
    			if (controller.submitedOffline){
    				Toast.makeText(ctx, "Your Question will be posted online when you connect to the internet!", Toast.LENGTH_LONG).show();
    			}else{
        			String aux = controller.getQuestionId();
        			Intent i = new Intent(ctx,QuestionActivity.class);
        			i.putExtra(MainActivity.QUESTION_KEY, aux);
        			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        			ctx.startActivity(i);
    			}
    		}else{
    			Toast.makeText(ctx, "Something bad happened, try posting your question again!", Toast.LENGTH_LONG).show();
    		}
        	
        	if (pd!=null) {
				pd.dismiss();
			}        	
        }
    }
}
