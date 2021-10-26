/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Competition implements Serializable {
    /**
	 * This class is use to record all competitions info
	 * each competition only have to type LuckyNumber or RandomPick
	 */
	private static final long serialVersionUID = 1L;  // serializble ID
	private String name; //competition name
    private int id; //competition identifier
    public ArrayList<Entry> entryArray = new ArrayList<Entry>(); //save all entries
    private int entryID;
    public Map<String, Entry> winMap = new LinkedHashMap<>(); 
    public boolean status;  //check it active or not
    private int sumPrize;  //total Prize for this competition


    /**
     * This is a abstract method addEntries
     * which class implement this class need
     * to finish this method in their class
     * */
    public abstract void addEntries();

    /**
     * This is a abstract method drawWinners
     * which class implement this class need
     * to finish this method in their class
     * */
    public abstract void drawWinners();
    
    /**
     * This is a constructor for Competition Class
     * @param name is the competition name
     * @param entryID is the competition ID
     * */
    public Competition(String name, int entryID) {
    	this.name = name;
    	entryID =1;  //initialized the entryID
    	status = false;  //initialized the competition status
    	entryArray = new ArrayList<>(); //initialized the ArrayList entryArray
    	winMap = new HashMap<>();  //initialized the HashMap winMap
    	
    }
    
    /**
     * This is a constructor for Competition Class
     * @param id is the competition id
     * @param name is the competition name
     * */
    public Competition(int id,String name) {
    	
    	this.name = name;
    	this.id = id;
    	entryArray = new ArrayList<>(); //initialized the ArrayList entryArray
    	winMap = new HashMap<>(); //initialized the HashMap winMap
    	status = false; //initialized the competition status
    		
    }
    
    /**
     * This is a empty constructor for Entry class
     * */
    public Competition() {
    	
    }
    
    /**
     * This is a constructor for Competition Class
     * @param status is the competition active status
     * */
    public Competition(boolean status) {
    	this.status = status;
    	
    }
    
    /**
     * This is a constructor for Competition Class
     * @param id is the competition id
     * */
    public Competition(int id) {
    	this.id = id;
    }
    
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the entryID
	 */
	public int getEntryID() {
		return entryID;
	}

	/**
	 * @return the status
	 */


	/**
	 * This method is use to sum up all
	 * prize in this competition
	 * @return the sumPrize
	 */
	public int getSumPrize() {
		sumPrize = 0;
		for(String k : winMap.keySet()) {
			sumPrize += winMap.get(k).getPrize();
		}
		return sumPrize;
	}



}
