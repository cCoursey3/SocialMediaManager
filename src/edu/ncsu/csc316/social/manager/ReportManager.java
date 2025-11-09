package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.social.data.Connection;
import edu.ncsu.csc316.social.data.Person;
import edu.ncsu.csc316.social.dsa.Algorithm;
import edu.ncsu.csc316.social.dsa.DSAFactory;
import edu.ncsu.csc316.social.dsa.DataStructure;
import edu.ncsu.csc316.social.data.PersonComparator;


/**
 * A report manager develops a report of either connections based
 * on a person's username or a connections based on a platform
 * @author Chloe Coursey 
 *
 */
public class ReportManager {

	
	/** a social media manager */
    private SocialMediaManager manager;	
	
	/** a sorter for people arrays */
	private Sorter<Person> personSorter;
	
	
	/** a map of person objects */
	private Map<String, Person> personMap;
	
	/** A default map for the default constructor */
    private static final DataStructure DEFAULT_MAP = DataStructure.LINEARPROBINGHASHMAP;


    /**
     * Constructs a report manager using a default map and two input files
     * @param peopleFile an input file of people objects
     * @param connectionFile an input file of connection objects
     * @throws FileNotFoundException if the file does not exist on the provided pathnames
     */
    public ReportManager(String peopleFile, String connectionFile) throws FileNotFoundException {
        this(peopleFile, connectionFile, DEFAULT_MAP);
    }

    /**
     * Constructs a social media manager using a map and two input files
     * @param peopleFile peopleFile an input file of people objects
     * @param connectionFile an input file of connection objects
     * @param mapType a type of map to use for the program
     * @throws FileNotFoundException if the file does not exist on the provided pathnames
     */
    public ReportManager(String peopleFile, String connectionFile, DataStructure mapType) throws FileNotFoundException {
        manager = new SocialMediaManager(peopleFile, connectionFile, mapType);
    	
    	DSAFactory.setMapType(mapType);
        DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.RADIX_SORT);

        personSorter = DSAFactory.getComparisonSorter(new PersonComparator());
    	personMap = manager.getPeople();
    	
    	
    }

    /**
     * returns a string of the connections for each person after sorting the people by username and the connections by
     * last name
     * @return fullString a complete string of connections
     */
    public String getConnectionsByPerson() {
    	Map<String, List<Connection>> connectionMap = manager.getConnectionsByPerson();
    	Map<String, List<Connection>> platformMap = manager.getConnectionsByPlatform(); 
    	if(personMap.isEmpty()) {
    		return "No people information was provided.";
    	} else if(platformMap.isEmpty()) {
    		return "No connections exist in the social media network.";
    	}
    	//put each map entry into a list
    	
    	String string = personString(connectionMap.entrySet());
    	
    	return string;
    	
    }
    
    /**
     * This helper method generates a string of a person's connections
     * @param entries the list of iterable entries in a map
     * @return sb the string builder of connections
     */
    private String personString(Iterable<Entry<String, List<Connection>>> entries) {
    	StringBuilder sb = new StringBuilder();
    	for(Entry<String, List<Connection>> e: entries) {
    		sb.append("Connections for ");
    		
    		Person p = personMap.get(e.getKey());
    		sb.append(p.getFirst()).append(" ").append(p.getLast()).append(" (").append(p.getId()).append(") {\n");
    		
    		List<Connection> list = e.getValue();
    		if(list.isEmpty()) {
    			sb.append("   No connections exist\n}\n");
        		continue;
    		}
 
        	for(Connection c: list) {
        		Person otherUser = personMap.get(getOtherUser(c, p));
        		sb.append("   ").append(otherUser.getFirst()).append(" ").append(otherUser.getLast()).append(" (")
        		.append(otherUser.getId()).append(") on ").append(c.getPlatform()).append(" since ").append(c.getDate()).append("\n");
        	}
        	sb.append("}\n");
    	}
    	return sb.toString();
    	
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
     * This method creates and returns a string of platforms and the connections on each
     * @return fullstring a string that contains all the connections on a platform
     */
    public String getConnectionsByPlatform() {
    	Map<String, List<Connection>> platformMap = manager.getConnectionsByPlatform(); 
    	if(personMap.isEmpty()) {
    		return "No people information was provided.";
    	} else if(platformMap.isEmpty()) {
    		return "No connections exist in the social media network.";
    	}
    	
    	
    	
    	return getString(platformMap);
    }

    /**
     * This helper method returns a string of connections for each platform
     * @param platformMap a map of platforms and the sorted connections
     * @return sb a string of connections
     */
    private String getString(Map<String, List<Connection>> platformMap) {
    	StringBuilder sb = new StringBuilder();
    	for(String s : platformMap) {
    		sb.append("Connections on ").append(s).append(" {\n");
    		List<Connection> list = platformMap.get(s);
    		for(int i = 0; i < list.size(); i++) {
    			Connection c = list.get(i);
    			sb.append("   ").append(c.getDate()).append(": ");
    			String users[] = c.getPeople();
    			Person[] people = {personMap.get(users[0]), personMap.get(users[1])};
    			
    			personSorter.sort(people);
    			sb.append(people[1].getFirst()).append(" ").append(people[1].getLast()).append(" (").append(people[1].getId()).append(") <--> ").append(people[0].getFirst()).append(" ").append(people[0].getLast()).append(" (").append(people[0].getId()).append(")\n");
    		}
    		sb.append("}\n");
    		
    	}
    	
    	return sb.toString();
    }
    
    
    
    
}