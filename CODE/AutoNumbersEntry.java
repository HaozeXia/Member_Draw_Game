/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class AutoNumbersEntry extends NumbersEntry implements Serializable {
    /**
	 * This class is extends from NumbersEntry and
	 * implements Serializable
	 * this class is use to automatic generate 7 numbers
	 * in the range 1 - 35
	 */
	private static final long serialVersionUID = 1L;  //serializable ID
	private final int NUMBER_COUNT = 7; 
    private final int MAX_NUMBER = 35;
	
    /**
     * This method is use to auto createNumber
     * @param seed is the seed to control auto generator
     * @return int[] it will return an array with 7 integer 
     * in the range 1 to 35
     *  */
    public int[] createNumbers (int seed) {
    	
        ArrayList<Integer> validList = new ArrayList<Integer>();
        
	    int[] tempNumbers = new int[NUMBER_COUNT]; // Array size 7
	
        for (int i = 1; i <= MAX_NUMBER; i++) {
        	//from 1 to 35
    	    validList.add(i);
        }
        
        Collections.shuffle(validList, new Random(seed));
        
        for (int i = 0; i < NUMBER_COUNT; i++) {
        	
    	    tempNumbers[i] = validList.get(i);
        }
        
        Arrays.sort(tempNumbers);  //sort in ascending order
        
        return tempNumbers;
    }
    
    /**
     * This is a empty constructor for AutoNumbersEntry class
     * */
    public AutoNumbersEntry() {
    	
    }
    
    /**
     * This is a constructor for AutoNumbersEntry class
     * @param entryID is the entry ID
     * @param billID is the bill ID
     * @param mID is the member ID
     * @param seed is the seed control randomness
     * */
    public AutoNumbersEntry(int entryID,String billID, String mID, int seed) {
    	super(entryID,billID,mID);
    	this.numbers = createNumbers(seed);  //generate numbers array
    }
}
