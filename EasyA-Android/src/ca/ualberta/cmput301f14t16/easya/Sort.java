package ca.ualberta.cmput301f14t16.easya;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * @author Klinton Shmeit
 *
 */
public class Sort {
	static Random r = new Random();
	//TODO make the sort algorithms and test with the view
//	public static ArrayList<Topic> sortUpVote(boolean sortOrder,
//			ArrayList<Topic> list) {
//		 if (list.size() <= 1) return list;
//	        int rotationplacement = r.nextInt(list.size());
//	        int rotation = list.get(rotationplacement);
//	        list.remove(rotationplacement);
//	        ArrayList<Integer> lower = new ArrayList<Integer>();
//	        ArrayList<Integer> higher = new ArrayList<Integer>();
//	        for (int num : list) {
//	            if (num <= rotation)
//	                lower.add(num);
//	            else
//	                higher.add(num);
//	        }
//	        sortUpVote(lower);
//	        sortUpVote(higher);
//
//	        list.clear();
//	        list.addAll(lower);
//	        list.add(rotation);
//	        list.addAll(higher);
//	        return list;
//	}
	public static void pictureSort(boolean sortOrder,
			ArrayList<Topic> topicList) {
	}
	public static void dateSort(boolean sortOrder,
			ArrayList<Topic> topicList) {
	}
	
}
