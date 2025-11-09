package edu.ncsu.csc316.social.data;

import java.util.Comparator;

import java.util.Date;


/**
 * This comparator compares connections based on their dates
 * it is used for the connections by platform report
 * @author Chloe Coursey
 *
 */
public class DateComparator implements Comparator<Connection> {
	
	/**
	 * Compares people based on names in ascending order
	 * @param one a Person to be compared to another
	 * @param two a Person to be compared to another
	 * @return 1 or -1 if the order of two people should be switched and 0 if equal
	 */
	@Override
	public int compare(Connection one, Connection two) {
			Date d1 = one.getDate();
			//System.out.println(d1);
			Date d2 = two.getDate();
			//System.out.println(d2);

			if(d1.compareTo(d2) > 0) {
				return 1;
			} if (d1.compareTo(d2) < 0) {
				return -1;
			} else if(d1.compareTo(d2) == 0) {
				String connectionId1 = one.getId();
				String connectionId2 = two.getId();
				if(connectionId1.compareTo(connectionId2) > 0) {
					return 1;
				} else if (connectionId1.compareTo(connectionId2) < 0){
					return -1;
				}
			}
		return 0;		
	}

}
