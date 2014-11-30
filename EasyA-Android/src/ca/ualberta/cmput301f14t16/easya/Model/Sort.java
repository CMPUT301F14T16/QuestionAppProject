package ca.ualberta.cmput301f14t16.easya.Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.Context;
import android.widget.Toast;

import ca.ualberta.cmput301f14t16.easya.View.SortEnum;

/**
 * 
 * Sort is a helper class that provides several methods that can sort lists of
 * {@link QuestionList} object. This class provides methods to sort by number of
 * upvotes, whether or not the question object has a picture, or by date. All
 * sorts may be performed from smallest to greatest, or greatest to smallest.
 * 
 * @author Klinton
 * @author Cauani
 */

public class Sort {

	public static List<QuestionList> sort(List<QuestionList> ql, SortEnum se){
		switch(se){
		case NEWEST:
			return sortDate(true, ql);
		case OLDEST:
			return sortDate(false, ql);
		case MOSTVOTES:
			return sortUpVote(true, ql);
		case LEASTVOTES:
			return sortUpVote(false, ql);
		case HASPICTURE:
			return sortPicture(true, ql);
		case HASNOPICTURE:
			return sortPicture(false, ql);
		case CLOSETOME:
			return sortDistance(false,ql);
		case FARFROMME:
			return sortDistance(true,ql);
		}
		return null;
	}
		
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
	public static List<QuestionList> sortUpVote(final boolean sortOrder,
			List<QuestionList> questionList) {
		Comparator<QuestionList> upvoteComparator = new Comparator<QuestionList>() {
			
			@Override
			public int compare(QuestionList lhs, QuestionList rhs) {
				Integer leftUpVote = lhs.getUpvotesInt();
				Integer rightUpVote = rhs.getUpvotesInt();
				
				return !sortOrder ? leftUpVote.compareTo(rightUpVote) : rightUpVote.compareTo(leftUpVote);
			}
		};
		Collections.sort(questionList, upvoteComparator);	
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
	public static List<QuestionList> sortPicture(boolean sortOrder,
			List<QuestionList> questionList) {
		if (sortOrder) {
		Collections.sort(questionList, new Comparator<QuestionList>() {
			public int compare(QuestionList questionList1,
					QuestionList questionList2) {
				return questionList1.getImage() && !questionList2.getImage() ? -1
						: !questionList1.getImage() && questionList2.getImage() ? 1
								: 0;
			}
		});
		} else {
			Collections.sort(questionList, new Comparator<QuestionList>() {
				public int compare(QuestionList questionList1,
						QuestionList questionList2) {
					return questionList1.getImage() && !questionList2.getImage() ? 1
							: !questionList1.getImage() && questionList2.getImage() ? -1
									: 0;
				}
			});
		}
		return questionList;
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
	public static List<QuestionList> sortDate(final boolean sortOrder,
		List<QuestionList> questionList) {
		Comparator<QuestionList> dateComparator = new Comparator<QuestionList>() {
		@Override
			public int compare(QuestionList lhs, QuestionList rhs) {
				Long leftTime = lhs.getDate() != null ? lhs.getDate().getTimeInMillis() : 0;
				Long rightTime = rhs.getDate() != null ? rhs.getDate().getTimeInMillis() : 0;
				
				return !sortOrder ? leftTime.compareTo(rightTime) : rightTime.compareTo(leftTime);
			}
		};
		Collections.sort(questionList, dateComparator);	
		return questionList;
	}
	
	public static List<QuestionList> sortDistance(final boolean sortOrder,
			List<QuestionList> questionList) {
		final double[] userLocation=Location.getLocationCoordinates();
		Comparator<QuestionList> locationComparator = new Comparator<QuestionList>() {
		@Override
			public int compare(QuestionList lhs, QuestionList rhs) {
				Double leftLocation = (lhs.getCoordinates()[0] != 0.0 && lhs.getCoordinates()[1] != 0.0) ? GeoCoder.toFindDistance(lhs.getCoordinates(),userLocation) : (double)-1.0;
				Double rightLocation = (rhs.getCoordinates()[0] != 0.0 && rhs.getCoordinates()[1] != 0.0) ? GeoCoder.toFindDistance(rhs.getCoordinates(),userLocation) : (double)-1.0;
				if (leftLocation == -1 || rightLocation == -1)
					return 0;
				return !sortOrder ? leftLocation.compareTo(rightLocation) : rightLocation.compareTo(leftLocation);
			}
		};
		Collections.sort(questionList, locationComparator);	
		return questionList;
	}
}
