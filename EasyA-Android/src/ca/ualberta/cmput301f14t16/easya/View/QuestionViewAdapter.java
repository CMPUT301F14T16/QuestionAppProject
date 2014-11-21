package ca.ualberta.cmput301f14t16.easya.View;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import ca.ualberta.cmput301f14t16.easya.R;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.submitReplyTask;
import ca.ualberta.cmput301f14t16.easya.Controller.ATasks.upvoteTask;
import ca.ualberta.cmput301f14t16.easya.Exceptions.NoContentAvailableException;
import ca.ualberta.cmput301f14t16.easya.Model.Answer;
import ca.ualberta.cmput301f14t16.easya.Model.MainModel;
import ca.ualberta.cmput301f14t16.easya.Model.Question;
import ca.ualberta.cmput301f14t16.easya.Model.Reply;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    
    private OnEditorActionListener keyAction = new OnEditorActionListener() {
    	@Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    		if (event==null) {
    		      if (actionId==EditorInfo.IME_ACTION_DONE);    		      
    		      else if (actionId==EditorInfo.IME_ACTION_NEXT);    		      
    		      else return false;
		    }else if (actionId==EditorInfo.IME_NULL) { 
		      if (event.getAction()==KeyEvent.ACTION_DOWN); 
		      else return true;  
		    }else return false; 
    		Context ctx = v.getContext();
        	BasicNameValuePair vp = (BasicNameValuePair)v.getTag();
        	String body = v.getText().toString();
        	(new submitReplyTask(ctx, vp, body, v)).execute();
        	return true;    		
        }
    };    
    
    private OnClickListener upVote = new OnClickListener() {
        @Override
        public void onClick(View v) {
            (new upvoteTask(v.getContext(), (BasicNameValuePair)v.getTag())).execute();
        }
    };    

    public void build() {
    	try{
    		clear();
	    	inflateQuestion();
	    	for (Answer a : q.getAnswers()){
	    		inflateAnswer(a);
	    	}
    	}catch(NoContentAvailableException ex){
    		//TODO: display the NoContentAvailable screen
    	}
    }
    
    public void clear(){
    	container.removeAllViews();
    }
    
    private void inflateQuestion() throws NoContentAvailableException{
    	byte[] imagefile;
    	View v = inflater.inflate(R.layout.question_question_fragment, (ViewGroup)container, false);
    	    	
    	TextView title, body, upvotescount, authordate;
    	LinearLayout replies;
    	EditText addReply;

    	ImageView image;
    	
    	title = (TextView)v.findViewById(R.id.question_fragment_title);
    	body = (TextView)v.findViewById(R.id.question_fragment_body);
    	authordate = (TextView)v.findViewById(R.id.question_fragment_authorDate);
    	upvotescount = (TextView)v.findViewById(R.id.question_fragment_upvoteText);
    	addReply = ((EditText)v.findViewById(R.id.question_fragment_submitReplyEdt));
    	
    	image = ((ImageView)v.findViewById(R.id.question_fragment_image));
    	
    	replies = (LinearLayout)v.findViewById(R.id.question_fragment_replies_list);    	
    	replies = inflateReplies(replies, q.getReplies());
    	addReply.setTag(new BasicNameValuePair(q.getId(), null));
    	addReply.setOnEditorActionListener(keyAction);
    	addReply.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);
    	
    	title.setText(q.getTitle());
    	body.setText(q.getBody());
    	authordate.setText(q.getAuthorDate());
    	upvotescount.setText(q.getUpVoteCountString());
   
    	// Handle Question image
    	byte[] qImage = q.getImage();
    	if (qImage != null) {
	    	byte[] decodedBytes = Base64.decode(qImage, 1);
			InputStream is = new ByteArrayInputStream(decodedBytes);
			Bitmap bmp = BitmapFactory.decodeStream(is);
			image.setImageBitmap(bmp);
    	} else {
    		image.setVisibility(View.GONE);
    	}
    	
    	((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setTag(new BasicNameValuePair(q.getId(), null));
    	((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setOnClickListener(upVote);
    	
    	if (q.checkUpVote(MainModel.getInstance().getCurrentUser())){
    		((ImageButton)v.findViewById(R.id.question_fragment_upvoteBtn)).setImageResource(R.drawable.ic_action_good_selected);
    	}    	
    	container.addView(v);
    }
    
    private void inflateAnswer(Answer a) throws NoContentAvailableException{
    	byte[] imagefile2;
    	View v = inflater.inflate(R.layout.question_answer_fragment, (ViewGroup)container, false);
    	    	
    	TextView body, upvotescount, authordate;
    	LinearLayout replies;
    	EditText addReply;
    	ImageView image2;
    	    	
    	body = (TextView)v.findViewById(R.id.answer_fragment_body);
    	authordate = (TextView)v.findViewById(R.id.answer_fragment_authorDate);
    	upvotescount = (TextView)v.findViewById(R.id.answer_fragment_upvoteText);
    	addReply = ((EditText)v.findViewById(R.id.answer_fragment_submitReplyEdt));
    	image2 = ((ImageView)v.findViewById(R.id.answer_fragment_image));
    	    	
    	replies = (LinearLayout)v.findViewById(R.id.answer_fragment_replies_list);
    	replies = inflateReplies(replies, a.getReplies());
    	addReply.setTag(new BasicNameValuePair(q.getId(), a.getId()));
    	addReply.setOnEditorActionListener(keyAction);
    	addReply.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);
    	try {
    		imagefile2 = a.getImage();
    		byte[] decodedBytes = Base64.decode(imagefile2, 1);
    		InputStream is = new ByteArrayInputStream(decodedBytes);
    		Bitmap bmp = BitmapFactory.decodeStream(is);
    		image2.setImageBitmap(bmp);
    	} catch (NullPointerException e) {
    		e.printStackTrace();
    	}
    	
    	body.setText(a.getBody());
    	authordate.setText(a.getAuthorDate());
    	upvotescount.setText(a.getUpVoteCountString());
    	
    	((ImageButton)v.findViewById(R.id.answer_fragment_upvoteBtn)).setTag(new BasicNameValuePair(q.getId(), a.getId()));
    	((ImageButton)v.findViewById(R.id.answer_fragment_upvoteBtn)).setOnClickListener(upVote);
    	
    	if (a.checkUpVote(MainModel.getInstance().getCurrentUser())){
    		((ImageButton)v.findViewById(R.id.answer_fragment_upvoteBtn)).setImageResource(R.drawable.ic_action_good_selected);
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
