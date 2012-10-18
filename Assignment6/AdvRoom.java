/*
 * File: AdvRoom.java
 * ------------------
 * This file defines a class that models a single room in the
 * Adventure game.
 */

import acm.util.*;
import java.io.*;
import java.util.*;

/**
 * This class defines a single room in the Adventure game.  A room
 * is characterized by the following properties:
 *
 * <ul>
 * <li>A room number, which must be greater than zero
 * <li>Its name, which is a one-line string identifying the room
 * <li>Its description, which is a line array describing the room
 * <li>A list of objects contained in the room
 * <li>A flag indicating whether the room has been visited
 * <li>A motion table specifying the exits and where they lead
 * </ul>
 *
 * The external format of the room file is described in the
 * assignment handout.
 */

public class AdvRoom extends AdvRoomMagicSuperclass {

    public AdvRoom() {
        description = new ArrayList<String>();
        motionTable = new HashMap<String, AdvMotionTableEntry>();
        objects = new ArrayList<AdvObject>();
        visited = false;
    }
    
    public void setRoomNumber(int n) {
        number = n;
    }
/**
 * Returns the room number.
 *
 * @return The room number
 */
	public int getRoomNumber() {
		return number; // Replace with your code
	}

	public void setName(String name) {
	    this.name = name;
	}
/**
 * Returns the room name, which is its one-line description.
 *
 * @return The room name
 */
	public String getName() {
		return name; // Replace with your code
	}

	public void addMotionTableEntry(AdvMotionTableEntry entry) {
	    motionTable.put(entry.getDirection(), entry);
	}
	
	public AdvMotionTableEntry getEntry(String dir) {
	    return motionTable.get(dir);
	}
	
/**
 * Adds an object to the list of objects in the room.
 *
 * @param obj The <code>AdvObject</code> to be added
 */
	public void addObject(AdvObject obj) {
		objects.add(obj);
	}

/**
 * Removes an object from the list of objects in the room.
 *
 * @param obj The <code>AdvObject</code> to be removed
 */
	public void removeObject(AdvObject obj) {
		objects.remove(obj); // Replace with your code
	}

/**
 * Checks whether the specified object is in the room.
 *
 * @param obj The <code>AdvObject</code> being tested
 * @return <code>true</code> if the object is in the room
 */
	public boolean containsObject(AdvObject obj) {
		return objects.contains(obj); // Replace with your code
	}

/**
 * Returns the number of objects in the room.
 *
 * @return The number of objects in the room
 */
	public int getObjectCount() {
		return objects.size(); // Replace with your code
	}

/**
 * Returns the specified element from the list of objects in the room.
 *
 * @return The <code>AdvObject</code> at the specified index position
 */
	public AdvObject getObject(int index) {
		return objects.get(index); // Replace with your code
	}

	
	public void addToDescription(String des) {
	    description.add(des);
	}
/**
 * Returns a string array giving the long description of the room.
 *
 * @return A string array giving the long description of the room
 */
	public String[] getDescription() {
//		return super.getDescription(); // Replace with your code
	    String[] arr = new String[description.size()];
	    description.toArray(arr);
	    return arr;
	}

/**
 * Sets a flag indicating whether this room has been visited.
 * Calling <code>setVisited(true)</code> means that the room has
 * been visited; calling <code>setVisited(false)</code> restores
 * its initial unvisited state.
 *
 * @param flag The new state of the "visited" flag
 */
	public void setVisited(boolean flag) {
		visited = flag;
	}

/**
 * Returns <code>true</code> if the room has previously been visited.
 *
 * @return <code>true</code> if the room has been visited
 */
	public boolean hasBeenVisited() {
		return visited; // Replace with your code
	}

/**
 * Returns the motion table associated with this room, which is an
 * array of directions, room numbers, and key objects stored
 * in an <code>AdvMotionTableEntry</code>.
 *
 * @return The array of motion table entries associated with this room
 */
	public AdvMotionTableEntry[] getMotionTable() {
	    AdvMotionTableEntry arr[] = new AdvMotionTableEntry[motionTable.size()];
		motionTable.values().toArray(arr);
		return arr;
	}
	
	public AdvMotionTableEntry getMotionTableEntry(String key) {
	    return motionTable.get(key);
	}

/**
 * Creates a new room by reading its data from the specified
 * reader.  If no data is left in the reader, this method returns
 * <code>null</code> instead of an <code>AdvRoom</code> value.
 * Note that this is a static method, which means that you need
 * to call
 *
 *<pre><code>
 *     AdvRoom.readRoom(rd)
 *</code></pre>
 *
 * @param rd The reader from which the room data is read 
 */
	public static AdvRoom readRoom(BufferedReader rd) {
		//return AdvRoomMagicSuperclass.readRoom(rd); // Replac
	    AdvRoom room = new AdvRoom();
	    try {
	        room.setRoomNumber(Integer.parseInt(rd.readLine()));
	        room.setName(rd.readLine());
	        // loop through the description
	        while(true) {
	            String line = rd.readLine();
	            if(line.equals(SENTINEL))
	                break;
	            room.addToDescription(line);
	        }
	        // loop through the motion entries
	        while(true) {
	            String line = rd.readLine();
	            AdvMotionTableEntry entry = AdvMotionTableEntry.createEntryFromString(line);
	            if(entry == null)
	                break;
	            room.addMotionTableEntry(entry);
	        }
	        return room;
	    } catch (Exception e) {
	    }
	    return null;
	    
	}

/* Private instance variables */
	private static String SENTINEL = "-----";
	// Add your own instance variables here
	private String name;
	private int number;
	private boolean visited;
	private ArrayList<String> description;
	private HashMap<String, AdvMotionTableEntry> motionTable;
	private ArrayList<AdvObject> objects;

}
