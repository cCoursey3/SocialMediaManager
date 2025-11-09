package edu.ncsu.csc316.social.data;

import java.util.Comparator;


import java.util.Date;

import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.social.dsa.Algorithm;
import edu.ncsu.csc316.social.dsa.DSAFactory;

/**
 * This method compares connections after checking if they have the same 
 * users in the connection. This is used for comparing connections for the
 * connection by person report
 * @author Chloe Coursey
 *
 */
public class ConnectionComparator implements Comparator<Connection> {

	@Override
	public int compare(Connection one, Connection two) {
		DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
		Sorter<String> sorter = DSAFactory.getComparisonSorter(null);
		//compare username and then compare by date
		String[] users1 = one.getPeople();
		String[] users2 = two.getPeople();
		
		
		sorter.sort(users1);
		sorter.sort(users2);


		if(users1[0].equals(users2[0]) && users1[1].equals(users2[1])) {
			Date d1 = one.getDate();
			Date d2 = two.getDate();

			if(d1.compareTo(d2) > 0) {
				return 1;
			} if (d1.compareTo(d2) < 0) {
				return -1;
			}
		} 
		return 0;		
	}

}
