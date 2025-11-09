package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;
import java.util.Iterator;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.social.data.Connection;
import edu.ncsu.csc316.social.data.ConnectionComparator;
import edu.ncsu.csc316.social.data.DateComparator;
import edu.ncsu.csc316.social.data.Person;
import edu.ncsu.csc316.social.data.PersonComparator;
import edu.ncsu.csc316.social.dsa.Algorithm;
import edu.ncsu.csc316.social.dsa.DSAFactory;
import edu.ncsu.csc316.social.dsa.DataStructure;
import edu.ncsu.csc316.social.io.InputReader;

/**
 * This class creates a social media manager which reads in an input and output file,
 * and creates a directory of people and a list of connections. Each are placed
 * into a map in a sorted order to be retrieved by reports in a later file.
 * @author Chloe Coursey 
 */
public class SocialMediaManager {
	
	/** A directory of people in a file */
	private List<Person> directory;
	
	/** A list of connections in a file */
	private List<Connection> connections;
	
	
	/** A map of people from the person file */
	private Map<String, Person> personMap;
	
	/** a sorter for connections */
	private Sorter<Connection> connectionSorter;
	
	/** a sorter of dates for platforms */
	private Sorter<Connection> dateSorter;
	
	
	
	/** a sorter for people arrays */
	private Sorter<Person> personSorter;
	
	
	/** A map of connections with key = a user and value = the list of connections */
	private Map<String, List<Connection>> connectionMap;

	/** A default map of type unordered linked map for the default constructor */
	private static final DataStructure DEFAULT_MAP = DataStructure.LINEARPROBINGHASHMAP;

	/**
	 * Constructs a social media manager using a default map and two input files
	 * @param peopleFile an input file of people objects
	 * @param connectionFile an input file of connection objects
	 * @throws FileNotFoundException if the file does not exist on the provided pathnames
	 */
    public SocialMediaManager(String peopleFile, String connectionFile) throws FileNotFoundException {
        this(peopleFile, connectionFile , DEFAULT_MAP);
    }

    /**
     * Constructs a social media manager using a map and two input files
     * @param peopleFile an input file of people objects
     * @param connectionFile an input file of connection objects
     * @param mapType a type of map to use for the program
     * @throws FileNotFoundException if the file does not exist on the provided pathnames
     */
    public SocialMediaManager(String peopleFile, String connectionFile, DataStructure mapType)
            throws FileNotFoundException {
    	DSAFactory.setMapType(mapType);
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);
        
        dateSorter = DSAFactory.getComparisonSorter(new DateComparator());
    	directory = InputReader.readPersonData(peopleFile);
        connections = InputReader.readConnectionData(connectionFile);
        connectionSorter = DSAFactory.getComparisonSorter(new ConnectionComparator());
        personSorter = DSAFactory.getComparisonSorter(new PersonComparator());
        
    }
    
    /**
     * This method calls for a list of people to be read in from the first file. 
     * Then it uses the list to places the people into a map
     * @return personMap the map key = a username and value = the person object
     */
    public Map<String, Person> getPeople() {
    	personMap = DSAFactory.getMap(null);
        for (Person p: directory) {
        	String username = p.getId();
        	if(personMap.get(username) != null) {
        		personMap.remove(username);
        	}
        	personMap.put(username, p);
        	
        }
        return personMap;
    }
    


    /**
     * This method creates and fills a map of connections in sorted order based on each user. This allows for
     * easy retrieval and usage in the reports.
     * @return connectionMap a map of connections where k = a user and value = the user's connections as a list
     */
    public Map<String, List<Connection>> getConnectionsByPerson() {
    	getPeople();
    	connectionMap = DSAFactory.getMap(null);
        for(String username: personMap) {
        	List<Connection> list = DSAFactory.getIndexedList();
        	connectionMap.put(username, list);
        }
        for (Connection c : connections) {
            String[] users = c.getPeople();
            List<Connection> userConnections1 = connectionMap.get(users[0]);
            List<Connection> userConnections2 = connectionMap.get(users[1]);
            if(userConnections2 == null || userConnections1 == null) {
            	break;
            }
            userConnections1.addLast(c);
            userConnections2.addLast(c);
        }
        
        sortConnections(connectionMap);
        return connectionMap;
        
    }
    /**
     * This method finds a connection based on a username, it helps with efficiency
     * @param otherUser the user trying to find in a list of connections
     * @param user1 the user finding the connections for
     * @param list a list of connections for user1
     * @return connection or null if no connection is found
     */
    private Connection getConnectionBasedonUser(Person otherUser, Person user1, List<Connection> list) {Iterator<Connection> iterator = list.iterator();
         while (iterator.hasNext()) {
             Connection connection = iterator.next();
             Person temp = personMap.get(getOtherUser(connection, user1));
             if (temp.compareTo(otherUser) == 0) {
                 iterator.remove();
                 return connection;
             }
         }
         return null; 
    	
    }
    
    
    
    /**
     * This private method sorts connections based on the people
     * in the connection and the dates the connections occurred.
     * @param map a map of the connections
     */
    private void sortConnections(Map<String, List<Connection>> map){
    	for(String username : map) {
    		List<Connection> list = map.get(username);
    		Connection[] cArr = new Connection[list.size()];
    		Person[] pArr = new Person[list.size()];
    		Person other = personMap.get(username);
    		
    		for(int i = 0; i < list.size(); i++) {
    			pArr[i] =  personMap.get(getOtherUser(list.get(i), other));
    		}
    		
    		personSorter.sort(pArr);
    		
    		for(int i = 0; i < pArr.length; i++) {
    			cArr[i] = getConnectionBasedonUser(pArr[i], other, list);
    		}
    		
    		connectionSorter.sort(cArr);
    		
    		for(int i = 0; i < cArr.length; i++) {
    			list.addLast(cArr[i]);
    		}
    		
    	}
    }

    
    /**
     * This helper method gets the user a person p is connected with
     * @param c a connection to check
     * @param p the person who is connected with another user
     * @return user the user other than person p in a connection
     */
    private String getOtherUser(Connection c, Person p) {
    	String[] users = c.getPeople();
        String userId = p.getId();
        return users[0].equals(userId) ? users[1] : users[0];
    }

    
    
    /**
     * This method creates a map of platforms with values equivalent to the connections
     * made on the platform
     * @return platformMap a map of platform keys with and values equivalent to the connections list
     */
    public Map<String, List<Connection>> getConnectionsByPlatform() {
    	//sort by time, list name by other user, 
    	Map<String, List<Connection>> platformMap = DSAFactory.getMap(null);
    	for (Connection c : connections) {
    		String platform = c.getPlatform();
    		 List<Connection> list = platformMap.get(platform);
             if (list == null) {
                 list = DSAFactory.getIndexedList();
                 platformMap.put(platform, list);
             }
             list.addLast(c);
         }
    	
    	
    	

    	sortPlatformConnections(platformMap);
         return platformMap;
    	
    }
    
    /**
     * This helper method sorts the connections in a platform map
     * based on the dates they were made and the users in the connection
     * @param map the map of platforms with key = platform and value = list of connections
     */
    private void sortPlatformConnections(Map<String, List<Connection>> map) {
    	for(String s : map) {
    		List<Connection> list = map.get(s);
    		Connection[] cArr = new Connection[list.size()];
    		
    		for(int i = 0; i < list.size(); i++) {
    			cArr[i] = list.get(i);
    		}
    		dateSorter.sort(cArr);
    		
    		for(int i = 0; i < cArr.length; i++) {
    			list.set(i, cArr[i]);
    		}
    	}
    	
    	
    }
    
}
