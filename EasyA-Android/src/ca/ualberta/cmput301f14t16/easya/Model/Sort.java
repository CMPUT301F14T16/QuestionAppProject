package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * @author Klinton
 *
 * Sorts by the method specified.
 * These methods sort by up vote, picture, and date. 
 *  
 */

public class Sort {
	
	/**
	 * Creates a comparable to sort by up votes. 
	 * 
	 * @param sortOrder 
	 * 			checks if the sort is from greatest to smallest or vice versa.
	 * @param topicList
	 *           list of topics to be sorted.
	 *           
	 */
	public static void sortUpVote(boolean sortOrder,
			ArrayList<Topic> topicList) {
		Collections.sort(topicList, new Comparator<Topic>(){
			public int compare(Topic topic1, Topic topic2){
				// int cmp = a > b ? +1 : a < b ? -1 : 0;
				// compares the 2 ints.
				return topic1.getUpVoteCount() > topic2.getUpVoteCount() ? 1 :
						topic1.getUpVoteCount() < topic2.getUpVoteCount() ? -1 : 0;
			}
		});
	}
	
	/**
	 * Sorts by if it has or doesn't have a picture. 
	 * 
	 * @param sortOrder 
	 * 			checks if the sort is from greatest to smallest or vice versa.
	 * @param topicList
	 *           list of topics to be sorted.
	 *           
	 */
	public static void pictureSort(boolean sortOrder,
			ArrayList<Topic> topicList) {
		Collections.sort(topicList, new Comparator<Topic>(){
			public int compare(Topic topic1, Topic topic2){
				return topic1.hasPicture() && !topic2.hasPicture()? 1 :
						!topic1.hasPicture() && topic2.hasPicture()? -1 : 0;
			}
		});
	}
	
	/**
	 * Sorts topics by date. 
	 * 
	 * @param sortOrder 
	 * 			checks if the sort is from greatest to smallest or vice versa.
	 * @param topicList
	 *           list of topics to be sorted.
	 *           
	 */
	public static void dateSort(boolean sortOrder,
			ArrayList<Topic> topicList) {
		Collections.sort(topicList, new Comparator<Topic>(){
			public int compare(Topic topic1, Topic topic2){
				return topic1.getDate().compareTo(topic2.getDate());
			}
		});
	}
	
}

