package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Sort is a helper class that provides several methods that can sort lists of
 * {@link QuestionList} object. This class provides methods to sort by number of
 * upvotes, whether or not the question object has a picture, or by date. All
 * sorts may be performed from smallest to greatest, or greatest to smallest.
 * 
 * @author Klinton
 * 
 */

public class Sort {
	
	/**
	 * Creates a comparable to sort by up votes. 
	 * 
	 * @param sortOrder 
	 * 			checks if the sort is from greatest to smallest or vice versa.
	 * @param questionList
	 *           list of topics to be sorted.
	 *           
	 */
	public static void sortUpVote(boolean sortOrder,
			List<QuestionList> questionList) {
		Collections.sort(questionList, new Comparator<QuestionList>(){
			public int compare(QuestionList questionList1, QuestionList questionList2){
				// int cmp = a > b ? +1 : a < b ? -1 : 0;
				// compares the 2 upVotes.
				return questionList1.getUpvotes().length() > questionList2.getUpvotes().length() ? 1 :
					Integer.parseInt(questionList1.getUpvotes()) < Integer.parseInt(questionList2.getUpvotes()) ? -1 : 0;
			}
		});
	}
	
	/**
	 * Sorts by if it has or doesn't have a picture. 
	 * 
	 * @param sortOrder 
	 * 			checks if the sort is from greatest to smallest or vice versa.
	 * @param questionList
	 *           list of topics to be sorted.
	 *           
	 */
	public static void pictureSort(boolean sortOrder,
			List<QuestionList> questionList) {
		Collections.sort(questionList, new Comparator<QuestionList>(){
			public int compare(QuestionList questionList1, QuestionList questionList2){
				return questionList1.getImage() && !questionList2.getImage()? 1 :
						!questionList1.getImage() && questionList2.getImage()? -1 : 0;
			}
		});
	}
	
	/**
	 * Sorts topics by date. 
	 * 
	 * @param sortOrder 
	 * 			checks if the sort is from greatest to smallest or vice versa.
	 * @param questionList
	 *           list of topics to be sorted.
	 *           
	 */
	public static List<QuestionList> dateSort(boolean sortOrder,
			List<QuestionList> questionList) {
		if (sortOrder == true) {
		Collections.sort(questionList, new Comparator<QuestionList>(){
			public int compare(QuestionList questionList1, QuestionList questionList2){
				return questionList2.getDate().compareTo(questionList1.getDate());
			}
		});
		}
		else {
			Collections.sort(questionList, new Comparator<QuestionList>(){
				public int compare(QuestionList questionList1, QuestionList questionList2){
					return questionList1.getDate().compareTo(questionList2.getDate());
				}
			});
		}
		return questionList;
	}
	
}

