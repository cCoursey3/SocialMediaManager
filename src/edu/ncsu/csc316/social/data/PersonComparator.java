package edu.ncsu.csc316.social.data;

import java.util.Comparator;

/**
 * Comparator for comparing person's names based on their last name, first name,
 * and their id
 * @author Chloe Coursey
 */
public class PersonComparator implements Comparator<Person> {

	/**
	 * Compares people based on names in ascending order
	 * @param one a Person to be compared to another
	 * @param two a Person to be compared to another
	 * @return 1 or -1 if the order of two people should be switched and 0 if equal
	 */
	@Override
	public int compare(Person one, Person two) {
		if(one.getLast().equals(two.getLast())) {
			if(one.getFirst().equals(two.getFirst())) {
				if (one.getId().compareTo(two.getId()) > 0) {
					return -1;
				} else {
					return 1;
				}
			} else if (one.getFirst().compareTo(two.getFirst()) > 0) {
				return -1;
			} else if (one.getFirst().compareTo(two.getFirst()) < 0) {
				return 1;
			}
		} else if (one.getLast().compareTo(two.getLast()) > 0) {
			return -1;
		}
		return 1;
		
	}
}
