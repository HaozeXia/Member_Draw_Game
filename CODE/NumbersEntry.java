import java.io.Serializable;

/*
 * Student name: Haoze Xia
 * Student ID: 1131343
 * LMS username: haozex
 */

public class NumbersEntry extends Entry implements Serializable {
	
    /**
	 * This class is extends from Entry and implements Serializable
	 * this class is to record the entry info
	 */
	private static final long serialVersionUID = 1L;  //serializable ID
	public int[] numbers;  //AutoNumbersEntry need to use this as well so set it to public
	
	/**
	 * This is a constructor for NumbersEntry Class
	 * @param entryId is the entry ID
	 * @param billId is the bill ID
	 * @param memberId is the member ID
	 * */
    public NumbersEntry(int entryId, String billId, String memberId) {
    	super(entryId,billId,memberId);
    }
    
	/**
	 * This is a constructor for NumbersEntry Class
	 * @param entryId is the entry ID
	 * @param billId is the bill ID
	 * @param memberId is the member ID
	 * @param numbers is the entry array (7 numbers)
	 * @param manualType is use to determine enter by manual or not
	 * */
    public NumbersEntry(int entryId, String billId, String memberId,
    		int[] numbers,boolean manualType) {
    	super(entryId,billId,memberId,manualType);
    	this.numbers = numbers;
    }
    
    /**
     * This is a empty constructor for NumbersEntry class
     * */
    public NumbersEntry() {
    	
    }
    
	/**
	 * @return the numbers
	 */
	public int[] getNumbers() {
		return numbers;
	}

}
