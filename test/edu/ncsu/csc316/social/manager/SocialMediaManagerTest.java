package edu.ncsu.csc316.social.manager;

import static org.junit.jupiter.api.Assertions.*;


import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.social.data.Connection;
import edu.ncsu.csc316.social.data.Person;

/**
 * This class test the social media sorting algorithms and placement
 * into maps by using two valid files and one invalid file
 * @author Chloe Coursey
 */


class SocialMediaManagerTest {

	/** a social media manager to construct */
	private SocialMediaManager sm;
	
	/**a map of people where key = username and value = the person information with id, first, and last name*/
	private Map<String, Person> peopleMap;
	
	/**a map of platforms and the connections on them */
	private Map<String, List<Connection>> platformMap;
	
	/**a map of connections based on a person's username*/
	private Map<String, List<Connection>> connectionMap;
	
	/**an input file with valid people in it */
	private static final String PEOPLE_FILE = "input/people1.txt";
	
	/**a full input file of connections */
	private static final String CONNECTION_FILE = "input/valid_connections.txt";
	
	/**
	 * sets up a valid social media manager using people1.txt and valid_connections.txt
	 */
	@BeforeEach
	public void setUp() {
		try {
			sm = new SocialMediaManager(PEOPLE_FILE, CONNECTION_FILE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * This tests the map of people objects by checking
	 * the size of the map and checking the get method
	 */
	@Test
	public void testGetPeople() {
		peopleMap = sm.getPeople();
		assertEquals(8, peopleMap.size());
		
		assertEquals("Ralph", peopleMap.get("RGoodmann23").getFirst());
		assertEquals("Smith", peopleMap.get("PSmith4567").getLast());
		
	}
	
	/**
	 * This tests the map of person keys and their connections (value)
	 * By checking if they are in sorted order and the size of the connection lists
	 */
	@Test
	public void testGetConnectionsByPerson() {
		//System.out.println(System.currentTimeMillis());
		connectionMap = sm.getConnectionsByPerson();
		//System.out.println(System.currentTimeMillis());
		assertEquals(8, connectionMap.size());
		
		List<Connection> rGList = connectionMap.get("RGoodmann23");
		
		
		assertEquals("UT278XWQ", rGList.get(0).getId());
		assertEquals("P0345DQW", rGList.get(1).getId());
		assertEquals("QR620H4T", rGList.get(2).getId());
		assertEquals("62SFMBQ1", rGList.get(3).getId());
		assertEquals("EDH4JD39", rGList.get(4).getId());
		assertEquals("PO35DXM0", rGList.get(5).getId());
		
		assertEquals(6, connectionMap.get("RGoodmann23").size());
		assertEquals(3, connectionMap.get("BCornfield654").size());
		assertEquals(2, connectionMap.get("JRenner987").size());
		assertEquals(4, connectionMap.get("GSmith123456").size());
		assertEquals(4, connectionMap.get("CCandid123").size());
		assertEquals(4, connectionMap.get("PSmith4567").size());
		assertEquals(5, connectionMap.get("RGoodmann45").size());
		

	}
	
	/**
	 * This tests the map of platforms where key = a platform string and value = the connections on it
	 * and the sorted connections.
	 */
	@Test
	public void testGetConnectionsByPlatform() {
		platformMap = sm.getConnectionsByPlatform();
		assertEquals(6, platformMap.size());
		
		assertEquals(1, platformMap.get("Telegram").size());
		assertEquals(3, platformMap.get("Facebook").size());
		assertEquals(4, platformMap.get("Instagram").size());
		assertEquals(2, platformMap.get("Twitch").size());
		assertEquals(2, platformMap.get("LinkedIn").size());
		assertEquals(2, platformMap.get("Twitter").size());
	}

}
