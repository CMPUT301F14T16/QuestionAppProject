package ca.ualberta.cmput301f14t16.easya.Model;

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
	 * Sorts the passed list by total number of upvotes received.
	 * 
	 * @param sortOrder
	 *            If True, the list will be sorted by greatest to smallest, if
	 *            False, the list will be sorted by smallest to greatest.
	 * @param questionList
	 *            The list of {@link Topic} objects to be sorted.
	 * @return The sorted list.
	 */
	public static List<QuestionList> sortUpVote(boolean sortOrder,
			List<QuestionList> questionList) {
		if (sortOrder) {
			Collections.sort(questionList, new Comparator<QuestionList>() {
				public int compare(QuestionList questionList1,
						QuestionList questionList2) {
					// int cmp = a > b ? +1 : a < b ? -1 : 0;
					// compares the 2 upVotes.
					return questionList1.getUpvotes().length() > questionList2
							.getUpvotes().length() ? 1 : Integer
							.parseInt(questionList2.getUpvotes()) < Integer
							.parseInt(questionList1.getUpvotes()) ? -1 : 0;
				}
			});
		} else {
			Collections.sort(questionList, new Comparator<QuestionList>() {
				public int compare(QuestionList questionList1,
						QuestionList questionList2) {
					// int cmp = a > b ? +1 : a < b ? -1 : 0;
					// compares the 2 upVotes.
					return questionList1.getUpvotes().length() > questionList2
							.getUpvotes().length() ? 1 : Integer
							.parseInt(questionList1.getUpvotes()) < Integer
							.parseInt(questionList2.getUpvotes()) ? -1 : 0;
				}
			});
		}

		return questionList;
	}

	/**
	 * Sorts the passed list by the presence of a picture.
	 * 
	 * @param sortOrder
	 *            If True, the list will be sorted with the {@link Topic}
	 *            objects containing pictures first. If False, the list will be
	 *            sorted with the {@link Topic} objects not containing pictures
	 *            first.
	 * @param questionList
	 *            The list of {@link Topic} objects to be sorted.
	 */
	public static void pictureSort(boolean sortOrder,
			List<QuestionList> questionList) {
		Collections.sort(questionList, new Comparator<QuestionList>() {
			public int compare(QuestionList questionList1,
					QuestionList questionList2) {
				return questionList1.getImage() && !questionList2.getImage() ? 1
						: !questionList1.getImage() && questionList2.getImage() ? -1
								: 0;
			}
		});
	}

	/**
	 * Sorts the passed list by date.
	 * 
	 * @param sortOrder
	 *            If True, the list will be sorted by most newest to oldest, if
	 *            False, the list will be sorted by oldest to newest.
	 * @param questionList
	 *            The list of {@link Topic} objects to be sorted.
	 * @return The sorted list.
	 */
	public static List<QuestionList> dateSort(boolean sortOrder,
			List<QuestionList> questionList) {
		if (sortOrder) {
			Collections.sort(questionList, new Comparator<QuestionList>() {
				public int compare(QuestionList questionList1,
						QuestionList questionList2) {
					return questionList2.getDate().compareTo(
							questionList1.getDate());
				}
			});
		} else {
			Collections.sort(questionList, new Comparator<QuestionList>() {
				public int compare(QuestionList questionList1,
						QuestionList questionList2) {
					return questionList1.getDate().compareTo(
							questionList2.getDate());
				}
			});
		}
		return questionList;
	}

}
