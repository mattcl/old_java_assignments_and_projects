/*
 * File: Adventure.java
 * Names: <fill in>
 * Section leader who is grading the assignment: <fill in>
 * -------------------------------------------------------
 * This program plays the Adventure game from Assignment #6.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

/**
 * This class is the main program class for the Adventure game.
 * In the final version, <code>Adventure</code> should extend
 * <code>ConsoleProgram</code> directly.
 */

public class Adventure extends AdventureMagicSuperclass {

   private ArrayList<AdvRoom> rooms;
   private HashMap<String, AdvObject> heldObjects;
   private HashMap<String, String> synonyms;
   private int currentRoom;
    
/**
 * Runs the Adventure program.
 */
	public void run() {
		//super.run();  // Replace with your code
	    
	    println("Welcome to the Adventure!");
	    loadFromFile();
	    play();
	}

// Add your own private methods and instance variables here
	
	private void play() {
	    currentRoom = 0;
	    while(true) {
	        printRoomDescription();
	        markRoomAsVisited();
	        getInput();
	    }
	}
	
	private void printRoomDescription() {
	    AdvRoom room = rooms.get(currentRoom);
	    if(room.hasBeenVisited()) {
	        println(room.getName());
	    } else {
	        String[] des = room.getDescription();
	        for(int i = 0; i < des.length; i++) {
	            println(des[i]);
	        }
	        for(int i = 0; i < room.getObjectCount(); i++) {
	            println(room.getObject(i).getDescription());
	        }
	    }
	}
	
	private void markRoomAsVisited() {
	    rooms.get(currentRoom).setVisited(true);
	}
	
	private void getInput() {
	    String line;
	    
	    // loop until line is not blank
	    while(true) {
	        line = readLine("> ");
	        if(line != null && line.length() > 0)
	            break;
	    }
	    
	    // convert to upper case
	    line = line.toUpperCase();
	    
	    // switch out the synonym if it's present
	    String alt = synonyms.get(line);
	    if(alt != null) {
	        line = alt;
	    }
	    
	    parseInput(line);
	    
	}
	
	private void parseInput(String inp) {
	    String[] inputParts = inp.split(" ");
	    if(inputParts.length > 0) {
	        if(inputParts[0].equals("QUIT")) {
                
            } else if(inputParts[0].equals("HELP")) {
                
            } else if(inputParts[0].equals("INVENTORY")) {
                
            } else if(inputParts[0].equals("LOOK")) {
                
            } else if(inputParts[0].equals("TAKE")) {
	            
	        } else if(inputParts[0].equals("DROP")) {
	            
	        } else {  
                handleMove(inputParts[0]);
	        }
	    }
	}
	
	private void handleMove(String inp) {
	    AdvRoom room = rooms.get(currentRoom);
	    AdvMotionTableEntry entry = room.getEntry(inp);
        if(entry == null) {
            println("I don't know what to do with " + inp + "!");
        } else {
            currentRoom = entry.getDestinationRoom();
        }
	}
	
	private void loadFromFile() {
	    String fileName = readLine("Enter the name of the adventure: ");
	    rooms = new ArrayList<AdvRoom>();
	    synonyms = new HashMap<String, String>();
	    heldObjects = new HashMap<String, AdvObject>();
        readRooms(fileName);
        readObjects(fileName);
        readSynonyms(fileName);
	}
	
	private void readRooms(String fileName) {
	    try {
	        BufferedReader rd = new BufferedReader(new FileReader(fileName + "Rooms.txt"));
	        while(true) {
	            AdvRoom room = AdvRoom.readRoom(rd);
	            if(room == null)
	                break;
	            rooms.add(room);
	        }
	        rd.close();
	    } catch (Exception e) {
	        println("Exception reading rooms: " + e.toString());
	    }
	}
	
	private void readObjects(String fileName) {
        try {
            BufferedReader rd = new BufferedReader(new FileReader(fileName + "Objects.txt"));
            while(true) {
                AdvObject object = AdvObject.readObject(rd);
                if(object == null)
                    break;
                rooms.get(object.getInitialLocation()).addObject(object);
            }
            rd.close();
        } catch (Exception e) {
            println("Exception reading objects: " + e.toString());
        }
    }
	
	private void readSynonyms(String fileName) {
	    try {
            BufferedReader rd = new BufferedReader(new FileReader(fileName + "Synonyms.txt"));
            while(true) {
                String line = rd.readLine();
                if(line == null || line.equals(""))
                    break;
                String[] sp = line.split("=");
                synonyms.put(sp[0], sp[1]);
            }
            rd.close();
        } catch (Exception e) {
            println("Exception reading synonyms: " + e.toString());
        }
	}
}
