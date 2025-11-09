package edu.ncsu.csc316.social.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.social.dsa.Algorithm;
import edu.ncsu.csc316.social.dsa.DSAFactory;

/**
 * This class tests the connection comparator used
 * @author Chloe Coursey
 *
 */
class ConnectionComparatorTest {
	/** a sorter of Connections */
	private Sorter<Connection> s;
	
	/** a comparator of connections */
	private ConnectionComparator comparator;
	
	/**an empty, arbitrary connection */
	private Connection one;
	
	/**an empty, arbitrary connection */
	private Connection two;
	
	/**an empty, arbitrary connection */
	private Connection three;
	
	/**an empty, arbitrary connection */
	private Connection four;
	
	/**
	 * This method sets up managers and comparators for the other tests 
	 */
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		comparator = new ConnectionComparator();
		DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
		
		s = DSAFactory.getComparisonSorter(new ConnectionComparator());
		 
		String[] arr1 = {"RGoodmann234", "BCornfield654"};
		String[] arr2 = {"BCornfield654", "CCandid123"};
		String[] arr3 = {"RGoodmann234", "BCornfield654"};
		
		one = new Connection("QR620H4T", arr1, new Date("Sun Aug 05 04:42:00 EDT 2018"), "Telegram");
		two = new Connection("DFR67SMX", arr2, new Date("Mon Feb 10 09:45:13 EST 2020,"), "Twitter");
		three = new Connection("p0345DQW", arr3, new Date("Mon Feb 05 12:42:00 EST 2018"), "Instagram");
		four = new Connection("aEYTXNMO", arr3, new Date("Mon Feb 05 12:42:00 EST 2018"), "LinkedIn");
	}

	/**
	 * Tests the compare method
	 */
	@Test
	public void testCompare() {
		assertTrue(comparator.compare(one, two) == 0);
		assertFalse(comparator.compare(two, three) > 0);


		assertFalse(comparator.compare(three, one) > 0);
		
		
	}

	/**
	 * Tests the actual comparator
	 */
	@Test
	public void testComparator() {
		Connection[] cArr = {one, two, three, four};
		s.sort(cArr);
		
		assertEquals("Instagram", cArr[1].getPlatform());
		assertEquals("LinkedIn", cArr[0].getPlatform());
		assertEquals("Twitter", cArr[2].getPlatform());
		assertEquals("Telegram", cArr[3].getPlatform());
	}
}